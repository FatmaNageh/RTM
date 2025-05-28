package com.example.aswe.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aswe.demo.models.Task;

public interface TaskRepository  extends JpaRepository<Task,Integer>{


    List<Task> findAllByProjectId(int id);

    List<Task> findByNameContainingIgnoreCase(String keyword);

    List<Task> findByProjectId(int id);
}
