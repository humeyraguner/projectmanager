/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao.interfaces;

import entity.Work;

/**
 *
 */
public interface IWorkDao extends IDao<Work>{
    void changeStatus(int workId,int status);
}
