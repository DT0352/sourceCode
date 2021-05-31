package com.itheima.pinda.file.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.pinda.base.id.IdGenerate;
import com.itheima.pinda.database.mybatis.conditions.Wraps;
import com.itheima.pinda.dozer.DozerUtils;
import com.itheima.pinda.file.dao.AttachmentMapper;
import com.itheima.pinda.file.dto.AttachmentDTO;
import com.itheima.pinda.file.entity.Attachment;
import com.itheima.pinda.file.entity.File;
import com.itheima.pinda.file.properties.FileServerProperties;
import com.itheima.pinda.file.service.AttachmentService;
import com.itheima.pinda.file.strategy.FileStrategy;
import com.itheima.pinda.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


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
    private void setDate(Attachment file) {
        LocalDateTime now = LocalDateTime.now();
        file.setCreateMonth(DateUtils.formatAsYearMonthEn(now));
        file.setCreateWeek(DateUtils.formatAsYearWeekEn(now));
        file.setCreateDay(DateUtils.formatAsDateEn(now));
    }

}
