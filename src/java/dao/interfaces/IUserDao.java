/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import entity.User;

/**
 *
 
 */
public interface IUserDao extends IDao<User>{
    User login(String email,String password);
    boolean checkByUsernameOrEmail(String username);
    User findyByUsername(String username);

}
