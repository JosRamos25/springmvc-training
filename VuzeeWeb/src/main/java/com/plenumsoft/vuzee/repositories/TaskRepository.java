package com.plenumsoft.vuzee.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.plenumsoft.vuzee.entities.Candidate;
import com.plenumsoft.vuzee.entities.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
	List<Task> findByCandidate(Candidate candidate);
}
