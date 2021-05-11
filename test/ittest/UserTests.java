package ittest;

import dao.UserDao;
import entity.User;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

public class UserTests {

    private UserDao userDao;

    @Test
    public void registerAndLogin() {
        User user = new User();
        user.setName("test");
        user.setUsername("test");
        user.setSurname("test");
        user.setEmail("test");
        user.setPassword("parola");
        assertTrue(getDao().create(user));
        assertTrue(getDao().login(user.getEmail(), user.getPassword())!= null);
    }
    

    private UserDao getDao() {
        if (userDao == null) {
            userDao = UserDao.getInstance();

        }
        return userDao;
    }

}
