package com.plenumsoft.vuzee.utils;

import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;

public class ModelMapperUtil {
	public <T> T mapObject(Type tarjet, Object origin) {
		ModelMapper modelMapper = new ModelMapper();
		T tarjetObject = modelMapper.map(origin, tarjet);
		return tarjetObject;
	}
}
