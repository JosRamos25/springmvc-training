package com.plenumsoft.vuzee.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.plenumsoft.vuzee.entities.Candidate;


@Repository
public interface CandidateRepository extends PagingAndSortingRepository<Candidate, Long> {
	List<Candidate> findByName(String name);
}
