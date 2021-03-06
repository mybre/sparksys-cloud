package com.sparksys.oauth.interfaces.controller;

import com.nimbusds.jose.JWSObject;
import com.sparksys.core.base.api.ResponseResultUtils;
import com.sparksys.jwt.entity.JwtUserInfo;
import com.sparksys.jwt.service.JwtTokenService;
import com.sparksys.log.annotation.WebLog;
import com.sparksys.oauth.service.OauthService;
import com.sparksys.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.assertj.core.util.Lists;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import com.sparksys.log.annotation.WebLog;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.ParseException;
import java.util.Map;


/**
 * description：授权登录管理
 *
 * @author zhouxinlei
 * @date 2020/6/6 9:08 上午
 */
@RestController
@RequestMapping("/oauth")
@ResponseResult
@WebLog
@Api(tags = "授权登录管理")
public class OauthController {

    private final OauthService oauthService;
    private final JwtTokenService jwtTokenService;

    public OauthController(OauthService oauthService, JwtTokenService jwtTokenService) {
        this.oauthService = oauthService;
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping("/token")
    @Trace(operationName = "oauth_get_token_trace")
    public OAuth2AccessToken getAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        ActiveSpan.tag("getAccessToken", "get授权登录");
        return oauthService.getAccessToken(principal, parameters);
    }

    @PostMapping("/token")
    @Trace(operationName = "oauth_post_token_trace")
    public OAuth2AccessToken postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        ActiveSpan.tag("postAccessToken", "post授权登录");
        return oauthService.postAccessToken(principal, parameters);
    }

    @GetMapping("/getCurrentUser")
    @ApiOperation("获取当前用户")
    public Object getCurrentUser(HttpServletRequest httpRequest) throws ParseException {
        String token = ResponseResultUtils.getAuthHeader(httpRequest);
        JWSObject jwsObject = JWSObject.parse(token);
        return jwsObject.getPayload().toJSONObject();
    }

    @ApiOperation("获取非对称加密（RSA）算法公钥")
    @GetMapping(value = "/createTokenByRsa")
    @ResponseBody
    public String createTokenByRsa() {
        JwtUserInfo jwtUserInfo = JwtUserInfo.builder()
                .sub("zhouxinlei")
                .iat(System.currentTimeMillis())
                .authorities(Lists.newArrayList())
                .username("zhouxinlei")
                .expire(3600L)
                .build();
        return jwtTokenService.createTokenByRsa(jwtUserInfo);
    }

}
