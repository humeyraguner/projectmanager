/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.interfaces.IWorkDao;
import entity.Status;
import entity.Work;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class WorkDao extends Dao implements IWorkDao {

    @Override
    public void changeStatus(int workId, int status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Work t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Work t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Work> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Work findById(int id) {
        String q = "select * from work where id=?";
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return workFromResulset(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Work workFromResulset(ResultSet rs) throws SQLException {
        Work work = new Work();
        work.setId(rs.getInt("id"));
        work.setTitle(rs.getString("title"));
        work.setDescription(rs.getString("description"));
        work.setStatus(Status.values()[rs.getInt("status")]);
        work.setAsSignedAt(null);
        work.setProject(null);
        work.setCreatedAt(rs.getDate("createdAt"));
        work.setUpdatedAt(rs.getDate("updatedAt"));

        return new Work();
    }

}
