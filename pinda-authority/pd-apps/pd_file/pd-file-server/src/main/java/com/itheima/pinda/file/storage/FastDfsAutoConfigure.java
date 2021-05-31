package com.itheima.pinda.file.storage;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.itheima.pinda.file.domain.FileDeleteDO;
import com.itheima.pinda.file.entity.File;
import com.itheima.pinda.file.properties.FileServerProperties;
import com.itheima.pinda.file.strategy.impl.AbstractFileStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/5/29
 */
@EnableConfigurationProperties(FileServerProperties.class)
@Configuration
@Slf4j
@ConditionalOnProperty(name = "pinda.file.type", havingValue = "FAST_DFS")
public class FastDfsAutoConfigure {
    /**
     * FastDFS 处理策略类
     */
    @Service
    public class FastDFSServiceImpl extends AbstractFileStrategy{


        @Autowired
        private FastFileStorageClient storageClient;

        @Override
        protected void uploadFile(File file, MultipartFile multipartFile) throws IOException {
            StorePath storePath = storageClient.uploadFile(multipartFile.getInputStream(), file.getSize(), file.getExt(), null);
            file.setUrl(fileServerProperties.getUriPrefix() + storePath.getFullPath());
            file.setGroup(storePath.getGroup());
            file.setPath(storePath.getPath());
        }

        @Override
        protected void deleteFile(FileDeleteDO fileDeleteDO) {
            storageClient.deleteFile(fileDeleteDO.getGroup() + fileDeleteDO.getPath());

        }
    }
}
