package controller;

import dao.ProjectDao;
import entity.Project;
import entity.User;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class ProjectController implements Serializable {

    private ProjectDao pdao;
    private Project project;
    private List<Project> projectList;
    private User user;

    public ProjectController() {
    }

    public ProjectDao getPdao() {
        if (pdao == null) {
            pdao = ProjectDao.getInstance();
        }
        return pdao;
    }

    public String projectPage(Project project) {
        if (project != null) {
            this.project = project;
        } else {
            this.project = new Project();
        }
        return "project";
    }

    public Project getProject() {
        if (project == null) {
            project = new Project();
        }
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String createProject() {
        if (project.getId() == 0) {
            project.setOwner(user);
            getPdao().create(project);
        } else {
            getPdao().update(project);
        }

        return "projects";
    }

    public String delete(int id) {
        getPdao().delete(id);
        return "projects";
    }

    public List<Project> getProjectList() {
        if (user == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            user = (User) facesContext.getExternalContext().getSessionMap().get("current_user");
        }
        projectList = getPdao().findAll(user.getId());
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
