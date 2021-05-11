package ittest;

import dao.WorkDao;
import dao.UserDao;
import entity.Work;
import java.util.List;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

public class WorkTests {

    private WorkDao workDao;
    private UserDao userDao;

    @Test
    public void addWork() {
        Work work = new Work();
        work.setTitle("Test Work");
        work.setDescription("Description");
        assertTrue(getDao().create(work));
    }

    @Test
    public void updateWork() {
        Work work = new Work();
        work.setTitle("Edit Work");
        work.setDescription("EditDescription");
        assertTrue(getDao().update(work));
    }

    @Test
    public void listWork() {
        List<Work> works = getDao().findAll();
        assertTrue(!works.isEmpty());
    }

    @Test
    public void deleteWork() {
        assertTrue(getDao().delete(1));
    }

    private WorkDao getDao() {
        if (workDao == null) {
            workDao = WorkDao.getInstance();

        }
        return workDao;
    }

    private UserDao getUserDao() {
        if (userDao == null) {
            userDao = UserDao.getInstance();

        }
        return userDao;
    }

}
