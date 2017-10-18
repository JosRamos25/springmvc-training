package com.plenumsoft.vuzee.viewmodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;

public class CandidateCreateViewModel {
	@NotNull
	@Size(min = 2, max = 200)
	@ApiModelProperty(notes = "Candidate's name", required = true)
	private String name;
	@NotNull
	@Size(min = 2, max = 20)
	private String positionApplied;
	private MultipartFile cv;

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

	public MultipartFile getCv() {
		return cv;
	}

	public void setCv(MultipartFile cv) {
		this.cv = cv;
	}

}
