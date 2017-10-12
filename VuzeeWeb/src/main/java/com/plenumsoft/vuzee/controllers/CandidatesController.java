package com.plenumsoft.vuzee.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.plenumsoft.vuzee.entities.Candidate;
import com.plenumsoft.vuzee.services.CandidateService;
import com.plenumsoft.vuzee.viewmodels.CandidateCreateViewModel;
import com.plenumsoft.vuzee.viewmodels.CandidateEditViewModel;

@Controller
@RequestMapping(value = { "/candidates" })
public class CandidatesController {
	String prefix = "candidates/";

	private CandidateService candidateService;

	public CandidatesController() {
	}

	@Autowired
	public CandidatesController(CandidateService candidateService) {
		super();
		this.candidateService = candidateService;
	}

	@RequestMapping(value = { "/", "" })
	public ModelAndView Index() {
		ModelAndView mv = new ModelAndView(prefix + "index");
		List<Candidate> candidates = candidateService.getAll();
		mv.addObject("candidates", candidates);
		return mv;
	}

	@RequestMapping(value = { "/create" })
	public String PrepareCreate(CandidateCreateViewModel candidateCreateViewModel) {
		return prefix + "create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String PostCreateCandidate(@Valid CandidateCreateViewModel candidateCreateViewModel,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return prefix + "create";
		}
		Candidate candidate = new Candidate();
		candidate.setName(candidateCreateViewModel.getName());
		candidate.setPositionApplied(candidateCreateViewModel.getPositionApplied());
		candidate.setCreatedBy("msoberanis");// TODO: hard-code
		candidate.setCreatedAt(new Date());
		candidateService.addCandidate(candidate);
		return "redirect:/candidates";
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public String PrepareEdit(@PathVariable("id") Long id, Model model) {
		Candidate candidate = candidateService.findById(id);
		model.addAttribute("candidateEditViewModel", candidate);
		return prefix + "edit";
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.PUT)
	public String PutEdit(@Valid CandidateEditViewModel candidateEditViewModel, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			return prefix + "edit";
		}
		Long id = candidateEditViewModel.getId();
		Candidate candidate = candidateService.findById(id);
		candidate.setName(candidateEditViewModel.getName());
		candidate.setPositionApplied(candidateEditViewModel.getPositionApplied());

		candidateService.updateCandidate(candidate);
		return "redirect:/candidates";
	}

	@RequestMapping(value = { "/delete/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCandidate(@PathVariable Long id, Model model) {
		if (id != null) {
			candidateService.deleteCandidate(id);
		} else {
			return new ResponseEntity<>("Identificador vacio", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
