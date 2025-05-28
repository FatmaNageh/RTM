
// Task.java
package com.example.aswe.demo.models;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
  
    private String name;
    
    private String description;
    
      @Enumerated(EnumType.STRING) 
    private  Status TaskStatus ; // TODO, IN_PROGRESS, DONE, CANCELLED
    
      @Enumerated(EnumType.STRING) 
    private  Priority TaskPriority;// HIGH, MEDIUM, LOW
    // @ManyToOne
    // private User assignedTo;

    
    
    private LocalDateTime dueDate;
    
    private LocalDateTime completedAt;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

    
    @ManyToOne
    private Project project;

    public Task() {
    }

    public Task(int id, String name, String description, Status TaskStatus, Priority TaskPriority, LocalDateTime dueDate, LocalDateTime completedAt, LocalDateTime createdAt, LocalDateTime updatedAt, Project project) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.TaskStatus = TaskStatus;
        this.TaskPriority = TaskPriority;
        this.dueDate = dueDate;
        this.completedAt = completedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.project = project;
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

    public Status getTaskStatus() {
        return this.TaskStatus;
    }

    public void setTaskStatus(Status TaskStatus) {
        this.TaskStatus = TaskStatus;
    }

    public Priority getTaskPriority() {
        return this.TaskPriority;
    }

    public void setTaskPriority(Priority TaskPriority) {
        this.TaskPriority = TaskPriority;
    }

    public LocalDateTime getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCompletedAt() {
        return this.completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
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

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Task id(int id) {
        setId(id);
        return this;
    }

    public Task name(String name) {
        setName(name);
        return this;
    }

    public Task description(String description) {
        setDescription(description);
        return this;
    }

    public Task TaskStatus(Status TaskStatus) {
        setTaskStatus(TaskStatus);
        return this;
    }

    public Task TaskPriority(Priority TaskPriority) {
        setTaskPriority(TaskPriority);
        return this;
    }

    public Task dueDate(LocalDateTime dueDate) {
        setDueDate(dueDate);
        return this;
    }

    public Task completedAt(LocalDateTime completedAt) {
        setCompletedAt(completedAt);
        return this;
    }

    public Task createdAt(LocalDateTime createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

    public Task updatedAt(LocalDateTime updatedAt) {
        setUpdatedAt(updatedAt);
        return this;
    }

    public Task project(Project project) {
        setProject(project);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(TaskStatus, task.TaskStatus) && Objects.equals(TaskPriority, task.TaskPriority) && Objects.equals(dueDate, task.dueDate) && Objects.equals(completedAt, task.completedAt) && Objects.equals(createdAt, task.createdAt) && Objects.equals(updatedAt, task.updatedAt) && Objects.equals(project, task.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, TaskStatus, TaskPriority, dueDate, completedAt, createdAt, updatedAt, project);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", TaskStatus='" + getTaskStatus() + "'" +
            ", TaskPriority='" + getTaskPriority() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", completedAt='" + getCompletedAt() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", project='" + getProject() + "'" +
            "}";
    }

    // public Task() {
    //     this.createdAt = LocalDateTime.now();
    //     this.updatedAt = LocalDateTime.now();
    // }
    

    // public Task(String name, String description, int projectId) {
    //     this();
    //     this.name = name;
    //     this.description = description;
    //     this.projectId = projectId;
    // }
   
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
        
    //     // Automatically set completed date when status changes to DONE
    //     if ("DONE".equals(status) && this.completedAt == null) {
    //         this.completedAt = LocalDateTime.now();
    //     } else if (!"DONE".equals(status)) {
    //         this.completedAt = null;
    //     }
    // }
    
    // public String getPriority() {
    //     return priority;
    // }
    
    // public void setPriority(String priority) {
    //     this.priority = priority;
    //     this.updatedAt = LocalDateTime.now();
    // }
    
    // public String getAssignedTo() {
    //     return assignedTo;
    // }
    
    // public void setAssignedTo(String assignedTo) {
    //     this.assignedTo = assignedTo;
    //     this.updatedAt = LocalDateTime.now();
    // }
    
    // public LocalDateTime getDueDate() {
    //     return dueDate;
    // }
    
    // public void setDueDate(LocalDateTime dueDate) {
    //     this.dueDate = dueDate;
    //     this.updatedAt = LocalDateTime.now();
    // }
    
    // public LocalDateTime getCompletedAt() {
    //     return completedAt;
    // }
    
    // public void setCompletedAt(LocalDateTime completedAt) {
    //     this.completedAt = completedAt;
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
    
    // public int getProjectId() {
    //     return projectId;
    // }
    
    // public void setProjectId(int projectId) {
    //     this.projectId = projectId;
    //     this.updatedAt = LocalDateTime.now();
    // }
    
    // public Project getProject() {
    //     return project;
    // }
    
    // public void setProject(Project project) {
    //     this.project = project;
    //     if (project != null) {
    //         this.projectId = project.getId();
    //     }
    // }
    
    // // Utility methods
    // @PreUpdate
    // public void preUpdate() {
    //     this.updatedAt = LocalDateTime.now();
    // }
    
    // public boolean isOverdue() {
    //     return dueDate != null && LocalDateTime.now().isAfter(dueDate) && !"DONE".equals(status);
    // }
    
    // public boolean isCompleted() {
    //     return "DONE".equals(status);
    // }
    
    // @Override
    // public String toString() {
    //     return "Task{" +
    //             "id=" + id +
    //             ", name='" + name + '\'' +
    //             ", description='" + description + '\'' +
    //             ", status='" + status + '\'' +
    //             ", priority='" + priority + '\'' +
    //             ", projectId=" + projectId +
    //             ", createdAt=" + createdAt +
    //             '}';
    // }
}