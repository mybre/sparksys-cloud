package com.sparksys.authorization.domain.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sparksys.core.constant.CacheKey;
import com.sparksys.core.entity.GlobalAuthUser;
import com.sparksys.core.utils.MD5Utils;
import com.sparksys.database.service.impl.AbstractSuperCacheServiceImpl;
import com.sparksys.database.utils.PageInfoUtils;
import com.sparksys.security.entity.AuthUserDetail;
import com.sparksys.authorization.application.service.IAuthUserService;
import com.sparksys.authorization.domain.constant.AuthorizationConstant;
import com.sparksys.authorization.domain.repository.IAuthUserRepository;
import com.sparksys.authorization.infrastructure.convert.AuthUserConvert;
import com.sparksys.authorization.infrastructure.entity.AuthUser;
import com.sparksys.authorization.infrastructure.mapper.AuthUserMapper;
import com.sparksys.authorization.interfaces.dto.user.AuthUserDTO;
import com.sparksys.authorization.interfaces.dto.user.AuthUserSaveDTO;
import com.sparksys.authorization.interfaces.dto.user.AuthUserStatusDTO;
import com.sparksys.authorization.interfaces.dto.user.AuthUserUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * description: 用户查询 服务实现类
 *
 * @author zhouxinlei
 * @date 2020-05-24 12:22:57
 */
@Service
@Slf4j
public class AuthUserServiceImpl extends AbstractSuperCacheServiceImpl<AuthUserMapper, AuthUser> implements IAuthUserService {

    private final IAuthUserRepository authUserRepository;

    public AuthUserServiceImpl(IAuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }


    @Override
    public boolean saveAuthUser(GlobalAuthUser authUser, AuthUserSaveDTO authUserSaveDTO) {
        AuthUser authUserDO = AuthUserConvert.INSTANCE.convertAuthUserDO(authUserSaveDTO);
        authUser.setStatus(true);
        String password = MD5Utils.encrypt(authUser.getPassword());
        authUser.setPassword(password);
        return save(authUserDO);
    }

    @Override
    public boolean updateAuthUser(GlobalAuthUser authUser, AuthUserUpdateDTO authUserUpdateDTO) {
        AuthUser authUserDO = AuthUserConvert.INSTANCE.convertAuthUserDO(authUserUpdateDTO);
        return updateById(authUserDO);
    }

    @Override
    public boolean deleteAuthUser(Long id) {
        return removeById(id);
    }

    @Override
    public boolean updateAuthUserStatus(GlobalAuthUser authUser, AuthUserStatusDTO authUserStatusDTO) {
        authUserStatusDTO.setUpdateUser(authUser.getId());
        AuthUser authUserDO = AuthUserConvert.INSTANCE.convertAuthUserDO(authUserStatusDTO);
        return updateById(authUserDO);
    }

    @Override
    public PageInfo<AuthUserDTO> listByPage(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(pageNum,pageSize);
        List<AuthUser> authUserList = authUserRepository.listByName(name);
        List<AuthUserDTO> authUsers =
                authUserList.stream().map(authUserDO -> {
                    AuthUserDTO authUserDTO = AuthUserConvert.INSTANCE.convertAuthUserDTO(authUserDO);
                    String sex = AuthorizationConstant.SEX_MAP.get(authUserDO.getSex());
                    authUserDTO.setSex(sex);
                    return authUserDTO;
                }).collect(Collectors.toList());
        return PageInfoUtils.pageInfo(authUsers);
    }

    @Override
    public AuthUserDTO getAuthUser(Long id) {
        AuthUser authUser = getByIdCache(id);
        AuthUserDTO authUserDTO = AuthUserConvert.INSTANCE.convertAuthUserDTO(authUser);
        String sex = AuthorizationConstant.SEX_MAP.get(authUser.getSex());
        authUserDTO.setSex(sex);
        return authUserDTO;
    }

    @Override
    public boolean resetPassErrorNum(Long id) {
        AuthUser authUser = new AuthUser();
        authUser.setId(id);
        authUser.setPasswordErrorNum(0);
        authUser.setPasswordErrorLastTime(null);
        return updateById(authUser);
    }

    @Override
    public boolean resetPassErrorNum(String account) {
        AuthUser authUser = new AuthUser();
        authUser.setAccount(account);
        authUser.setPasswordErrorNum(0);
        authUser.setPasswordErrorLastTime(null);
        return updateById(authUser);
    }

    @Override
    public boolean incrPasswordErrorNum(Long id) {
        return authUserRepository.incrPasswordErrorNumById(id);
    }

    @Override
    public boolean incrPasswordErrorNum(String account) {
        return authUserRepository.incrPasswordErrorNumByAccount(account);
    }

    @Override
    public AuthUserDetail getAuthUserDetail(String username) {
        AuthUser authUser = authUserRepository.selectByAccount(username);
        if (ObjectUtils.isNotEmpty(authUser)) {
            GlobalAuthUser globalAuthUser = AuthUserConvert.INSTANCE.convertGlobalAuthUser(authUser);
            List<String> userPermissions = authUserRepository.getAuthUserPermissions(authUser.getId());
            globalAuthUser.setPermissions(userPermissions);
            return new AuthUserDetail(globalAuthUser);
        }
        return null;
    }

    @Override
    protected String getRegion() {
        return CacheKey.USER;
    }
}
