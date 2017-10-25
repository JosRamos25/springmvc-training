package com.plenumsoft.vuzee.viewmodels;

import java.util.List;

public class CandidatePageViewModel {
	private int draw;
	private long recordsTotal;
	private long recordsFiltered;
	private List<CandidateViewModel> data;

	public CandidatePageViewModel(int draw, long recordsTotal, long recordsFiltered, List<CandidateViewModel> data) {
		super();
		this.draw = draw;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		this.data = data;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<CandidateViewModel> getData() {
		return data;
	}

	public void setData(List<CandidateViewModel> data) {
		this.data = data;
	}

}
