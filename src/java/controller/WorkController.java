/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.WorkDao;
import entity.Project;
import entity.Status;
import entity.User;
import entity.Work;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 */
@Named
@SessionScoped
public class WorkController implements Serializable {

    private WorkDao workDao;
    private List<Work> todoWorkList = null;
    private List<Work> doingWorkList = null;
    private List<Work> doneWorkList = null;
    private int projectId = 0;
    private Work work;
    private User user;

    public WorkController() {
    }

    public WorkDao getWorkDao() {
        if (workDao == null) {
            workDao = WorkDao.getInstance();
        }
        return workDao;
    }

    public String changeStatus(Work work, int status) {
        getWorkDao().changeStatus(work.getId(), status);
        return "works";
    }

    public String worksPage(int projId) {
        if (user == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            user = (User) facesContext.getExternalContext().getSessionMap().get("current_user");
        }
        setProjectId(projId);
        return "works";
    }

    public String workPage(int workId) {
        if (workId != 0) {
            setWork(getWorkDao().findById(workId));
        } else {
            work = new Work();
            work.setStatus(Status.TODO);
            if (user == null) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                user = (User) facesContext.getExternalContext().getSessionMap().get("current_user");
            }
            work.setAsSignedAt(user);
        }
        work.setProject(new Project());
        work.getProject().setId(getProjectId());
        return "work";
    }

    public String saveWork() {
        User temp = user;
        temp.setUsername(work.getAsSignedAt().getUsername());
        work.setAsSignedAt(temp);
        if (getWork().getId() == 0) {
            getWorkDao().create(getWork());
        } else {
            getWorkDao().update(getWork());
        }
        work = new Work();
        work.setStatus(Status.TODO);
        return "works";
    }

    public List<Work> getTodoWorkList() {
        if (user == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            user = (User) facesContext.getExternalContext().getSessionMap().get("current_user");
        }
        todoWorkList = getWorkDao().findAll(user.getId(), Status.TODO, getProjectId());
        return todoWorkList;
    }

    public void setWorkList(List<Work> todoWorkList) {
        this.todoWorkList = todoWorkList;
    }

    public List<Work> getDoingWorkList() {
        if (user == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            user = (User) facesContext.getExternalContext().getSessionMap().get("current_user");
        }
        doingWorkList = getWorkDao().findAll(user.getId(), Status.DOING, getProjectId());
        return doingWorkList;
    }

    public void setDoingWorkList(List<Work> doingWorkList) {
        this.doingWorkList = doingWorkList;
    }

    public List<Work> getDoneWorkList() {
        if (user == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            user = (User) facesContext.getExternalContext().getSessionMap().get("current_user");
        }
        doneWorkList = getWorkDao().findAll(user.getId(), Status.DONE, getProjectId());
        return doneWorkList;
    }

    public void setDoneWorkList(List<Work> doneWorkList) {
        this.doneWorkList = doneWorkList;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Work getWork() {
        if (work == null) {
            work = new Work();
        }
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

}
