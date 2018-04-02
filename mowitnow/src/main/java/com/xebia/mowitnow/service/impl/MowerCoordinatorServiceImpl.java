package com.xebia.mowitnow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xebia.mowitnow.beans.Mower;
import com.xebia.mowitnow.beans.MowerCoordinator;
import com.xebia.mowitnow.service.MowerCoordinatorService;
import com.xebia.mowitnow.service.MowerService;

@Service
public class MowerCoordinatorServiceImpl implements MowerCoordinatorService {

	private static final Logger logger = LoggerFactory.getLogger(MowerCoordinatorServiceImpl.class);
	
	
	@Autowired
	MowerService mowerService;

	@Override
	public List<Mower> mowIt(MowerCoordinator mowerCoordinator) {

		if (mowerCoordinator != null && !CollectionUtils.isEmpty(mowerCoordinator.getMowers())
				&& mowerCoordinator.getGarden() != null) {
			logger.debug("Start processing for mower coordinator : " + mowerCoordinator);

			final List<Mower> endMowers = new ArrayList<Mower>();
			final AtomicInteger idx = new AtomicInteger();
			
			mowerCoordinator.getMowers().forEach(mower -> {
				mowerService.move(mowerCoordinator.getGarden(), mower);
				logger.debug("Final position for Mower number : " + idx.getAndIncrement() + ", Mower Details : " + mower);
				endMowers.add(mower);
			});
			
			return endMowers;

		} else {
			logger.debug("Error while starting process for mowerCoordinator : " + mowerCoordinator);
			throw new IllegalArgumentException(
					"Error while starting process for mowerCoordinator : " + mowerCoordinator);
		}

	}
	
	
}
