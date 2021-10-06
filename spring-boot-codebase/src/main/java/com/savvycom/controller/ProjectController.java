package com.savvycom.controller;

import com.savvycom.convert.ProjectConvert;
import com.savvycom.dto.APIResponse;
import com.savvycom.dto.ProjectDTO;
import com.savvycom.entity.Project;
import com.savvycom.repository.ProjectRepository;
import com.savvycom.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lam.le
 * @created 27/09/2021
 */
@CrossOrigin
@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRepository projectRepository;

    private static final String message = "Success";

    @GetMapping("/getAll")
    public ResponseEntity<APIResponse<List<ProjectDTO>>> getAllProject() {
        List<ProjectDTO> projectDTOList = projectService.getAllProject();
        APIResponse<List<ProjectDTO>> response = new APIResponse<>();
        response.setMessage(message);
        response.setStatus(HttpStatus.OK.value());
        response.setData(projectDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAll/projectName")
    public ResponseEntity<APIResponse<List>> getAllProjectName() {
        List allProjectName = projectRepository.getAllProjectName();
        APIResponse<List> response = new APIResponse<>();
        response.setMessage(message);
        response.setStatus(HttpStatus.OK.value());
        response.setData(allProjectName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ProjectDTO>> getProjectByNo(@PathVariable("id") Long id) {
        Project project = projectRepository.getProjectByNo(id);
        ProjectDTO projectDTO = ProjectConvert.entityToDTO(project);
        APIResponse<ProjectDTO> response = new APIResponse<>();
        response.setData(projectDTO);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/projectpage")
    public ResponseEntity<APIResponse<Page<ProjectDTO>>> getProjectPage(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "5", required = false) Integer size,
            @RequestParam(defaultValue = "ASC", required = false) String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("no").descending());
        Page<Project> projects = projectRepository.getProject(pageable);
        Page<ProjectDTO> projectDTOPage = projects.map(ProjectConvert::entityToDTO);
        APIResponse<Page<ProjectDTO>> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage("Paging Success!");
        responseData.setData(projectDTOPage);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse<ProjectDTO>> updateProject(@Valid @RequestBody ProjectDTO projectDTO, @PathVariable Long id) {
        ProjectDTO projectDTO1 = projectService.updateProject(projectDTO, id);
        APIResponse<ProjectDTO> response = new APIResponse<>();
        response.setData(projectDTO1);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Updated Successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // search by Project Id or Project Name
    @GetMapping("search")
    public ResponseEntity<APIResponse<List<ProjectDTO>>> searchProject(@RequestParam(defaultValue = "", required = false) String search) {
        if (search.isEmpty()) {
            List<ProjectDTO> projectDTOList = projectService.getAllProject();
            APIResponse<List<ProjectDTO>> response = new APIResponse<>();
            response.setMessage(message);
            response.setStatus(HttpStatus.OK.value());
            response.setData(projectDTOList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            List<Project> projects = projectRepository.getProjectByProjectCodeContainingOrProjectNameContaining(search, search);
            APIResponse<List<ProjectDTO>> response = new APIResponse<>();
            List<ProjectDTO> projectDTOList = new ArrayList<>();
            for (Project p : projects) {
                ProjectDTO projectDTO = ProjectConvert.entityToDTO(p);
                projectDTOList.add(projectDTO);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setMessage(message);
            response.setData(projectDTOList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse<ProjectDTO>> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
        Project project = new Project();
        project.setNo(projectDTO.getNo());
        project.setProjectCode(projectDTO.getProjectCode());
        project.setProjectName(projectDTO.getProjectName());
        project.setProjectManager(projectDTO.getProjectManager());
        project.setCreateDate(projectDTO.getCreateDate());
        project.setStatus(projectDTO.getStatus());
        projectRepository.save(project);
        APIResponse<ProjectDTO> response = new APIResponse<>();
        response.setData(projectDTO);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // lọc theo project manager
    @GetMapping("/filters/{projectManager}")
    public ResponseEntity<APIResponse<List<ProjectDTO>>> getProjectByProjectManager(@PathVariable String projectManager) {
        List<Project> projects = projectRepository.getProjectByProjectName(projectManager);
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        for (Project p : projects) {
            ProjectDTO projectDTO = ProjectConvert.entityToDTO(p);
            projectDTOList.add(projectDTO);
        }
        APIResponse<List<ProjectDTO>> response = new APIResponse<>();
        response.setData(projectDTOList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Success!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // lọc theo ngày
    @GetMapping("/filterByDate")
    public ResponseEntity<APIResponse<List<ProjectDTO>>> filterByDate(@RequestParam(name = "start") String start, @RequestParam(name = "end") String end) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(start);
        Date date1 = format.parse(end);
        List<Project> projects = projectRepository.getProjectByCreateDateBetween(date, date1);
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        for (Project p : projects) {
            ProjectDTO projectDTO = ProjectConvert.entityToDTO(p);
            projectDTOList.add(projectDTO);
        }
        APIResponse<List<ProjectDTO>> response = new APIResponse<>();
        response.setData(projectDTOList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Success!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
