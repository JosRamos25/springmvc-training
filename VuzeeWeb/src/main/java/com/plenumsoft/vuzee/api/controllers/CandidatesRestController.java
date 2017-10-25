package com.plenumsoft.vuzee.api.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.modelmapper.TypeToken;
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

import com.plenumsoft.vuzee.api.models.CandidateApiModel;
import com.plenumsoft.vuzee.api.models.CandidateEditApiModel;
import com.plenumsoft.vuzee.entities.Candidate;
import com.plenumsoft.vuzee.services.CandidateService;
import com.plenumsoft.vuzee.utils.ModelMapperUtil;
import com.plenumsoft.vuzee.viewmodels.CandidateCreateViewModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/candidates")
@Api(value = "Api para la gestión de Candidatos")
public class CandidatesRestController {

	private CandidateService candidateService;
	private ModelMapperUtil modelMapperUtil;

	@Autowired
	public CandidatesRestController(CandidateService candidateService, ModelMapperUtil modelMapperUtil) {
		super();
		this.candidateService = candidateService;
		this.modelMapperUtil = modelMapperUtil;
	}

	@ApiOperation(value = "Find a candidate by id", response = Candidate.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = ("/{id}"))
	ResponseEntity<CandidateApiModel> getCandidateById(@PathVariable Long id) {
		Candidate candidate = this.candidateService.findById(id);
		if (candidate == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		CandidateApiModel candidateApi = modelMapperUtil.mapObject(CandidateApiModel.class, candidate);
		candidateApi.add(linkTo(methodOn(this.getClass()).getCandidateById(id)).withSelfRel());
		return new ResponseEntity<CandidateApiModel>(candidateApi, HttpStatus.OK);
	}

	@GetMapping
	ResponseEntity<List<CandidateApiModel>> getCandidates() {

		List<Candidate> candidates = this.candidateService.getAll();
		if (candidates.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		Type listType = new TypeToken<List<CandidateApiModel>>() {
		}.getType();
		List<CandidateApiModel> candidatesApi = modelMapperUtil.mapObject(listType, candidates);

		return new ResponseEntity<List<CandidateApiModel>>(candidatesApi, HttpStatus.OK);
	}


	@PostMapping
	ResponseEntity<Long> saveCandidates(@RequestBody CandidateCreateViewModel candidateCreate) throws IOException {
		Candidate candidate = new Candidate();
		candidate.setName(candidateCreate.getName());
		candidate.setPositionApplied(candidateCreate.getPositionApplied());
		candidate.setCreatedBy("msoberanis");// TODO: hard-code
		candidate.setCreatedAt(new Date());
		if (candidateCreate.getCv() != null) {
			candidate.setCv(candidateCreate.getCv().getBytes());
		}
		return new ResponseEntity<Long>(this.candidateService.addCandidate(candidate), HttpStatus.CREATED);
	}

	@PutMapping(value = ("/{id}"))
	ResponseEntity<?> updateCandidate(@PathVariable Long id, @RequestBody CandidateEditApiModel candidateEdit) {

		Candidate candidate = candidateService.findById(id);
		candidate.setName(candidateEdit.getName());
		candidate.setPositionApplied(candidateEdit.getPositionApplied());
		this.candidateService.updateCandidate(candidate);
		return new ResponseEntity<String>("Se actualizo el candidato" + id, HttpStatus.OK);
	}

	@DeleteMapping(value = ("/{id}"))
	ResponseEntity<?> deleteCandidate(@PathVariable Long id) {
		this.candidateService.deleteCandidate(id);
		return new ResponseEntity<String>("Se elimino el candidato" + id, HttpStatus.OK);
	}
}
