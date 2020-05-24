package com.sparksys.gateway.infrastructure.registry;

import com.alibaba.fastjson.JSON;
import com.sparksys.gateway.infrastructure.prop.IgnoreUrlsProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description: Security api放行配置
 *
 * @Author zhouxinlei
 * @Date 2020-05-24 12:17:37
 */
@Setter
@Getter
@Slf4j
@Component
public class SecurityRegistry {

    private final IgnoreUrlsProperties ignoreUrlsProperties;

    private final List<String> excludePatterns = new ArrayList<>();

    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    public static final List<String> LIST = Arrays.asList(
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/swagger/**",
            "/**/v2/api-docs",
            "/**/*.js",
            "/**/*.css",
            "/**/*.png",
            "/**/*.ico",
            "/webjars/springfox-swagger-ui/**",
            "/doc.html",
            "/actuator/**",
            "/druid/**"
    );

    public SecurityRegistry(IgnoreUrlsProperties ignoreUrlsProperties) {
        this.ignoreUrlsProperties = ignoreUrlsProperties;
        log.info("ignoreUrlsProperties is {}", ignoreUrlsProperties.getUrls());
        excludePatterns.addAll(ignoreUrlsProperties.getUrls());
        excludePatterns.addAll(LIST);
    }

    /**
     * 设置放行api
     */
    public SecurityRegistry excludePathPatterns(String... patterns) {
        return excludePathPatterns(Arrays.asList(patterns));
    }

    /**
     * 设置放行api
     */
    public SecurityRegistry excludePathPatterns(List<String> patterns) {
        this.excludePatterns.addAll(patterns);
        return this;
    }

    public boolean isIgnoreToken(String currentUri) {
        return isIgnore(excludePatterns, currentUri);
    }

    public boolean isIgnore(List<String> list, String currentUri) {
        log.info("忽略地址：{}", JSON.toJSONString(list));
        log.info("请求地址：{}", currentUri);
        if (list.isEmpty()) {
            return false;
        }
        return list.stream().anyMatch((url) ->
                currentUri.startsWith(url) || ANT_PATH_MATCHER.match(url, currentUri)
        );
    }

}