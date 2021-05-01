/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.interfaces.IProjectDao;
import entity.Project;
import entity.User;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ProjectDao extends Dao implements IProjectDao {

    private UserDao userDao;
    private static ProjectDao instance = null;

    private ProjectDao() {
    }

    public static ProjectDao getInstance() {
        if (instance == null) {
            instance = new ProjectDao();
        }
        return instance;
    }

    @Override
    public boolean create(Project proj) {
        String q = "insert into project (id,title,description,owner,goaltime) values (default,?,?,?,?)";
        try {
            PreparedStatement pst = getConn().prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, proj.getTitle());
            pst.setString(2, proj.getDescription());
            pst.setInt(3, proj.getOwner().getId());
            pst.setString(4, proj.getGoalTime());
            pst.executeUpdate();
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int projectId = (int) generatedKeys.getLong(1);
                    String relationQuery = "insert into user_projects (id,user_id,project_id) values (default,?,?)";
                    PreparedStatement pst2 = getConn().prepareStatement(relationQuery);
                    pst2.setInt(1, proj.getOwner().getId());
                    pst2.setInt(2, projectId);
                    return pst2.execute();
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
    public boolean update(Project proj) {
        String q = "update project set title=?,description=?,owner=?,goaltime=? where id=?";
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setString(1, proj.getTitle());
            pst.setString(2, proj.getDescription());
            pst.setInt(3, proj.getOwner().getId());
            pst.setString(4, proj.getGoalTime());
            pst.setInt(5, proj.getId());
            ResultSet rs = pst.executeQuery();
            pst.close();
            return rs.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String q = "delete from project where id = ?";
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            pst.close();
            return rs.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Project> findAll(int owner) {
        List<Project> list = new ArrayList<>();
        String q = "select * from project p where p.id in (select project_id from user_projects where user_id=?)";
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setInt(1, owner);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(projectFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public Project findById(int id) {
        String q = "select * from project where id = ?";
        try {
            PreparedStatement pst = getConn().prepareStatement(q);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            pst.close();
            if (rs.next()) {
                return projectFromResultSet(rs);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    private Project projectFromResultSet(ResultSet rs) throws SQLException {
        User temp = getUserDao().findById(rs.getInt("owner"));
        return new Project(rs.getInt("id"), rs.getString("title"), rs.getString("description"), temp, rs.getString("goaltime"), rs.getDate("created_at"), rs.getDate("updated_at"));
    }

    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = UserDao.getInstance();
        }
        return userDao;
    }

    @Override
    public List<Project> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
