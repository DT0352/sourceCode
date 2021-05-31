package com.itheima.pinda.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pinda.file.dto.AttachmentDTO;
import com.itheima.pinda.file.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

/**
 * 附件上传
 *
 * @author fuzhidong
 */
public interface AttachmentService extends IService<Attachment> {

    /**
     * 上传文件
     * @param multipartFile
     * @param bizId
     * @param bizType
     * @param id
     * @param isSingle
     * @return
     */
    AttachmentDTO upload(MultipartFile multipartFile, String bizId, String bizType, Long id, Boolean isSingle);

}
