package com.fettmedia.stagebook.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fettmedia.stagebook.domain.StageBookUser;
import com.fettmedia.stagebook.domain.repos.IBaseRepository;
import com.fettmedia.stagebook.domain.repos.IStageBookUserRepository;

@Service
public class StageBookUserService extends BaseService<StageBookUser>
{

	@Autowired
	IStageBookUserRepository repo;

	@Override
	public IStageBookUserRepository getRepository()
	{
		return repo;
	}

}
