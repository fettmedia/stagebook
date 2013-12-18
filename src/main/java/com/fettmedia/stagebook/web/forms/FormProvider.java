package com.fettmedia.stagebook.web.forms;

import java.util.Hashtable;

import org.springframework.stereotype.Component;

import com.fettmedia.stagebook.domain.IEntity;

@Component
public class FormProvider
{
	@SuppressWarnings("rawtypes")
	Hashtable<Class<? extends IEntity>, EntityForm<? extends IEntity>> registry = new Hashtable<Class<? extends IEntity>, EntityForm<? extends IEntity>>();
	
}
