/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDao;
import dtos.RegisterDto;
import dtos.LoginDto;
import entity.User;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 *
 */
@Named
@SessionScoped
public class UserController implements Serializable {

    private UserDao userDao;
    private User user;
    private LoginDto loginDto;
    private RegisterDto registerDto;
    private String loginStatus;

    public UserController() {
    }

    public String register() {
        if (getUserDao().checkByUsernameOrEmail(getRegisterDto().getEmail())) {
            getRegisterDto().setErrorMessage("Bu email başka bir kullanıcı tarafından kullanılıyor.");
        } else if (getUserDao().checkByUsernameOrEmail(getRegisterDto().getUsername())) {
            getRegisterDto().setErrorMessage("Bu kullanıcı adı başka bir kullanıcı tarafından kullanılıyor.");
        } else if (getUserDao().checkByUsernameOrEmail(getRegisterDto().getUsername())) {
            getRegisterDto().setErrorMessage("Girilen parolalar uyuşmuyor.");
        } else {
            this.getUserDao().create(new User(0, getRegisterDto().getName(), getRegisterDto().getSurname(),
                    getRegisterDto().getEmail(), getRegisterDto().getUsername(), getRegisterDto().getPassword()));
            setRegisterDto(new RegisterDto());
            getRegisterDto().setErrorMessage("Başarılı bir şekilde kayıt oldunuz.");
        }
        return "index";
    }

    public String login() {
        User tmp = getUserDao().login(getLoginDto().getEmail(), getLoginDto().getPassword());
        if (tmp != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("current_user", tmp);
            return "/auth/projects?faces-redirect=true";
        } else {
            setLoginStatus("Giriş Başarısız");
        }
        return "login";
    }

    public String loginPage() {
        return "login";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RegisterDto getRegisterDto() {
        if (registerDto == null) {
            registerDto = new RegisterDto();
        }
        return registerDto;
    }

    public void setRegisterDto(RegisterDto registerDto) {
        this.registerDto = registerDto;
    }

    public LoginDto getLoginDto() {
        if (loginDto == null) {
            loginDto = new LoginDto();
        }
        return loginDto;
    }

    public void setLoginDto(LoginDto loginDto) {
        this.loginDto = loginDto;
    }

    public UserDao getUserDao() {
        if (this.userDao == null) {
            this.userDao = UserDao.getInstance();
        }
        return userDao;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

}
