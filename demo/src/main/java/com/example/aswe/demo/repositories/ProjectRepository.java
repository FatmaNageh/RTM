package com.example.aswe.demo.repositories;
import com.example.aswe.demo.models.Project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
public interface ProjectRepository extends JpaRepository <Project,Integer>
{

    List<Project> findByNameContainingIgnoreCase(String keyword);
    
}
