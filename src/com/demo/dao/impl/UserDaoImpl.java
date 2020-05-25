package com.demo.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.demo.bean.UserBean;
import com.demo.dao.UserDao;
import com.demo.util.JDBCUtil;

/**
 * by JDBCUtil and QueryRunner return the result and wrap into UserBean
 * @author Howins
 *
 */
public class UserDaoImpl implements UserDao {

	public UserBean login(UserBean user) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
		String sql = "select * from t_usr where username = ? and password = ?";
		return qr.query(sql, new BeanHandler<UserBean>(UserBean.class),user.getUsername(),user.getPassword());

	}

}
