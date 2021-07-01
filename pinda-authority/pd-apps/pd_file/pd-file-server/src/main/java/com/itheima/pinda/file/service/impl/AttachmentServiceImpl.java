package com.itheima.pinda.file.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.pinda.base.id.IdGenerate;
import com.itheima.pinda.database.mybatis.conditions.Wraps;
import com.itheima.pinda.dozer.DozerUtils;
import com.itheima.pinda.exception.BizException;
import com.itheima.pinda.file.dao.AttachmentMapper;
import com.itheima.pinda.file.domain.FileDO;
import com.itheima.pinda.file.domain.FileDeleteDO;
import com.itheima.pinda.file.dto.AttachmentDTO;
import com.itheima.pinda.file.entity.Attachment;
import com.itheima.pinda.file.entity.File;

import com.itheima.pinda.file.enumeration.DataType;
import com.itheima.pinda.file.service.AttachmentService;
import com.itheima.pinda.file.strategy.FileStrategy;
import com.itheima.pinda.file.utils.ZipUtils;
import com.itheima.pinda.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Dte 2021/5/30
 */
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {

    @Autowired
    private IdGenerate<Long> idGenerate;
    @Autowired
    private FileStrategy fileStrategy;
    @Autowired
    private DozerUtils dozerUtils;

    @Override
    public AttachmentDTO upload(MultipartFile multipartFile, String bizId, String bizType, Long id, Boolean isSingle) {
        if (StringUtils.isNotEmpty(bizType) && bizId.isEmpty()) {
            bizId = String.valueOf(idGenerate.generate());
        }
        File file = fileStrategy.upload(multipartFile);
        Attachment attachment = dozerUtils.map(file, Attachment.class);
        attachment.setBizId(bizId);
        attachment.setBizType(bizType);
        setDate(attachment);
        if (isSingle) {
            super.remove(Wraps.<Attachment>lbQ().eq(Attachment::getBizId, bizId).eq(Attachment::getBizType, bizType));
        }
        if (id != null && id > 0) {
            //当前端传递了文件id时，修改这条记录
            attachment.setId(id);
            super.updateById(attachment);
        } else {
            attachment.setId(idGenerate.generate());
            super.save(attachment);
        }
        AttachmentDTO dto = dozerUtils.map(attachment, AttachmentDTO.class);
        return dto;
    }

    @Override
    public void remove(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return;
        }
        // 查询数据库
        List<Attachment> list = super.list(Wrappers.<Attachment>lambdaQuery().in(Attachment::getId, ids));
        if (list.isEmpty()) {
            return;
        }
        List<FileDeleteDO> collect = list.stream().map((fi) -> FileDeleteDO.builder()
                .relativePath(fi.getRelativePath())
                .fileName(fi.getFilename())
                .group(fi.getGroup())
                .path(fi.getPath())
                .build()).collect(Collectors.toList());
        // 删除文件
        fileStrategy.deleteFile(collect);
        // 设置数据库
        super.removeByIds(Arrays.asList(ids));
    }

    @Override
    public void removeByBizIdAndBizType(String bizId, String bizType) {

        List<Attachment> list = super.list(Wrappers.<Attachment>lambdaQuery().eq(Attachment::getBizId, bizId).eq(Attachment::getBizType, bizType));
        if (list.isEmpty()) {
            return;
        }
        remove(list.stream().mapToLong(Attachment::getId).boxed().toArray(Long[]::new));
    }

    @Override
    public void down(Long[] ids, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Attachment> list = (List<Attachment>) super.listByIds(Arrays.asList(ids));
        if (list.isEmpty()) {
            throw BizException.wrap("文件不存在");
        }
        //  获得FileDO集合
        List<FileDO> fileDOS = list.stream().map((fileDO) -> FileDO.builder()
                .submittedFileName(fileDO.getSubmittedFileName())
                .url(fileDO.getUrl())
                .dataType(fileDO.getDataType())
                .size(fileDO.getSize())
                .build()
        ).collect(Collectors.toList());
        long size = fileDOS.stream().filter(file -> file != null &&
                !DataType.DIR.equals(file.getDataType()) &&
                !file.getUrl().isEmpty()).
                mapToLong(FileDO::getSize).sum();
        String extName = list.get(0).getSubmittedFileName();
        if (list.size() > 1) {
            extName = list.get(0).getSubmittedFileName().substring(0, StringUtils.lastIndexOf(extName, ".")) + "等.zip";
        }
        // 获取文件map
        LinkedHashMap<String, String> fileMap = new LinkedHashMap<>(list.size());
        HashMap<String, Integer> duplicateFile = new HashMap<>();
        list.stream()
                //过滤不符合的文件
                .filter(file -> file != null &&
                    !DataType.DIR.equals(file.getDataType()) &&
                    !file.getUrl().isEmpty())
                //循环处理相同文件名   eg: 算法.pdf  算法（1).pdf
                .forEach(file -> {
                    String submittedFileName = file.getSubmittedFileName();
                    if (fileMap.containsKey(submittedFileName)) {
                        if (duplicateFile.containsKey(submittedFileName)) {
                            duplicateFile.put(submittedFileName, duplicateFile.get(submittedFileName) + 1);
                        } else {
                            duplicateFile.put(submittedFileName, 1);
                        }
                    } else {
                        fileMap.put(submittedFileName, file.getUrl());
                    }
                });

        ZipUtils.zipFilesByInputStream(fileMap,size,extName,request,response);
    }
    private void setDate(Attachment file) {
        LocalDateTime now = LocalDateTime.now();
        file.setCreateMonth(DateUtils.formatAsYearMonthEn(now));
        file.setCreateWeek(DateUtils.formatAsYearWeekEn(now));
        file.setCreateDay(DateUtils.formatAsDateEn(now));
    }

}
