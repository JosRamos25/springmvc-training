package com.plenumsoft.vuzee.api.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CandidateEditApiModel {

	@NotNull
	private Long id;
	@NotNull
	@Size(min = 2, max = 200)
	private String name;
	@NotNull
	@Size(min = 2, max = 20)
	private String positionApplied;

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
