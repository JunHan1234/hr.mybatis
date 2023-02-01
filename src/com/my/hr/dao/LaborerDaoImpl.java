package com.my.hr.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.my.hr.config.Configuration;
import com.my.hr.dao.map.LaborerMap;
import com.my.hr.domain.Laborer;
import com.my.hr.domain.NoneException;

public class LaborerDaoImpl implements LaborerDao {
	private LaborerMap laborerMap;
	
	public LaborerDaoImpl() {
		this.laborerMap = Configuration.getMapper(LaborerMap.class);
	}
	
	@Override
	public List<Laborer> selectLaborers() {
		return this.laborerMap.selectLaborers();
	}
	
	private Laborer selectLaborer(int laborerId) {
		List<Laborer> list = this.laborerMap.selectLaborers().stream()
				.filter(laborer -> laborer.getLaborerId() == laborerId)
				.collect(Collectors.toList());
		
		Laborer laborer = null;
		if(list.size() != 0) laborer = list.get(0);
		
		return laborer;
	}
	
	@Override
	public void insertLaborer(String laborerName, LocalDate hireDate) {
		this.laborerMap.insertLaborer(laborerName, hireDate);
	}
	
	@Override
	public void updateLaborer(Laborer laborer) throws NoneException {
		Laborer selectLaborer = this.selectLaborer(laborer.getLaborerId());
		if(selectLaborer != null) this.laborerMap.updateLaborer(laborer);
		else throw new NoneException("해당 노동자가 없습니다.");
	}
	
	@Override
	public void deleteLaborer(int laborerId) throws NoneException {
		Laborer laborer = this.selectLaborer(laborerId);
		if(laborer != null) this.laborerMap.deleteLaborer(laborerId);
		else throw new NoneException("해당 노동자가 없습니다.");
	}
}