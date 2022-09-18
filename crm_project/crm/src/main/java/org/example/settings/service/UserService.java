package org.example.settings.service;

import org.example.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //查询用户，登录验证
    User queryUserByLoginActAndPwd(Map<String,Object> map);

    //查询所有用户名
    List<User> queryAllUser();
}
