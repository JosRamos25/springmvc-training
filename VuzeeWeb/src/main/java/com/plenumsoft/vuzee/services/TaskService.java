package com.plenumsoft.vuzee.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.plenumsoft.vuzee.entities.Task;

@Service
public interface TaskService {

	List<Task> getAll();

	Task findById(Long id);

	Long addTask(Task task);

	void updateTask(Task task);

	void deleteTask(Long id);
}
