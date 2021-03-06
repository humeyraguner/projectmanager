/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.interfaces.IUserDao;
import entity.User;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 
 */
public class UserDao extends Dao implements IUserDao {
    
    private static UserDao instance = null;

    private UserDao() {
    }

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }
    

    @Override
    public User login(String email, String password) {
        String q = "select * from users where (email=? or username=?) and password=?";

        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setString(1, email);
            pst.setString(2, email);
            pst.setString(3, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return userFromResulset(rs);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(User user) {
        String q = "insert into users (id,name,surname,email,username,password) values (default,?,?,?,?,?)";
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setString(1, user.getName());
            pst.setString(2, user.getSurname());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getUsername());
            pst.setString(5, user.getPassword());
            ResultSet rs = pst.executeQuery();
            pst.close();
            return rs.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(User user) {
        String q = "update users set name=?,surname=?,email=?,username=?,password=? where id=?";
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setString(1, user.getName());
            pst.setString(2, user.getSurname());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getUsername());
            pst.setString(5, user.getPassword());
            pst.setInt(6, user.getId());
            ResultSet rs = pst.executeQuery();
            pst.close();
            return rs.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public User findById(int id) {
        String q = "select * from users where id=?";
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return userFromResulset(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        String q ="delete from users where id=?";
        
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List findAll() {
        String q ="select * from users";
        List<User> users = new ArrayList<>();
         try {
            PreparedStatement pst = getConn().prepareStatement(q);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                users.add(userFromResulset(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
    
    public User userFromWorkId(int id){
         String q = "select * from users where id = (select u.user_id from user_works u where work_id = ?)";
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return userFromResulset(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private User userFromResulset(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"), rs.getString("name"),
                rs.getString("surname"), rs.getString("email"),
                rs.getString("username"), rs.getString("password"));
    }

    @Override
    public boolean checkByUsernameOrEmail(String username) {
        String q = "select * from users where email=? or username=?";
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setString(1, username);
            pst.setString(2, username);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public User findyByUsername(String username) {
        String q = "select * from users where username=?";
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return userFromResulset(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
