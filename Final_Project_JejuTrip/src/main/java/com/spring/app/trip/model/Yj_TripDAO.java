package com.spring.app.trip.model;

import java.util.List;

import com.spring.app.trip.domain.FoodStoreVO;

public interface Yj_TripDAO {

	// === 맛집 리스트 페이지 보이기 === //
	List<FoodStoreVO> viewFoodStoreList();

}
