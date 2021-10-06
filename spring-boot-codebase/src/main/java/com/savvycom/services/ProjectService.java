package com.savvycom.services;

import com.savvycom.dto.ProjectDTO;

import java.util.List;

/**
 * @author lam.le
 * @created 04/10/2021
 */
public interface ProjectService {
    List<ProjectDTO> getAllProject();

    ProjectDTO updateProject(ProjectDTO projectDTO, Long id);
}
