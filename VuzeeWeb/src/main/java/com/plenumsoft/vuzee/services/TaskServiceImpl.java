package com.plenumsoft.vuzee.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.plenumsoft.vuzee.entities.Task;
import com.plenumsoft.vuzee.repositories.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

	private TaskRepository taskRepository;

	public TaskServiceImpl(TaskRepository taskRepository) {
		super();
		this.taskRepository = taskRepository;
	}

	@Override
	public List<Task> getAll() {
		// TODO Auto-generated method stub
		return (List<Task>) this.taskRepository.findAll();
	}

	@Override
	public Task findById(Long id) {
		// TODO Auto-generated method stub
		return this.taskRepository.findOne(id);
	}

	@Override
	public Long addTask(Task task) {
		// TODO Auto-generated method stub
		return this.taskRepository.save(task).getId();
	}

	@Override
	public void updateTask(Task task) {
		// TODO Auto-generated method stub
		this.taskRepository.save(task);
	}

	@Override
	public void deleteTask(Long id) {
		// TODO Auto-generated method stub
		this.taskRepository.delete(id);
	}

}
