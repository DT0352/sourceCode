package com.itheima.pinda.file.storage;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.itheima.pinda.file.domain.FileDeleteDO;
import com.itheima.pinda.file.entity.File;
import com.itheima.pinda.file.properties.FileServerProperties;
import com.itheima.pinda.file.strategy.impl.AbstractFileStrategy;
import com.itheima.pinda.utils.DateUtils;
import com.itheima.pinda.utils.SpringUtils;
import com.itheima.pinda.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.UUID;

/**
 * @Description 这是一个配置类，
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/5/29
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(FileServerProperties.class)
@ConditionalOnProperty(name = "pinda.file.type", havingValue = "LOCAL")
public class LocalAutoConfigure {

    /**
     * 本地文件处理类
     */
    @Service
    public class LocalServiceImpl extends AbstractFileStrategy {
        private void buildCilent() {
            // 设置处理策略
            properties = fileServerProperties.getLocal();
        }

        @Override
        protected void uploadFile(File file, MultipartFile multipartFile) throws IOException {
            buildCilent();
            String fileName = UUID.randomUUID().toString() + StrPool.DOT + file.getExt();
            // 相对路径
            String relativePath = Paths.get(LocalDate.now().format(DateTimeFormatter.ofPattern(DateUtils.DEFAULT_MONTH_FORMAT_SLASH))).toString();
            // 文件全路径名
            String absolutePath = Paths.get(properties.getEndpoint(), properties.getBucketName(), relativePath, fileName).toString();
            java.io.File outFile = new java.io.File(absolutePath);
            // 上传文件到本地ls

            FileUtil.writeBytes(multipartFile.getBytes(), outFile);
            // 完成File 封装  http：//ip:port/oss-file-service/2021/01/filename
            String url = new StringBuilder(properties.getUriPrefix())
                    .append(StrPool.SLASH)
                    .append(properties.getBucketName())
                    .append(StrPool.SLASH)
                    .append(relativePath)
                    .append(StrPool.SLASH)
                    .append(fileName).toString();
            StrUtil.replace(url, "\\\\", StrPool.SLASH);
            StrUtil.replace(url, "\\", StrPool.SLASH);
            file.setUrl(url);
            file.setFilename(fileName);
            file.setRelativePath(relativePath);
        }


        @Override
        protected void deleteFile(FileDeleteDO fileDeleteDO) {
            java.io.File file = new java.io.File(Paths.get(properties.getEndpoint(),
                    properties.getBucketName(),
                    fileDeleteDO.getRelativePath(),
                    fileDeleteDO.getFileName()).toString());
            FileUtil.del(file);
            ///
        }
    }
}
