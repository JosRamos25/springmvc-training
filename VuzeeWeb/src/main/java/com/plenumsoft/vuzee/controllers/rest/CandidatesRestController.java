package com.plenumsoft.vuzee.controllers.rest;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plenumsoft.vuzee.entities.Candidate;
import com.plenumsoft.vuzee.services.CandidateService;
import com.plenumsoft.vuzee.viewmodels.CandidateCreateViewModel;
import com.plenumsoft.vuzee.viewmodels.CandidateEditViewModel;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidatesRestController {

	private CandidateService candidateService;

	@Autowired
	public CandidatesRestController(CandidateService candidateService) {
		super();
		this.candidateService = candidateService;
	}

	@GetMapping(value = ("/{id}"))
	ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
		Candidate candidate = this.candidateService.findById(id);
		if (candidate == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Candidate>(candidate, HttpStatus.OK);
	}

	@GetMapping
	ResponseEntity<List<Candidate>> getCandidates() {

		List<Candidate> candidates = this.candidateService.getAll();
		if (candidates.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Candidate>>(candidates, HttpStatus.OK);
	}

	@PostMapping
	ResponseEntity<Long> saveCandidates(@RequestBody CandidateCreateViewModel candidateCreateViewModel)
			throws IOException {
		Candidate candidate = new Candidate();
		candidate.setName(candidateCreateViewModel.getName());
		candidate.setPositionApplied(candidateCreateViewModel.getPositionApplied());
		candidate.setCreatedBy("msoberanis");// TODO: hard-code
		candidate.setCreatedAt(new Date());
		if (candidateCreateViewModel.getCv() != null) {
			candidate.setCv(candidateCreateViewModel.getCv().getBytes());
		}
		return new ResponseEntity<Long>(this.candidateService.addCandidate(candidate), HttpStatus.CREATED);
	}

	@PutMapping(value = ("/{id}"))
	ResponseEntity<?> updateCandidate(@PathVariable Long id,
			@RequestBody CandidateEditViewModel candidateEditViewModel) {

		Candidate candidate = candidateService.findById(id);
		//
		// if (cv != null) {
		// candidate.setCv(cv.getBytes());
		// }
		candidate.setName(candidateEditViewModel.getName());
		candidate.setPositionApplied(candidateEditViewModel.getPositionApplied());
		this.candidateService.updateCandidate(candidate);
		return new ResponseEntity<String>("Se actualizo el candidato" + id, HttpStatus.OK);
	}

	@DeleteMapping(value = ("/{id}"))
	ResponseEntity<?> deleteCandidate(@PathVariable Long id) {
		this.candidateService.deleteCandidate(id);
		return new ResponseEntity<String>("Se elimino el candidato" + id, HttpStatus.OK);
	}
}
