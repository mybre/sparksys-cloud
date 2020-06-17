package com.sparksys.authorization.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sparksys.authorization.infrastructure.entity.AuthUser;
import com.sparksys.authorization.infrastructure.mapper.AuthUserMapper;
import com.sparksys.authorization.domain.repository.IAuthUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

/**
 * description：用户仓储层实现类
 *
 * @Author zhouxinlei
 * @Date 2020/6/5 8:45 下午
 */
@Repository
public class AuthUserRepository implements IAuthUserRepository {

    @Autowired
    private AuthUserMapper authUserMapper;

    @Override
    public AuthUser selectById(Long id) {
        return authUserMapper.selectById(id);
    }

    @Override
    public AuthUser selectByAccount(String account) {
        QueryWrapper<AuthUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        queryWrapper.eq("status", 1);
        return authUserMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean saveAuthUser(AuthUser authUser) {
        authUser.setStatus(true);
        return authUserMapper.insert(authUser) > 0;
    }

    @Override
    public boolean updateAuthUser(AuthUser authUser) {
        return authUserMapper.updateById(authUser) > 0;
    }

    @Override
    public boolean deleteAuthUser(Long id) {
        return authUserMapper.deleteById(id) > 0;
    }

    @Override
    public Page<AuthUser> listByPage(Page authUserDOPage, String name) {
        QueryWrapper<AuthUser> authUserDOQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equalsIgnoreCase(name)) {
            authUserDOQueryWrapper.like("name", name);
        }
        return authUserMapper.selectPage(authUserDOPage, authUserDOQueryWrapper);
    }

    @Override
    public boolean incrPasswordErrorNumById(Long id) {
        return authUserMapper.incrPasswordErrorNumById(id) == 1;
    }
}
