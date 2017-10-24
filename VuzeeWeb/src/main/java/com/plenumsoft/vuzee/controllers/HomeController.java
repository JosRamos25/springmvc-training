package com.plenumsoft.vuzee.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.plenumsoft.vuzee.entities.Candidate;
import com.plenumsoft.vuzee.entities.Task;
import com.plenumsoft.vuzee.entities.TaskState;
import com.plenumsoft.vuzee.services.CandidateService;
import com.plenumsoft.vuzee.services.TaskService;
import com.plenumsoft.vuzee.viewmodels.CandidateCard;

@Controller
@RequestMapping(value = { "/" })
public class HomeController {
	String prefix = "home/";

	private CandidateService candidateService;
	private TaskService taskService;

	@Autowired
	public HomeController(CandidateService candidateService, TaskService taskService) {
		super();
		this.candidateService = candidateService;
		this.taskService = taskService;
	}

	@RequestMapping(value = "/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
	
	@RequestMapping(value = "/login{logout}")
	public ModelAndView logout() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}

	@RequestMapping(value = "/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView(prefix + "index");
		return mv;
	}

	@RequestMapping(value = "/dashboard")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index");
		List<CandidateCard> cards = new ArrayList<>();
		List<Candidate> candidates = this.candidateService.getAll();
		candidates.forEach((candidate) -> {
			List<Task> tasks = this.taskService.findByCandidate(candidate);
			CandidateCard card = new CandidateCard();
			card.setName(candidate.getName());
			card.setPending(tasks.stream().filter(t -> t.getTaskState() == TaskState.PENDING).count());
			card.setInProgress(tasks.stream().filter(t -> t.getTaskState() == TaskState.INPROGRESS).count());
			card.setDone(tasks.stream().filter(t -> t.getTaskState() == TaskState.DONE).count());
			cards.add(card);
		});
		mv.addObject("candidateCards", cards);
		return mv;
	}

}
