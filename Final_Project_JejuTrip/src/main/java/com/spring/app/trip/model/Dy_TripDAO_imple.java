package com.spring.app.trip.model;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.spring.app.trip.domain.MemberVO;

@Repository
public class Dy_TripDAO_imple implements Dy_TripDAO {
	
	@Autowired
	@Qualifier("sqlsession")
	private SqlSessionTemplate sqlsession;

	
	// 회원가입 처리하기
	@Override
	public int memberRegister(MemberVO mvo) {
		
		int n = sqlsession.insert("dy_trip.memberRegister", mvo);
		
		return n;
	}


	// 로그인 처리하기 (일반회원, 관리자)
	@Override
	public MemberVO getLoginMember(Map<String, String> paraMap) {

		MemberVO loginuser = sqlsession.selectOne("dy_trip.getLoginMember", paraMap);
		
		return loginuser;
	}


	// 회원의 idle 컬럼의 값을 1로 변경하기
	@Override
	public void updateIdle(String id) {
		sqlsession.update("dy_trip.updateIdle", id);
		
	}


	// 로그인 기록 테이블에 기록 입력하기
	@Override
	public void insert_loginhistory(Map<String, String> paraMap) {

		sqlsession.insert("dy_trip.insert_loginhistory", paraMap);
	}
	

}
