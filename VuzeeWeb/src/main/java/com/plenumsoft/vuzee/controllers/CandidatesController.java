package com.plenumsoft.vuzee.controllers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.plenumsoft.vuzee.api.models.CandidateApiModel;
import com.plenumsoft.vuzee.entities.Candidate;
import com.plenumsoft.vuzee.services.CandidateService;
import com.plenumsoft.vuzee.utils.ModelMapperUtil;
import com.plenumsoft.vuzee.viewmodels.CandidateCreateViewModel;
import com.plenumsoft.vuzee.viewmodels.CandidateEditViewModel;
import com.plenumsoft.vuzee.viewmodels.CandidatePageViewModel;
import com.plenumsoft.vuzee.viewmodels.CandidateViewModel;

@Controller
@RequestMapping(value = { "/candidates" })
public class CandidatesController {
	private final String prefix = "candidates/";

	private CandidateService candidateService;
	private ModelMapperUtil modelMapperUtil;

	public CandidatesController() {
	}

	@Autowired
	public CandidatesController(CandidateService candidateService, ModelMapperUtil modelMapperUtil) {
		super();
		this.candidateService = candidateService;
		this.modelMapperUtil = modelMapperUtil;
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

	@GetMapping(value = ("/page"))
	@ResponseBody
	ResponseEntity<CandidatePageViewModel> getCandidates(@RequestParam("numPage") int numPage,
			@RequestParam("length") int length, @RequestParam("draw") int draw) {

		Page<Candidate> page = this.candidateService.getAll(new PageRequest(numPage, length));
		Type listType = new TypeToken<List<CandidateViewModel>>() {
		}.getType();
		List<CandidateViewModel> candidates = modelMapperUtil.mapObject(listType, page.getContent());
		CandidatePageViewModel pageView = new CandidatePageViewModel(draw, page.getTotalElements(), page.getTotalElements(), candidates);
		
		if (candidates.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CandidatePageViewModel>(pageView, HttpStatus.OK);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String PostCreateCandidate(@Valid CandidateCreateViewModel candidateCreateViewModel, Model model,
			RedirectAttributes redirectAttributes, BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			model.addAttribute("message", "Favor de verificar las entradas");
			return prefix + "create";
		}
		Candidate candidate = new Candidate();
		candidate.setName(candidateCreateViewModel.getName());
		candidate.setPositionApplied(candidateCreateViewModel.getPositionApplied());
		candidate.setCreatedBy("msoberanis");// TODO: hard-code
		candidate.setCreatedAt(new Date());
		if (candidateCreateViewModel.getCv() != null) {
			candidate.setCv(candidateCreateViewModel.getCv().getBytes());
		}
		candidateService.addCandidate(candidate);
		redirectAttributes.addFlashAttribute("message", "Se ha creado correctamente el candidato");
		return "redirect:/candidates";
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public String PrepareEdit(@PathVariable("id") Long id, Model model) {
		Candidate candidate = candidateService.findById(id);
		CandidateEditViewModel cadidateEdit = new CandidateEditViewModel();
		cadidateEdit.setId(candidate.getId());
		cadidateEdit.setCreatedAt(candidate.getCreatedAt());
		cadidateEdit.setCreatedBy(candidate.getCreatedBy());
		cadidateEdit.setName(candidate.getName());
		cadidateEdit.setPositionApplied(candidate.getPositionApplied());
		model.addAttribute("candidateEditViewModel", cadidateEdit);
		return prefix + "edit";
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.PUT)
	public String PutEdit(@Valid CandidateEditViewModel candidateEditViewModel, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model, @RequestParam("cv") MultipartFile cv)
			throws IOException {

		Long id = candidateEditViewModel.getId();
		Candidate candidate = candidateService.findById(id);
		if (bindingResult.hasErrors()) {
			candidateEditViewModel.setCreatedAt(candidate.getCreatedAt());
			candidateEditViewModel.setCreatedBy(candidate.getCreatedBy());
			model.addAttribute("message", "Favor de verificar las entradas");
			return prefix + "edit";
		}

		if (cv != null) {
			candidate.setCv(cv.getBytes());
		}
		candidate.setName(candidateEditViewModel.getName());
		candidate.setPositionApplied(candidateEditViewModel.getPositionApplied());
		candidateService.updateCandidate(candidate);
		redirectAttributes.addFlashAttribute("message", "Se ha actualizado correctamente el candidato.");
		return "redirect:/candidates";
	}

	@RequestMapping(value = { "/delete/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCandidate(@PathVariable Long id) {
		if (id != null) {
			candidateService.deleteCandidate(id);
		} else {
			return new ResponseEntity<>("Identificador vacio", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/uploadcv", method = RequestMethod.POST)
	public String uploadCV(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) throws Exception {
		Candidate candidate = candidateService.findById(id);
		candidate.setCv(file.getBytes());
		candidateService.updateCandidate(candidate);
		redirectAttributes.addFlashAttribute("message", "Se ha cargado correctamente el CV");
		return "redirect:/candidates";
	}

	@RequestMapping(value = "/downloadcv/{id}", method = RequestMethod.GET)
	public void downloadCV(@PathVariable("id") Long id, RedirectAttributes redirectAttributes,
			HttpServletResponse response) throws Exception {
		Candidate candidate = candidateService.findById(id);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename=cv_" + candidate.getName() + ".pdf");
		response.getOutputStream().write(candidate.getCv());
	}

}
