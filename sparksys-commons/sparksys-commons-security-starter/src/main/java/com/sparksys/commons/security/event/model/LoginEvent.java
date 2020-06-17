package com.sparksys.commons.security.event.model;

import org.springframework.context.ApplicationEvent;

/**
 * 登录事件
 *
 * @author zuihou
 * @date 2020年03月18日17:22:55
 */
public class LoginEvent extends ApplicationEvent {

    public LoginEvent(LoginStatusDTO source) {
        super(source);
    }
}
