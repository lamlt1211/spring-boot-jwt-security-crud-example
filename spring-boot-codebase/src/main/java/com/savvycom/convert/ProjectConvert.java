package com.savvycom.convert;

import com.savvycom.dto.ProjectDTO;
import com.savvycom.entity.Project;

/**
 * @author lam.le
 * @created 04/10/2021
 */
public class ProjectConvert {
    public static ProjectDTO entityToDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setNo(project.getNo());
        projectDTO.setProjectCode(project.getProjectCode());
        projectDTO.setProjectName(project.getProjectName());
        projectDTO.setProjectManager(project.getProjectManager());
        projectDTO.setStatus(project.getStatus());
        projectDTO.setCreateDate(project.getCreateDate());
        return projectDTO;
    }

    public static Project DTOToEntity(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setNo(projectDTO.getNo());
        project.setProjectCode(projectDTO.getProjectCode());
        project.setProjectName(projectDTO.getProjectName());
        project.setProjectManager(projectDTO.getProjectManager());
        project.setStatus(projectDTO.getStatus());
        project.setCreateDate(projectDTO.getCreateDate());
        return project;
    }
}
