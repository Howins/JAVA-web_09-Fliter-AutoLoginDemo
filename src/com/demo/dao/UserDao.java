package com.demo.dao;

import java.sql.SQLException;

import com.demo.bean.UserBean;

public interface UserDao {

	UserBean login(UserBean user) throws SQLException;
}
