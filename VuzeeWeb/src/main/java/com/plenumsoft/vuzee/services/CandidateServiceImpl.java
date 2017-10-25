package com.plenumsoft.vuzee.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.plenumsoft.vuzee.entities.Candidate;
import com.plenumsoft.vuzee.repositories.CandidateRepository;
import com.plenumsoft.vuzee.repositories.TaskRepository;

@Service
public class CandidateServiceImpl implements CandidateService {
	private CandidateRepository candidateRepository;
	private TaskRepository taskRepository;

	public CandidateServiceImpl(CandidateRepository candidateRepository, TaskRepository taskRepository) {
		super();
		this.candidateRepository = candidateRepository;
		this.taskRepository = taskRepository;
	}

	@Override
	public List<Candidate> getAll() {
		return (List<Candidate>) this.candidateRepository.findAll();
	}

	@Override
	public Page<Candidate> getAll(Pageable pageable) {
		return this.candidateRepository.findAll(pageable);
	}

	@Override
	public Long addCandidate(Candidate candidate) {
		if (candidate == null)
			throw new CandidateServiceException("No se pudo agregar la entidad" + candidate);

		if (candidate.getName().length() == 0)
			throw new CandidateServiceException("No se pudo agregar la entidad" + candidate);

		if (candidate.getPositionApplied().length() == 0)
			throw new CandidateServiceException("No se pudo agregar la entidad" + candidate);

		Candidate insertedCandidate = this.candidateRepository.save(candidate);
		if (insertedCandidate != null)
			return insertedCandidate.getId();

		return null;
	}

	@Override
	public Candidate findById(Long id) {
		// TODO Auto-generated method stub
		return candidateRepository.findOne(id);
	}

	@Override
	public void updateCandidate(Candidate candidate) {
		// TODO Auto-generated method stub
		candidateRepository.save(candidate);

	}

	@Override
	public void deleteCandidate(Long id) {
		this.taskRepository.delete(this.taskRepository.findByCandidate(this.findById(id)));
		candidateRepository.delete(id);
	}

}
