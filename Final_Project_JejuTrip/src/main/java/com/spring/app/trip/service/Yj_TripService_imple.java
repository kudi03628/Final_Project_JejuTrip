package com.spring.app.trip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.trip.domain.FoodStoreVO;
import com.spring.app.trip.model.Yj_TripDAO;
@Service
public class Yj_TripService_imple implements Yj_TripService {
	
	@Autowired
	private Yj_TripDAO dao;

	
	// === 맛집 리스트 페이지 보이기 === //
	@Override
	public List<FoodStoreVO> viewFoodStoreList() {
		List<FoodStoreVO> foodStoreList = dao.viewFoodStoreList();
		return foodStoreList;
	}

}
