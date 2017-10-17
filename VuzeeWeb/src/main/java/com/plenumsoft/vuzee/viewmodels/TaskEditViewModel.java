package com.plenumsoft.vuzee.viewmodels;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.plenumsoft.vuzee.entities.Candidate;
import com.plenumsoft.vuzee.entities.TaskState;

public class TaskEditViewModel {

	@NotNull
	private Long id;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date taskDate;

	private boolean hasRating;
	@NotNull
	private TaskState taskState;

	private Candidate candidate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public boolean isHasRating() {
		return hasRating;
	}

	public void setHasRating(boolean hasRating) {
		this.hasRating = hasRating;
	}

	public TaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

}
