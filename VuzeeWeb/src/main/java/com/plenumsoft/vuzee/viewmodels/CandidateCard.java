package com.plenumsoft.vuzee.viewmodels;

public class CandidateCard {

	private String Name;
	private Long pending;
	private Long inProgress;
	private Long done;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Long getPending() {
		return pending;
	}

	public void setPending(Long pending) {
		this.pending = pending;
	}

	public Long getInProgress() {
		return inProgress;
	}

	public void setInProgress(Long inProgress) {
		this.inProgress = inProgress;
	}

	public Long getDone() {
		return done;
	}

	public void setDone(Long done) {
		this.done = done;
	}

}
