package com.itheima.sourcecodeshiro.config;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public Realm realm() {
        // 创建SimpleAccountRealm 对象
        SimpleAccountRealm realm = new SimpleAccountRealm();
        // 添加两个用户 参数是username，password，roles
        realm.addAccount("admin","admin","ADMIN");
        realm.addAccount("normal","normal","NORMAL");
        return realm;
    }
    @Bean
    public DefaultWebSecurityManager securityManager() {
        // 创建对象DefaultWebSecurityManager 对象
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置其使用的realm
        securityManager.setRealm(this.realm());
        return securityManager;
    }
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
//      1  创建ShiroFilterFactoryBean 对象 用于创建ShiroFilter
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
//      2  设置securityManager
        filterFactoryBean.setSecurityManager(this.securityManager());
//      3  设置url
            // 登陆url
        filterFactoryBean.setLoginUrl("/login");
            // 登陆成功url
        filterFactoryBean.setSuccessUrl("/login_success");
            // 无权限url
        filterFactoryBean.setUnauthorizedUrl("/unauthorized");
//      4   设置url 的权限配置
        filterFactoryBean.setFilterChainDefinitionMap(this.filterChainDefinitionMap());
        return filterFactoryBean;
    }

    private Map<String, String> filterChainDefinitionMap() {
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        // 允许匿名访问
        filterMap.put("/test/echo","anon");
        // 需要admin角色
        filterMap.put("/test/admin","roles[ADMIN]");
        // 允许NORMAL访问
        filterMap.put("/test/normal","roles[NORMAL]");
        // 退出
        filterMap.put("/logout", "logout");
        // 默认的剩余url 需要经过认证
        filterMap.put("/**","authc");
        return filterMap;
    }


}
