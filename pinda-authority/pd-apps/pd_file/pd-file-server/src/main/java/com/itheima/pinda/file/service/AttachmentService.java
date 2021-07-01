package com.itheima.pinda.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pinda.file.dto.AttachmentDTO;
import com.itheima.pinda.file.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
     * 批量删除文件
     * @param ids
     * @return
     */
     void remove(Long[] ids);

    /**
     * 根据业务ID或业务类型删除附件
     * @param bizId
     * @param bizType
     */
    void removeByBizIdAndBizType(String bizId, String bizType);

    void down(Long[] ids, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
