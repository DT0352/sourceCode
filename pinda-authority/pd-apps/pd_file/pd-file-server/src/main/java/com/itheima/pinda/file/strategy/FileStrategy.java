package com.itheima.pinda.file.strategy;

import com.itheima.pinda.file.domain.FileDeleteDO;
import com.itheima.pinda.file.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description 文件服务的总接口
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/5/25
 */
public  interface FileStrategy {

    /**
     * 上传文件
     * @param multipartFile 浏览器上传的文件
     * @return
     */
    File upload(MultipartFile multipartFile);

    /**
     * 批量删除文件
     * @param list 文件集合
     * @return
     */
    boolean deleteFile(List<FileDeleteDO> list);


}
