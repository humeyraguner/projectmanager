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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class WorkDao extends Dao implements IWorkDao {

    private ProjectDao projectDao = null;

    private UserDao userDao = null;

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
        String q = "insert into work (id,title,description,status,project) values(default,?,?,?,?)";
        try {
            PreparedStatement pst = getConn().prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, t.getTitle());
            pst.setString(2, t.getDescription());
            pst.setInt(3, t.getStatus().ordinal());
            pst.setInt(4, t.getProject().getId());
            pst.executeUpdate();
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int workId = (int) generatedKeys.getLong(1);
                    String relationQuery = "insert into user_works (id,user_id,work_id) values (default,?,?)";
                    PreparedStatement pst2 = getConn().prepareStatement(relationQuery);
                    pst2.setInt(1, t.getAsSignedAt().getId());
                    pst2.setInt(2, workId);
                    pst2.execute();

                } else {
                    throw new SQLException("Creating failed.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Work t) {
        String q = "update work set title=?,description=? where id=?";
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setString(1, t.getTitle());
            pst.setString(2, t.getDescription());
            pst.setInt(3, t.getId());
            pst.execute();
            try {
                int beforeUserId = t.getAsSignedAt().getId();
                t.setAsSignedAt(getUserDao().findyByUsername(t.getAsSignedAt().getUsername()));
                String relationQuery = "update user_works set user_id=? where user_id=? and work_id=?";
                PreparedStatement pst2 = getConn().prepareStatement(relationQuery);
                pst2.setInt(1, t.getAsSignedAt().getId());
                pst2.setInt(2, beforeUserId);
                pst2.setInt(3, t.getId());
                pst2.execute();
                try {
                    String relationQuery2 = "insert into user_projects (id,user_id,project_id) values (default,?,?)";
                    PreparedStatement pst3 = getConn().prepareStatement(relationQuery2);
                    pst3.setInt(1, t.getAsSignedAt().getId());
                    pst3.setInt(2, t.getProject().getId());
                    return pst3.execute();

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
            while (rs.next()) {
                works.add(workFromResulset(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return works;
    }

    public List<Work> findAll(int userId, Status status, int projectId) {
        String q = "select * from work where project=? and status=? and id in (select work_id from user_works where user_id=?)";
        List<Work> works = new ArrayList<>();
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setInt(1, projectId);
            pst.setInt(2, status.ordinal());
            pst.setInt(3, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
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
        work.setAsSignedAt(getUserDao().userFromWorkId(rs.getInt("id")));
        work.setProject(getProjectDao().findById(rs.getInt("project")));
        work.setCreatedAt(rs.getDate("created_at"));
        work.setUpdatedAt(rs.getDate("updated_at"));
        return work;
    }

    public ProjectDao getProjectDao() {
        if (projectDao == null) {
            projectDao = ProjectDao.getInstance();
        }
        return projectDao;
    }

    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = UserDao.getInstance();
        }
        return userDao;
    }
}
