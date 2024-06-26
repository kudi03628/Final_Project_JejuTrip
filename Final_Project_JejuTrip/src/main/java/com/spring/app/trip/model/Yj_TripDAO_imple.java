package com.spring.app.trip.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.spring.app.trip.domain.FoodstoreVO;

@Repository
public class Yj_TripDAO_imple implements Yj_TripDAO {
	
	@Autowired
	@Qualifier("sqlsession")
	private SqlSessionTemplate sqlsession;

	
	// === 맛집 리스트 페이지 보이기 === //
	@Override
	public List<FoodstoreVO> viewFoodstoreList() {
		List<FoodstoreVO> foodstoreList = sqlsession.selectList("yj_trip.viewFoodstoreList");
		return foodstoreList;
	}

}
