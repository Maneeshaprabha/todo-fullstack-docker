package com.backend.test.controller;

import com.backend.test.Entity.Task;
import com.backend.test.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Get the 5 most recent incomplete tasks
    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {
        List<Task> tasks = taskService.getTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> taskOpt = taskService.getTaskById(id);
        return taskOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new task
    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        try {
            Task savedTask = taskService.addTask(task);
            return ResponseEntity.ok(savedTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Mark a task as completed
    @PutMapping("/{id}")
    public ResponseEntity<String> markDone(@PathVariable Long id) {
        boolean updated = taskService.markDone(id);
        if (updated) {
            return ResponseEntity.ok("Task marked as done");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
