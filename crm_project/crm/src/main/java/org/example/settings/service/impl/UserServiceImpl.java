package org.example.settings.service.impl;

import org.example.settings.domain.User;
import org.example.settings.mapper.UserMapper;
import org.example.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    //通过用户名和密码查询用户，用来登录验证  在settings/UserController
    @Override
    public User queryUserByLoginActAndPwd(Map<String, Object> map) {
        return userMapper.selectUserByLoginActAndPwd(map);
    }
    //查询所有用户，市场活动创建时，遍历展示
    //ActivityController     workbench/activity/index.jsp
    @Override
    public List<User> queryAllUser() {
        return userMapper.selectAllUsers();
    }
}
