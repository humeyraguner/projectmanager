package ittest;

import dao.ProjectDao;
import dao.UserDao;
import entity.Project;
import java.util.List;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

public class ProjectTests {

    private ProjectDao projDao;
    private UserDao userDao;

    @Test
    public void addProject() {
        Project proj = new Project();
        proj.setTitle("Test Project");
        proj.setDescription("Description");
        proj.setGoalTime("12-12-12");
        proj.setOwner(getUserDao().findById(1));
        assertTrue(getDao().create(proj));
    }

    @Test
    public void updateProject() {
        Project proj = new Project();
        proj.setTitle("Edit Project");
        proj.setDescription("EditDescription");
        proj.setGoalTime("12-12-21");
        proj.setOwner(getUserDao().findById(1));
        assertTrue(getDao().update(proj));
    }

    @Test
    public void listProject() {
        List<Project> projects = getDao().findAll();
        assertTrue(!projects.isEmpty());
    }

    @Test
    public void silProject() {
        assertTrue(getDao().delete(1));
    }

    private ProjectDao getDao() {
        if (projDao == null) {
            projDao = ProjectDao.getInstance();

        }
        return projDao;
    }

    private UserDao getUserDao() {
        if (userDao == null) {
            userDao = UserDao.getInstance();

        }
        return userDao;
    }
}
