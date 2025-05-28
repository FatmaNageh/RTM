// Project.java
package com.example.aswe.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name;
    
    private String description;
      @Enumerated(EnumType.STRING)
    private Status projectStatus; // ACTIVE, COMPLETED, ON_HOLD, CANCELLED
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    @Enumerated(EnumType.STRING) // or EnumType.ORDINAL
    private  Priority projectPriority; // HIGH, MEDIUM, LOW
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
   
    @ManyToOne
    private User projectManager;
    

    public Project() {
    }

    public Project(int id, String name, String description, Status projectStatus, LocalDateTime startDate, LocalDateTime endDate, Priority projectPriority, LocalDateTime createdAt, LocalDateTime updatedAt, User projectManager) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectStatus = projectStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectPriority = projectPriority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.projectManager = projectManager;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getProjectStatus() {
        return this.projectStatus;
    }

    public void setProjectStatus(Status projectStatus) {
        this.projectStatus = projectStatus;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Priority getProjectPriority() {
        return this.projectPriority;
    }

    public void setProjectPriority(Priority projectPriority) {
        this.projectPriority = projectPriority;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getProjectManager() {
        return this.projectManager;
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }

    public Project id(int id) {
        setId(id);
        return this;
    }

    public Project name(String name) {
        setName(name);
        return this;
    }

    public Project description(String description) {
        setDescription(description);
        return this;
    }

    public Project projectStatus(Status projectStatus) {
        setProjectStatus(projectStatus);
        return this;
    }

    public Project startDate(LocalDateTime startDate) {
        setStartDate(startDate);
        return this;
    }

    public Project endDate(LocalDateTime endDate) {
        setEndDate(endDate);
        return this;
    }

    public Project projectPriority(Priority projectPriority) {
        setProjectPriority(projectPriority);
        return this;
    }

    public Project createdAt(LocalDateTime createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

    public Project updatedAt(LocalDateTime updatedAt) {
        setUpdatedAt(updatedAt);
        return this;
    }

    public Project projectManager(User projectManager) {
        setProjectManager(projectManager);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return id == project.id && Objects.equals(name, project.name) && Objects.equals(description, project.description) && Objects.equals(projectStatus, project.projectStatus) && Objects.equals(startDate, project.startDate) && Objects.equals(endDate, project.endDate) && Objects.equals(projectPriority, project.projectPriority) && Objects.equals(createdAt, project.createdAt) && Objects.equals(updatedAt, project.updatedAt) && Objects.equals(projectManager, project.projectManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, projectStatus, startDate, endDate, projectPriority, createdAt, updatedAt, projectManager);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", projectStatus='" + getProjectStatus() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", projectPriority='" + getProjectPriority() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", projectManager='" + getProjectManager() + "'" +
            "}";
    }
  

    // // Default constructor
    // public Project() {
    //     this.createdAt = LocalDateTime.now();
    //     this.updatedAt = LocalDateTime.now();
    // }
    
    // // Constructor with essential fields
    // public Project(String name, String description) {
    //     this();
    //     this.name = name;
    //     this.description = description;
    // }
    
    // // Getters and Setters
    // public int getId() {
    //     return id;
    // }
    
    // public void setId(int id) {
    //     this.id = id;
    // }
    
    // public String getName() {
    //     return name;
    // }
    
    // public void setName(String name) {
    //     this.name = name;
    //     this.updatedAt = LocalDateTime.now();
    // }
    
    // public String getDescription() {
    //     return description;
    // }
    
    // public void setDescription(String description) {
    //     this.description = description;
    //     this.updatedAt = LocalDateTime.now();
    // }
    
    // public String getStatus() {
    //     return status;
    // }
    
    // public void setStatus(String status) {
    //     this.status = status;
    //     this.updatedAt = LocalDateTime.now();
    // }
    
    // public LocalDateTime getStartDate() {
    //     return startDate;
    // }
    
    // public void setStartDate(LocalDateTime startDate) {
    //     this.startDate = startDate;
    //     this.updatedAt = LocalDateTime.now();
    // }
    
    // public LocalDateTime getEndDate() {
    //     return endDate;
    // }
    
    // public void setEndDate(LocalDateTime endDate) {
    //     this.endDate = endDate;
    //     this.updatedAt = LocalDateTime.now();
    // }
    
    // public String getPriority() {
    //     return priority;
    // }
    
    // public void setPriority(String priority) {
    //     this.priority = priority;
    //     this.updatedAt = LocalDateTime.now();
    // }
    
    // public LocalDateTime getCreatedAt() {
    //     return createdAt;
    // }
    
    // public void setCreatedAt(LocalDateTime createdAt) {
    //     this.createdAt = createdAt;
    // }
    
    // public LocalDateTime getUpdatedAt() {
    //     return updatedAt;
    // }
    
    // public void setUpdatedAt(LocalDateTime updatedAt) {
    //     this.updatedAt = updatedAt;
    // }
    
    // public List<Task> getTasks() {
    //     return tasks;
    // }
    
    // public void setTasks(List<Task> tasks) {
    //     this.tasks = tasks;
    // }
    
    // // Utility methods
    // @PreUpdate
    // public void preUpdate() {
    //     this.updatedAt = LocalDateTime.now();
    // }
    
    // @Override
    // public String toString() {
    //     return "Project{" +
    //             "id=" + id +
    //             ", name='" + name + '\'' +
    //             ", description='" + description + '\'' +
    //             ", status='" + status + '\'' +
    //             ", priority='" + priority + '\'' +
    //             ", createdAt=" + createdAt +
    //             '}';
    // }
}
