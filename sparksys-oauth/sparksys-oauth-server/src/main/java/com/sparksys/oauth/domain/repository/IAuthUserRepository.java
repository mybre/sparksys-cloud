package com.sparksys.oauth.domain.repository;

import com.sparksys.oauth.infrastructure.entity.AuthUser;

import java.util.List;

/**
 * description: 用户仓储层
 *
 * @author zhouxinlei
 * @date 2020-06-05 20:39:15
 */
public interface IAuthUserRepository {


    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    AuthUser selectById(Long id);

    /**
     * 根据账户查询用户信息
     *
     * @param account
     * @return
     */
    AuthUser selectByAccount(String account);

    /**
     * 根据姓名模糊分页查询用户信息
     *
     * @param name 姓名
     * @return List<AuthUser>
     */
    List<AuthUser> listByName(String name);

    /**
     * 密码输错自增
     *
     * @param id
     * @return boolean
     */
    boolean incrPasswordErrorNumById(Long id);

    /**
     * 获取用户权限集
     *
     * @param id
     * @return Set<String>
     */
    List<String> getAuthUserPermissions(Long id);

    /**
     * 密码输错自增
     *
     * @param account
     * @return
     */
    boolean incrPasswordErrorNumByAccount(String account);
}
