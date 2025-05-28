package com.example.aswe.demo.controllers;

import com.example.aswe.demo.models.Project;
import com.example.aswe.demo.models.Task;
import com.example.aswe.demo.repositories.ProjectRepository;
import com.example.aswe.demo.repositories.TaskRepository;
import com.example.aswe.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional; // Import Optional

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ModelAndView getProjects() {
        ModelAndView mav = new ModelAndView("list-project.html");
        List<Project> projects = this.projectRepository.findAll();
        mav.addObject("projects", projects);
        return mav;
    }

    @GetMapping("addProject")
    public ModelAndView addProject() {
        ModelAndView mav = new ModelAndView("addProject.html");
        Project newProject = new Project();
        mav.addObject("project", newProject);
        return mav;
    }

    @PostMapping("addProject")
    public String saveProject(@ModelAttribute Project project) {
        this.projectRepository.save(project);
        return "Added"; // Consider redirecting to the project list or the new project's detail page
    }

    // --- Modified addTask GET method ---
    @GetMapping("addTask/{projectId}") // Added projectId to the path
    public ModelAndView addTask() {
        ModelAndView mav = new ModelAndView("addTask.html");
        List<Project> allProjects = this.projectRepository.findAll();
        mav.addObject("allProjects", allProjects);
        mav.addObject("allUsers", userRepository.findAll());
        Task newTask = new Task();

        mav.addObject("task", newTask);
        return mav;
    }

    // --- Modified addTask POST method ---
    @PostMapping("addTask")
    public String saveTask(@ModelAttribute Task task) {

        this.taskRepository.save(task);
        return "Task Added to Project "; // Consider redirecting to the project's task list

    }

    @GetMapping("/project/{id}")
    public ModelAndView getProject(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("list-task.html");
        // It's good practice to ensure the project exists before fetching its tasks
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            List<Task> tasks = this.taskRepository.findByProjectId(id); // Assuming you have this method in
                                                                        // TaskRepository
            mav.addObject("project", project.get()); // Add the project object to the model
            mav.addObject("tasks", tasks);
        } else {
            // Handle case where project is not found, e.g., redirect to project list or
            // show error
            mav.setViewName("error-page"); // You'd need an error-page.html
            mav.addObject("message", "Project not found!");
        }
        return mav;
    }

    // Add methods for updating and deleting projects and tasks
    // For example, to update a project:
    @GetMapping("/editProject/{id}")
    public ModelAndView editProject(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("editProject.html");
        Optional<Project> project = projectRepository.findById(id);
        project.ifPresent(p -> mav.addObject("project", p));
        return mav;
    }

    @PostMapping("/editProject")
    public String updateProject(@ModelAttribute Project project) {
        this.projectRepository.save(project); // save will update if ID exists
        return "Project Updated";
    }

    @GetMapping("/deleteProject/{id}")
    public String deleteProject(@PathVariable("id") int id) {
        projectRepository.deleteById(id);
        return "Project Deleted";
    }

    // Similar methods for tasks (editTask, deleteTask)
    @GetMapping("/editTask/{id}")
    public ModelAndView editTask(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("editTask.html");
        Optional<Task> task = taskRepository.findById(id);
        task.ifPresent(t -> mav.addObject("task", t));
        mav.addObject("allProjects", projectRepository.findAll()); // Needed for selecting a new project
        return mav;
    }

    @PostMapping("/editTask")
    public String updateTask(@ModelAttribute Task task, @RequestParam("projectId") int projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            task.setProject(project.get());
            this.taskRepository.save(task);
            return "Task Updated";
        } else {
            return "Error: Project not found for task update!";
        }
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable("id") int id) {
        taskRepository.deleteById(id);
        return "Task Deleted";
    }
}