/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.interfaces;

import java.util.List;

/**
 *
 
 */
public interface IDao<T> {
    
    boolean create(T t);
    boolean update(T t);
    boolean delete(int id);
    
    List<T> findAll();
    T findById(int id);
    
}
