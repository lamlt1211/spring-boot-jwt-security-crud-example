package com.savvycom.services.impl;

import com.savvycom.convert.ProjectConvert;
import com.savvycom.dto.ProjectDTO;
import com.savvycom.entity.Project;
import com.savvycom.exception.NotFoundException;
import com.savvycom.repository.ProjectRepository;
import com.savvycom.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author lam.le
 * @created 04/10/2021
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Override
    public List<ProjectDTO> getAllProject() {
        List<Project> projectList = projectRepository.findAll();
        if (projectList.isEmpty()) {
            throw new NotFoundException("Not found Project");
        }
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        for (Project project : projectList) {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setNo(project.getNo());
            projectDTO.setProjectCode(project.getProjectCode());
            projectDTO.setProjectName(project.getProjectName());
            projectDTO.setProjectManager(project.getProjectManager());
            projectDTO.setCreateDate(project.getCreateDate());
            projectDTO.setStatus(project.getStatus());
            projectDTOList.add(projectDTO);
        }
        return projectDTOList;
    }

    @Override
    public ProjectDTO updateProject(ProjectDTO projectDTO, Long id) {
        Optional<Project> p = projectRepository.findProjectByNo(id);
        if (p.isPresent()) {
            Project project = ProjectConvert.DTOToEntity(projectDTO);
            project.setNo(id);
            return ProjectConvert.entityToDTO(projectRepository.save(project));
        }
        throw new NotFoundException("Id not found");
    }
}
