package com.fettmedia.stagebook.domain.repos;

import org.springframework.stereotype.Repository;

import com.fettmedia.stagebook.domain.StageBookUser;

@Repository
public interface IStageBookUserRepository extends
		IBaseRepository<StageBookUser, Long>
{

}
