package com.plenumsoft.vuzee.viewmodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CandidateEditViewModel {

	@NotNull
	private Long id;
	@NotNull
	@Size(min = 2, max = 200)
	private String name;
	@NotNull
	@Size(min = 2, max = 20)
	private String positionApplied;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPositionApplied() {
		return positionApplied;
	}

	public void setPositionApplied(String positionApplied) {
		this.positionApplied = positionApplied;
	}

}
