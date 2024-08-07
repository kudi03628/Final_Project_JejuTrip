package com.spring.app.trip.model;



import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.openqa.selenium.devtools.v123.css.model.SelectorList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.spring.app.trip.domain.Calendar_schedule_VO;
import com.spring.app.trip.domain.CompanyVO;
import com.spring.app.trip.domain.LikeVO;
import com.spring.app.trip.domain.LodgingVO;
import com.spring.app.trip.domain.MemberVO;
import com.spring.app.trip.domain.PlayVO;
import com.spring.app.trip.domain.ReviewVO;
@Component
@Repository
public class Hs_TripDAO_imple implements Hs_TripDAO {
	
	@Autowired
	@Qualifier("sqlsession")
	private SqlSessionTemplate sqlsession;

	//즐길거리 List
	@Override
	public List<PlayVO> playList() {
		List<PlayVO> playList = sqlsession.selectList("hs_trip.playList");
		return playList;
	}

	@Override
	public List<PlayVO> getPlayListByCategory(Map<String, Object> paraMap) {
		List<PlayVO> playList = sqlsession.selectList("hs_trip.getPlayListByCategory",paraMap);
		return playList;
	}

	@Override
	public int registerPlayEnd(PlayVO playvo) {
		int n = sqlsession.insert("hs_trip.registerPlayEnd",playvo);
		return n;
	}

	@Override
	public int getPlayTotalCount(Map<String, Object> paraMap) {
		int totalcount = sqlsession.selectOne("hs_trip.getPlayTotalCount",paraMap);
		return totalcount;
	}

	@Override
	public PlayVO goAddSchedule(String play_code) {
		PlayVO goAddSchedule = sqlsession.selectOne("hs_trip.goAddSchedule",play_code);
		return goAddSchedule;
	}

	@Override
	public int addReview(ReviewVO reviewvo) {
		int n = sqlsession.insert("hs_trip.addReview",reviewvo);
		return n;
	}
	//리뷰보여주기
	@Override
	public List<ReviewVO> reviewList(Map<String, String> paraMap) {
		List<ReviewVO> reviewList = sqlsession.selectList("hs_trip.reviewList",paraMap);
		return reviewList;
	}
	//리뷰 총수량 알아오기
	@Override
	public int getPlayReviewCount(Map<String, String> paraMap) {
		int getPlayReviewCount = sqlsession.selectOne("hs_trip.getPlayReviewCount",paraMap);
		return getPlayReviewCount;
	}
	
	//리뷰수정하기
	@Override
	public int updateReview(Map<String, String> paraMap) {
		int n = sqlsession.update("hs_trip.updateReview",paraMap);
		return n;
	}
	//리뷰삭제하기
	@Override
	public int reviewDel(String review_code) {
		int n = sqlsession.delete("hs_trip.reviewDel",review_code);
		return n;
	}
	
	//글 한개 조회하기
	@Override
	public PlayVO getPlaySelect(Map<String, String> paraMap) {
		PlayVO getPlaySelect = sqlsession.selectOne("hs_trip.getPlaySelect",paraMap);
		return getPlaySelect;
	}

	@Override
	public int editEnd(PlayVO playvo) {
		int n = sqlsession.update("hs_trip.editEnd",playvo);
		return n;
	}
	//조회수 증가
	@Override
	public int increase_readCount(String play_code) {
		int n = sqlsession.update("hs_trip.increase_readCount",play_code);
		return n;
	}
	//원글 삭제 전 리뷰삭제
	@Override
	public int delReview(Map<String, String> paraMap) {
		int n = sqlsession.delete("hs_trip.delReview",paraMap);
		return n;
	}
	//리뷰삭제 후 원글 삭제
	@Override
	public int delView(Map<String, String> paraMap) {
		int n = sqlsession.delete("hs_trip.delView",paraMap);
		return n;
	}

	@Override
	public List<LikeVO> checkLike(Map<String, String> paraMap) {
		List<LikeVO> checkLike = sqlsession.selectList("hs_trip.checkLike",paraMap);
		//System.out.println("checkLike_dao" + checkLike);
		return checkLike;
	}

	@Override
	public int likeAdd(Map<String, String> paraMap) {
		int n = sqlsession.insert("hs_trip.likeAdd",paraMap);
		return n;
	}

	@Override
	public void likeDel(Map<String, String> paraMap) {
		sqlsession.delete("hs_trip.likeDel",paraMap);
		
	}
	
	@Override
	public int countLike(Map<String, String> paraMap) {
		int countLike = sqlsession.selectOne("hs_trip.countLike",paraMap);
		return countLike;
	}

	//카테고리 count
	public int countTotal() {
	    return sqlsession.selectOne("hs_trip.countTotal");
	}

	public int countTourism() {
	    return sqlsession.selectOne("hs_trip.countTourism");
	}

	public int countShowing() {
	    return sqlsession.selectOne("hs_trip.countShowing");
	}

	public int countExperience() {
	    return sqlsession.selectOne("hs_trip.countExperience");
	}

	//일행 추가를 위한 유저 ID select
	@Override
	public List<MemberVO> searchPlayJoinUserList(String joinUserName) {
		List<MemberVO> playJoinUserList = sqlsession.selectList("hs_trip.searchPlayJoinUserList",joinUserName);
		return playJoinUserList;
	}
	
	//일정추가
	@Override
	public int registerPlaySchedule_end(Map<String, String> paraMap) {
		int n = sqlsession.insert("hs_trip.registerPlaySchedule_end",paraMap);
		return n;
	}

//	//일정추가했는지 알아오기
//	@Override
//	public List<Calendar_schedule_VO> checkSchedule(Map<String, String> paraMap) {
//		List<Calendar_schedule_VO> checkSchedule = sqlsession.selectList("hs_trip.checkSchedule",paraMap);
//		return checkSchedule;
//	}

	
	//모든 리뷰를 count 하기
	@Override
	public int getAllReviewCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("hs_trip.getAllReviewCount",paraMap);
		return n;
	}
	
	
	// 리뷰 List
	@Override
	public List<ReviewVO> allReviewList(Map<String, String> paraMap) {
		List<ReviewVO> allReviewList = sqlsession.selectList("hs_trip.allReviewList",paraMap);
		return allReviewList;
	}

	
	@Override
	public int getFoodReviewCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("hs_trip.getFoodReviewCount",paraMap);
		return n;
	}

	@Override
	public List<Map<String, String>> foodReviewList(Map<String, String> paraMap) {
		List<Map<String, String>> foodReviewList = sqlsession.selectList("hs_trip.foodReviewList",paraMap);
		return foodReviewList;
	}

	
	
	@Override
	public int getPlaytotalReviewCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("hs_trip.getPlaytotalReviewCount",paraMap);
		return n;
	}

	@Override
	public List<Map<String, String>> playReviewList(Map<String, String> paraMap) {
		List<Map<String, String>> playReviewList = sqlsession.selectList("hs_trip.playReviewList",paraMap);
		return playReviewList;
	}

	@Override
	public int getLoginReviewCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("hs_trip.getLoginReviewCount",paraMap);
		return n;
	}

	@Override
	public List<Map<String, String>> loginReviewList(Map<String, String> paraMap) {
		List<Map<String, String>> loginReviewList = sqlsession.selectList("hs_trip.loginReviewList",paraMap);
		return loginReviewList;
	}

	
	@Override
	public int lodgingLikeCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("hs_trip.lodgingLikeCount",paraMap);
		return n;
	}

	@Override
	public List<Map<String, String>> lodginglikeList(Map<String, String> paraMap) {
		List<Map<String, String>> lodginglikeList = sqlsession.selectList("hs_trip.lodginglikeList",paraMap);
		return lodginglikeList;
	}
	

	@Override
	public int foodLikeCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("hs_trip.foodLikeCount",paraMap);
		return n;
	}

	@Override
	public List<Map<String, String>> foodlikeList(Map<String, String> paraMap) {
		List<Map<String, String>> foodlikeList = sqlsession.selectList("hs_trip.foodlikeList",paraMap);
		return foodlikeList;
	}

	
	@Override
	public int playLikeCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("hs_trip.playLikeCount",paraMap);
		return n;
	}

	@Override
	public List<Map<String, String>> playlikeList(Map<String, String> paraMap) {
		List<Map<String, String>> playlikeList = sqlsession.selectList("hs_trip.playlikeList",paraMap);
		return playlikeList;
	}
	
	
	//관리자 리뷰 관련
	
	
	//1. 전체 리뷰
	@Override
	public int admin_ReviewCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("hs_trip.admin_ReviewCount",paraMap);
		return n;
	}

	@Override
	public List<ReviewVO> admin_ReviewList(Map<String, String> paraMap) {
		List<ReviewVO> admin_ReviewList = sqlsession.selectList("hs_trip.admin_ReviewList",paraMap);
		return admin_ReviewList;
	}

	
	//2. 맛집리뷰
	@Override
	public int adminFoodReviewCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("hs_trip.adminFoodReviewCount",paraMap);
		return n;
	}

	@Override
	public List<Map<String, String>> adminfoodReviewList(Map<String, String> paraMap) {
		List<Map<String, String>> adminfoodReviewList = sqlsession.selectList("hs_trip.adminfoodReviewList",paraMap);
		return adminfoodReviewList;
	}
	
	//3. 즐길거리
	@Override
	public int adminPlaytotalReviewCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("hs_trip.adminPlaytotalReviewCount",paraMap);
		return n;
	}

	@Override
	public List<Map<String, String>> adminPlayReviewList(Map<String, String> paraMap) {
		List<Map<String, String>> adminPlayReviewList = sqlsession.selectList("hs_trip.adminPlayReviewList",paraMap);
		return adminPlayReviewList;
	}
	
	
	//4. 숙소
	@Override
	public int adminLogingReviewCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("hs_trip.adminLogingReviewCount",paraMap);
		return n;
	}

	@Override
	public List<Map<String, String>> adminLogingReviewList(Map<String, String> paraMap) {
		List<Map<String, String>> adminLogingReviewList = sqlsession.selectList("hs_trip.adminLogingReviewList",paraMap);
		return adminLogingReviewList;
	}

	
	//회사별 리뷰 가져오기
	@Override
	public List<Map<String, String>> logingCmpReviewList(Map<String, String> paraMap) {
		List<Map<String, String>> logingCmpReviewLis = sqlsession.selectList("hs_trip.logingCmpReviewList",paraMap);
		return logingCmpReviewLis;
	}

	@Override
	public int getCmpLoginReviewCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("hs_trip.getCmpLoginReviewCount",paraMap);
		return n;
	}

	//회사가 가지고있는 숙소리스트 가져오기
	@Override
	public List<LodgingVO> lodgingList(String companyid) {
		List<LodgingVO> lodgingList = sqlsession.selectList("hs_trip.lodgingList",companyid);
		return lodgingList;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}