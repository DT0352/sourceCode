package com.itheima.pinda.file.storage;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.itheima.pinda.file.domain.FileDeleteDO;
import com.itheima.pinda.file.entity.File;
import com.itheima.pinda.file.properties.FileServerProperties;
import com.itheima.pinda.file.strategy.impl.AbstractFileStrategy;
import com.itheima.pinda.utils.DateUtils;
import com.itheima.pinda.utils.StrPool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @Description
 * @Author 付志东
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/5/30
 */
@EnableConfigurationProperties(FileServerProperties.class)
@Configuration
@Slf4j
@ConditionalOnProperty(name = "pinda.file.type", havingValue = "ALI")
public class AliOssAutoConfigure {
    /**
     * 阿里文件服务策略处理类
     */

    @Service
    public class AliServiceImpl extends AbstractFileStrategy {
        private OSS getOssClient() {
            properties = fileServerProperties.getAli();
            return new OSSClientBuilder().build(
                    properties.getEndpoint(),
                    properties.getAccessKeyId(),
                    properties.getAccessKeySecret());
        }
        @Override
        protected String getUriPrefix() {
            if (StringUtils.isNotEmpty(properties.getUriPrefix())) {
                return properties.getUriPrefix();
            } else {
                String prefix = properties.
                        getEndpoint().
                        contains("https://") ? "https://" : "http://";
                return prefix + properties.getBucketName() + "." +
                        properties.getEndpoint().replaceFirst(prefix, "");
            }
        }

        @Override
        protected void uploadFile(File file, MultipartFile multipartFile) throws IOException {
            OSS ossClient = getOssClient();
            // 若bucketName 不存在则创建
            if (!ossClient.doesBucketExist(properties.getBucketName())){
                ossClient.createBucket(properties.getBucketName());
            }
            String fileName = UUID.randomUUID().toString() + StrPool.DOT + file.getExt();
            //日期文件夹，例如：2020\04
            String relativePath =
                    Paths.get(LocalDate.now().
                            format(DateTimeFormatter.
                            ofPattern(DateUtils.DEFAULT_MONTH_FORMAT_SLASH))).
                            toString();
            // web服务器存放的相对路径
            String relativeFileName = relativePath + StrPool.SLASH + fileName;
            relativeFileName = StrUtil.replace(relativeFileName, "\\\\",
                    StrPool.SLASH);
            relativeFileName = StrUtil.replace(relativeFileName, "\\",
                    StrPool.SLASH);
            // 上传文件到阿里云OSS
            PutObjectResult objectResult = ossClient.putObject(properties.getBucketName(), relativeFileName, new ByteArrayInputStream(multipartFile.getBytes()));
            log.info("objectResult={}", JSONObject.toJSONString(objectResult));
            String url = getUriPrefix()+StrPool.SLASH + relativeFileName;
            StrUtil.replace(url,"\\\\",StrPool.SLASH);
            StrUtil.replace(url,"\\",StrPool.SLASH);
            //写人file表
            file.setUrl(url);
            file.setFilename(fileName);
            file.setRelativePath(relativePath);
            file.setGroup(objectResult.getETag());
            file.setPath(objectResult.getRequestId());
            ossClient.shutdown();
        }

        @Override
        protected void deleteFile(FileDeleteDO fileDeleteDO) {
            OSS ossClient = getOssClient();
            ossClient.deleteObject(properties.getBucketName(), fileDeleteDO.getFileName());

        }

    }
}
