package com.savvycom.repository;

import com.savvycom.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author lam.le
 * @created 04/10/2021
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT DISTINCT p.projectName FROM Project p")
    List getAllProjectName();

    Project getProjectByNo(Long id);

    @Query("SELECT c FROM Customer c")
    Page<Project> getProject(Pageable pageable);

    Optional<Project> findProjectByNo(Long id);

    List<Project> getProjectByProjectName(String projectName);

    List<Project> getProjectByProjectCodeContainingOrProjectNameContaining(String id, String name);

    @Query("SELECT p FROM Project p WHERE p.createDate BETWEEN:date1 AND:date2")
    List<Project> getProjectByCreateDateBetween(@Param("date1") Date start, @Param("date2") Date end);
}
