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
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class WorkDao extends Dao implements IWorkDao {

    private static WorkDao instance = null;

    private WorkDao() {

    }

    public static WorkDao getInstance() {
        if (instance == null) {
            instance = new WorkDao();
        }
        return instance;
    }

    @Override
    public void changeStatus(int workId, int status) {
        String q = "update work set status=? where id=?";
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setInt(1, status);
            pst.setInt(2, workId);
            pst.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean create(Work t) {
        String q = "insert into work (id,title,description,status) values(default,?,?,?)";
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setString(1, t.getTitle());
            pst.setString(2, t.getDescription());
            pst.setInt(3, t.getStatus().ordinal());
            ResultSet rs = pst.executeQuery();
            pst.close();
            return rs.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Work t) {
        String q = "update work set";
        // TODO UPDATE
        return true;
    }

    @Override
    public boolean delete(int id) {
        String q = "delete from work where id=?";

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
    public List<Work> findAll() {
        String q = "select * from work";
        List<Work> works = new ArrayList<>();
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                works.add(workFromResulset(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return works;
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
