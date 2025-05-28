package com.example.aswe.demo.controllers;

import com.example.aswe.demo.models.Project;
import com.example.aswe.demo.models.Status;
import com.example.aswe.demo.models.Task;
import com.example.aswe.demo.models.User;
import com.example.aswe.demo.repositories.ProjectRepository;
import com.example.aswe.demo.repositories.TaskRepository;
import com.example.aswe.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
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
        ModelAndView mav = new ModelAndView("list-project");
        List<Project> projects = this.projectRepository.findAll();
        mav.addObject("projects", projects);
        return mav;
    }

    @GetMapping("/addProject")
    public ModelAndView addProject() {
        ModelAndView mav = new ModelAndView("addProject");
        Project newProject = new Project();
        List<User> users = this.userRepository.findAll();
        mav.addObject("project", newProject);
        mav.addObject("users", users);
        return mav;
    }

    @PostMapping("/addProject")
    public String saveProject(@ModelAttribute("project") Project project,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        if (result.hasErrors()) {
            List<User> users = this.userRepository.findAll();
            model.addAttribute("users", users);
            return "addProject";
        }

        try {
            this.projectRepository.save(project);
            redirectAttributes.addFlashAttribute("successMessage", "Project added successfully!");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding project: " + e.getMessage());
            return "redirect:/addProject";
        }
    }

    @GetMapping("/editProject/{id}")
    public ModelAndView editProject(@PathVariable() int id) {
        Optional<Project> projectOpt = this.projectRepository.findById(id);
        if (projectOpt.isPresent()) {
            ModelAndView mav = new ModelAndView("editProject");
            List<User> users = this.userRepository.findAll();
            mav.addObject("project", projectOpt.get());
            mav.addObject("users", users);
            return mav;
        } else {
            ModelAndView mav = new ModelAndView("redirect:/");
            return mav;
        }
    }

    @PostMapping("/editProject/{id}")
    public String updateProject(@PathVariable("id") int id,
            @ModelAttribute("project") Project project,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        if (result.hasErrors()) {
            List<User> users = this.userRepository.findAll();
            model.addAttribute("users", users);
            return "editProject";
        }

        try {
            project.setId(id);
            this.projectRepository.save(project);
            redirectAttributes.addFlashAttribute("successMessage", "Project updated successfully!");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating project: " + e.getMessage());
            return "redirect:/editProject/" + id;
        }
    }

    @GetMapping("/deleteProject/{id}")
    public String deleteProject(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Project> projectOpt = this.projectRepository.findById(id);
            if (projectOpt.isPresent()) {
                List<Task> projectTasks = this.taskRepository.findAllByProjectId(id);
                this.taskRepository.deleteAll(projectTasks);

                this.projectRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("successMessage", "Project deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Project not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting project: " + e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/addTask")
    public ModelAndView addTask() {
        ModelAndView mav = new ModelAndView("addTask");
        List<Project> allProjects = this.projectRepository.findAll();
        mav.addObject("allProjects", allProjects);

        List<User> allUsers = this.userRepository.findAll();
        mav.addObject("allUsers", allUsers);

        Task newTask = new Task();

        mav.addObject("task", newTask);
        return mav;
    }
    // Save new task
    // @PostMapping("/addTask")
    // public String saveTask(@ModelAttribute Task task,
    // BindingResult result,
    // RedirectAttributes redirectAttributes,
    // Model model) {
    // if (result.hasErrors()) {
    // List<Project> allProjects = this.projectRepository.findAll();
    // List<User> allUsers = this.userRepository.findAll();
    // model.addAttribute("allProjects", allProjects);
    // model.addAttribute("allUsers", allUsers);
    // return "addTask";
    // }

    // try {
    // this.taskRepository.save(task);
    // redirectAttributes.addFlashAttribute("successMessage", "Task added
    // successfully!");

    // // Redirect to the project's task list if task has a project
    // if (task.getProject() != null) {
    // return "redirect:/project/" + task.getProject().getId();
    // }
    // // Otherwise redirect to home page
    // return "redirect:/";

    // } catch (Exception e) {
    // redirectAttributes.addFlashAttribute("errorMessage", "Error adding task: " +
    // e.getMessage());
    // return "redirect:/addTask";
    // }
    // }
    @PostMapping("/addTask")
    public String saveTask(@ModelAttribute Task task) {

        this.taskRepository.save(task);
        return "added";

    }

    @GetMapping("/editTask/{id}")
    public ModelAndView editTask(@PathVariable("id") int id) {
        Optional<Task> taskOpt = this.taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            ModelAndView mav = new ModelAndView("editTask");
            List<Project> allProjects = this.projectRepository.findAll();
            List<User> allUsers = this.userRepository.findAll();
            mav.addObject("task", taskOpt.get());
            mav.addObject("allProjects", allProjects);
            mav.addObject("allUsers", allUsers);
            return mav;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @PostMapping("/editTask/{id}")
    public String updateTask(@PathVariable("id") int id,

            @ModelAttribute("task") Task task,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        if (result.hasErrors()) {
            List<Project> allProjects = this.projectRepository.findAll();
            List<User> allUsers = this.userRepository.findAll();
            model.addAttribute("allProjects", allProjects);
            model.addAttribute("allUsers", allUsers);
            return "editTask";
        }

        try {
            task.setId(id);
            this.taskRepository.save(task);
            redirectAttributes.addFlashAttribute("successMessage", "Task updated successfully!");
            if (task.getProject() != null) {
                return "redirect:/project/" + task.getProject().getId();
            }
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating task: " + e.getMessage());
            return "redirect:/editTask/" + id;
        }
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable("id") int id,
            @RequestParam(value = "projectId", required = false) Long projectId,
            RedirectAttributes redirectAttributes) {
        try {
            Optional<Task> taskOpt = this.taskRepository.findById(id);
            if (taskOpt.isPresent()) {
                this.taskRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("successMessage", "Task deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Task not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting task: " + e.getMessage());
        }

        if (projectId != null) {
            return "redirect:/project/" + projectId;
        }
        return "redirect:/";
    }

    @GetMapping("/project/{id}")
    public ModelAndView getProject(@PathVariable("id") int id) {

        ModelAndView mav = new ModelAndView("list-task");
        List<Task> tasks = this.taskRepository.findAllByProjectId(id);

        mav.addObject("tasks", tasks);
        return mav;

    }

    @GetMapping("/projectDetails/{id}")
    public ModelAndView getProjectDetails(@PathVariable("id") int id) {
        Optional<Project> projectOpt = this.projectRepository.findById(id);
        if (projectOpt.isPresent()) {
            ModelAndView mav = new ModelAndView("project-details");
            Project project = projectOpt.get();
            List<Task> tasks = this.taskRepository.findAllByProjectId(id);

            long totalTasks = tasks.size();
            long completedTasks = tasks.stream().filter(task -> "COMPLETED".equals(task.getTaskStatus())).count();
            long inProgressTasks = tasks.stream().filter(task -> "IN_PROGRESS".equals(task.getTaskStatus())).count();
            long pendingTasks = tasks.stream().filter(task -> "PENDING".equals(task.getTaskStatus())).count();

            mav.addObject("project", project);
            mav.addObject("tasks", tasks);
            mav.addObject("totalTasks", totalTasks);
            mav.addObject("completedTasks", completedTasks);
            mav.addObject("inProgressTasks", inProgressTasks);
            mav.addObject("pendingTasks", pendingTasks);

            return mav;
        } else {
            return new ModelAndView("redirect:/");
        }
    }

    @PostMapping("/updateTaskStatus/{id}")
    @ResponseBody
    public String updateTaskStatus(@PathVariable("id") int id, @RequestParam("status") Status status) {
        try {
            Optional<Task> taskOpt = this.taskRepository.findById(id);
            if (taskOpt.isPresent()) {
                Task task = taskOpt.get();
                task.setTaskStatus(status);
                this.taskRepository.save(task);
                return "success";
            } else {
                return "error: Task not found";
            }
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    @GetMapping("/search")
    public ModelAndView searchProjects(@RequestParam("query") String query) {
        ModelAndView mav = new ModelAndView("list-project");
        List<Project> projects = this.projectRepository.findByNameContainingIgnoreCase(query);
        mav.addObject("projects", projects);
        mav.addObject("searchQuery", query);
        return mav;
    }
}