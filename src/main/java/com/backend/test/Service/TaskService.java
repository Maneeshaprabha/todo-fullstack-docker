package com.backend.test.Service;

import com.backend.test.Entity.Task;
import com.backend.test.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasks() {
        return taskRepository.findTop5ByIsCompletedFalseOrderByCreatedAtDesc();
    }

    public Task addTask(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        task.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        task.setIsCompleted(false);
        return taskRepository.save(task);
    }

    public boolean markDone(Long id) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setIsCompleted(true);
            taskRepository.save(task);
            return true;
        } else {
            return false;
        }
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
}
