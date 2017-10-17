package com.plenumsoft.vuzee.viewmodels;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.plenumsoft.vuzee.entities.Candidate;

public class TaskCreateViewModel {

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date taskDate;

	private boolean hasRating;
	@NotNull
	private Candidate candidate;

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

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
}
