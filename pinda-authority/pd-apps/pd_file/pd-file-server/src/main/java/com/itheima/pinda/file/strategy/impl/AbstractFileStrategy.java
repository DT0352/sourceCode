package com.itheima.pinda.file.strategy.impl;

import com.itheima.pinda.exception.BizException;
import com.itheima.pinda.exception.code.ExceptionCode;
import com.itheima.pinda.file.domain.FileDeleteDO;
import com.itheima.pinda.file.entity.File;
import com.itheima.pinda.file.enumeration.IconType;
import com.itheima.pinda.file.properties.FileServerProperties;
import com.itheima.pinda.file.strategy.FileStrategy;
import com.itheima.pinda.file.utils.FileDataTypeUtil;
import com.itheima.pinda.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @Description 文件服务策略的抽象处理类
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/5/26
 */
@Slf4j
public abstract class AbstractFileStrategy implements FileStrategy {
    private static final String FILE_SPLIT = ".";
    @Autowired
    protected FileServerProperties fileServerProperties;
    protected FileServerProperties.Properties properties;

    @Override
    public File upload(MultipartFile multipartFile) {
        try {
            // 判断是否存在后缀
            if (!Objects.requireNonNull(multipartFile.getOriginalFilename()).contains(FILE_SPLIT)) {
                throw BizException.wrap(ExceptionCode.BASE_VALID_PARAM.build("缺少后缀名"));
            }
            File file = File.builder()
                    // 数据类型
                    .dataType(FileDataTypeUtil.getDataType(multipartFile.getContentType()))
                    // 原始文件名
                    .submittedFileName(multipartFile.getOriginalFilename())
                    // 是否删除
                    .isDelete(false)
                    // 文件大小
                    .size(multipartFile.getSize())
                    // 文件上传类型
                    .contextType(multipartFile.getContentType())
                    // 文件后缀
                    .ext(FilenameUtils.getExtension(multipartFile.getOriginalFilename()))
                    .build();
            // 设置图标
            file.setIcon(IconType.getIcon(file.getExt()).getIcon());

            uploadFile(file, multipartFile);

            LocalDateTime now = LocalDateTime.now();
            // 设置时间
            file.setCreateDay(DateUtils.formatAsDateEn(now));
            file.setCreateWeek(DateUtils.formatAsYearWeekEn(now));
            file.setCreateMonth(DateUtils.formatAsYearMonthEn(now));
            return file;
        } catch (Exception e) {
            log.error("e={}", e);
            throw BizException.wrap(ExceptionCode.BASE_VALID_PARAM.build("上传文件失败"));
        }

    }

    /**
     * 具体上传文件的方法 子类实现
     *
     * @param file
     * @param multipartFile
     */
    protected abstract void uploadFile(File file, MultipartFile multipartFile) throws IOException;

    @Override
    public boolean deleteFile(List<FileDeleteDO> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        boolean flag = false;
        for (FileDeleteDO fileDeleteDO : list) {
            try {
                deleteFile(fileDeleteDO);
                flag = true;
            } catch (Exception e) {
                log.error("删除文件失败", e);
            }
        }
        return flag;
    }

    /**
     * 子类具体实现删除方法
     *
     * @param fileDeleteDO
     */
    protected abstract void deleteFile(FileDeleteDO fileDeleteDO);

    /**
     * 获取下载地址前缀
     */
    protected String getUriPrefix() {
        if (StringUtils.isNotEmpty(properties.getUriPrefix())) {
            return properties.getUriPrefix();
        } else {
            return properties.getEndpoint();
        }
    }
}