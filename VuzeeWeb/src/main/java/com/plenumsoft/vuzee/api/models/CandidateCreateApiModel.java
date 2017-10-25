package com.plenumsoft.vuzee.api.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class CandidateCreateApiModel {
	@NotNull
	@Size(min = 2, max = 200)
	@ApiModelProperty(notes = "Candidate's name", required = true)
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
