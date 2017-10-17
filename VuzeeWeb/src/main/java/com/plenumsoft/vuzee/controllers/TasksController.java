package com.plenumsoft.vuzee.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.plenumsoft.vuzee.entities.Candidate;
import com.plenumsoft.vuzee.entities.Task;
import com.plenumsoft.vuzee.entities.TaskState;
import com.plenumsoft.vuzee.services.CandidateService;
import com.plenumsoft.vuzee.services.TaskService;
import com.plenumsoft.vuzee.viewmodels.CandidateEditViewModel;
import com.plenumsoft.vuzee.viewmodels.TaskCreateViewModel;
import com.plenumsoft.vuzee.viewmodels.TaskEditViewModel;

@Controller
@RequestMapping(value = { "/tasks" })
public class TasksController {

	private final String prefix = "tasks/";
	private TaskService taskService;
	private CandidateService candidateService;

	public TasksController() {
	}

	@Autowired
	public TasksController(TaskService taskService, CandidateService candidateService) {
		super();
		this.taskService = taskService;
		this.candidateService = candidateService;
	}

	@ModelAttribute("allCandidates")
	public List<Candidate> getAllCandidates() {
		return this.candidateService.getAll();
	}

	@RequestMapping(value = { "/", "" })
	public ModelAndView Index() {
		ModelAndView mv = new ModelAndView(prefix + "index");
		mv.addObject("tasks", this.taskService.getAll());
		return mv;
	}

	@RequestMapping(value = { "/create" })
	public String PrepareCreate(@ModelAttribute("taskView") TaskCreateViewModel taskView) {
		return prefix + "create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String PostCreateCandidate(@Valid TaskCreateViewModel taskView, Model model,
			RedirectAttributes redirectAttributes, BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			model.addAttribute("message", "Favor de verificar las entradas");
			return prefix + "create";
		}
		Task task = new Task();
		task.setCandidate(taskView.getCandidate());
		task.setHasRating(taskView.isHasRating());
		task.setTaskDate(taskView.getTaskDate());
		task.setTaskState(TaskState.PENDING);
		task.setCreatedBy("msoberanis");// TODO: hard-code
		task.setCreatedAt(new Date());
		this.taskService.addTask(task);
		redirectAttributes.addFlashAttribute("message", "Se ha creado correctamente la tarea");
		return "redirect:/tasks";
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public String PrepareEdit(@PathVariable("id") Long id, Model model) {
		Task task = this.taskService.findById(id);
		TaskEditViewModel taskEdit = new TaskEditViewModel();
		taskEdit.setCandidate(task.getCandidate());
		taskEdit.setHasRating(task.isHasRating());
		taskEdit.setId(id);
		taskEdit.setTaskDate(task.getTaskDate());
		taskEdit.setTaskState(task.getTaskState());
		model.addAttribute("taskEdit", task);

		return prefix + "edit";
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.PUT)
	public String PutEdit(@Valid TaskEditViewModel taskEdit, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) throws IOException {

		if (bindingResult.hasErrors()) {
			model.addAttribute("message", "Favor de verificar las entradas");
			return prefix + "edit";
		}

		Long id = taskEdit.getId();
		Task task = this.taskService.findById(id);
		task.setHasRating(taskEdit.isHasRating());
		task.setTaskDate(taskEdit.getTaskDate());
		task.setTaskState(taskEdit.getTaskState());
		this.taskService.updateTask(task);
		redirectAttributes.addFlashAttribute("message", "Se ha actualizado correctamente la tarea.");
		return "redirect:/tasks";
	}

	@RequestMapping(value = { "/delete/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTask(@PathVariable Long id) {
		if (id != null) {
			this.taskService.deleteTask(id);
		} else {
			return new ResponseEntity<>("Identificador vacio", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
