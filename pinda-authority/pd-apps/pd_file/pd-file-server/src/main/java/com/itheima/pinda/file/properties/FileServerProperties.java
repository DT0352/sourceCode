package com.itheima.pinda.file.properties;

import com.itheima.pinda.file.enumeration.FileStorageType;
import com.itheima.pinda.utils.StrPool;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 *文件策略配置属性类
 */
@Data
@ConfigurationProperties(prefix = "pinda.file")
@RefreshScope
@Component
public class FileServerProperties {
    /**
     * 为以下3个值，指定不同的自动化配置
     * qiniu：七牛oss
     * aliyun：阿里云oss
     * fastdfs：本地部署的fastDFS
     */
    private FileStorageType type = FileStorageType.LOCAL;
    /**
     * 文件访问前缀
     */
    private String uriPrefix = "" ;
    /**
     * 内网通道前缀 主要用于解决某些服务器的无法访问外网ip的问题
     */
    private String innerUriPrefix = "";

    public String getInnerUriPrefix() {
        return innerUriPrefix;
    }

    public String getUriPrefix() {
        if (!uriPrefix.endsWith(StrPool.SLASH)) {
            uriPrefix += StrPool.SLASH;
        }
        return uriPrefix;
    }

    private Properties local;
    private Properties ali;
    private Properties minio;
    private Properties qiniu;
    private Properties tencent;

    @Data
    public static class Properties {
        private String uriPrefix;
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private String bucketName;
    }
}
