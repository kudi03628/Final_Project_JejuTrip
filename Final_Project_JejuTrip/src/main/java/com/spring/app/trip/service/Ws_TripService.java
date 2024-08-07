package com.spring.app.trip.service;

import java.util.List;
import java.util.Map;

import com.spring.app.trip.domain.Calendar_schedule_VO;
import com.spring.app.trip.domain.Calendar_small_category_VO;
import com.spring.app.trip.domain.CompanyVO;
import com.spring.app.trip.domain.FoodstoreVO;
import com.spring.app.trip.domain.LodgingVO;
import com.spring.app.trip.domain.MemberVO;
import com.spring.app.trip.domain.PlayVO;

public interface Ws_TripService {

	int companyRegister(CompanyVO cvo);// 회사 회원가입을 위한 service 메소드 생성
	int companyIdCheck(String companyid);// 가입하려는 아이디가 존재하는 아이디인지 체크하는 메소드 생성
	int companyEmailCheck(String email);// 가입하려는 기업 이메일이 존재하는 이메일인지 사용가능한 이메일인지 확인하는 메소드
	int registerHotelEnd(LodgingVO lodgingvo);// === 데이터 베이스에 등록하려는 숙소 정보 insert 하기 === // 
	List<LodgingVO> select_lodgingvo(Map<String, String> paraMap);	// 숙소 등록을 신청한 업체중 선택한 카테고리에 해당하는 모든 업체들 불러오기
	int screeningRegisterEnd(Map<String, String> paraMap);// 관리자가 숙소 등록 요청에 답한대로 DB를 업데이트 시켜준다.
	int getTotalCount(String choice_status);// 총 게시물 건수(totalCount)
	List<Map<String, String>> select_convenient();// 편의시설 체크박스를 만들기 위해 DB에 있는 편의시설 테이블에서 편의시설 종류를 select 해온다.
	String getSeq();// 시퀀스 채번해오기
	void insert_convenient(Map<String, String> paraMap);// 숙소정보에 따른 편의시설 정보 insert 해주기
	List<Map<String, String>> select_convenient_list();// 편의시설 정보를 가져와서 view 페이지에 표출시켜주기위한 List select
	List<Map<String, String>> select_count_registerHotel(String companyid);// 숙소 테이블에서 해당 업체의 신청건수, 승인건수, 반려 건수를 각각 알아온다.
	List<LodgingVO> select_loginCompany_lodgingvo(String companyid);		// 로그인 한 기업의 신청 목록을 읽어와서 view 페이지에 목록으로 뿌려주기 위한 select
	LodgingVO selectRegisterHotelJSON(String lodging_code);// 업체가 신청한 호텔에 대한 상세 정보를 보여주기위해 DB에서 읽어온다.
	List<MemberVO> select_member_all(Map<String, String> paraMap);// 모든 회원의 정보를 읽어오는 메소드 생성
	List<CompanyVO> select_Company_all(Map<String, String> paraMap);// 모든 기업의 정보를 읽어오는 메소드 생성
	int getTotalMemberCount();// member 테이블의 총 행 개수 알아오기
	int getTotalCompanyCount();// company 테이블의 총 행 개수 알아오기
	MemberVO select_detailMember(String userid);// 멤버 정보를 가져온다.
	CompanyVO select_detailCompany(String userid);// 아이디를 토대로 회사 정보를 가져온다.
	List<Map<String, String>> get_year_login_member_chart();// 매년 로그인 수 통계를 내기 위한 차트 값 가져오기
	List<Map<String, String>> get_month_login_member_chart(String choice_year);// 매달 가입자 수 통계를 내기 위한 차트 값 가져오기
	List<Map<String, String>> user_age_group_chart();// 사용자 연령대 차트에 사용할 정보 가져오기
	List<Map<String, String>> user_gender_chart();// 사용자 성별 차트에 사용할 정보 가져오기
	List<Map<String, String>> get_year_reservation_hotel_chart(String lodging_code);// 매년 호텔 예약건수를 찾아와서 차트화 시켜주기위한 정보 가져오기
	List<Map<String, String>> get_month_reservation_chart(Map<String, String> paraMap);// 선택한 년도의 매월 예약건수를 가져와서 차트화 시켜준다.
	List<LodgingVO> select_lodging(Map<String, String> paraMap);// 숙소  테이블에서 기본적인 정보 목록을 가져온다.
	int getTotalLodgingCount();// tbl_lodging 테이블에서 status가 1인 모든 숙소의 개수를 읽어온다.
	List<FoodstoreVO> select_foodstore(Map<String, String> paraMap);// 맛집  테이블에서 기본적인 정보 목록을 가져온다.
	int getTotalFoodstoreCount();// tbl_food_store 테이블에 있는 모든 맛집의 개수를 알아온다.
	List<PlayVO> select_play(Map<String, String> paraMap);//즐길거리  테이블에서 기본적인 정보 목록을 가져온다.
	int getTotalPlayCount();// tbl_play 테이블에 있는 모든 즐길거리 개수를 알아온다.
	List<Map<String, String>> get_day_reservation_chart(Map<String, String> paraMap);// 선택한 월에서 매일의 예약건수를 가져와서 차트화 시켜준다.
	String get_last_day(String choice_month);// 내가 선택한 월이 있다면 그 월의 마지막 날을 구해준다.
	List<Map<String, String>> get_year_profit_chart(String companyid);// 매년 업체수익을 찾아와서 차트화 시켜주기위한 정보 가져오기
	List<Map<String, String>> get_month_profit_chart(Map<String, String> paraMap);// 선택한 년도의 매월 매출액을 가져와서 차트화 시켜준다.
	List<Map<String, String>> get_day_profit_chart(Map<String, String> paraMap);// 선택한 월에서 매일의 매출액을 가져와서 차트화 시켜준다.
	List<Map<String, String>> select_company_all_Reservation(String companyid);// 기업이 소유하고있는 호텔의 총 예약건을 읽어온다.
	String select_reservation_Count(Map<String, String> reservationMap);// 예약일자마다의 객실 잔여석을 알아오기 위함이다.
	List<Map<String, String>> get_year_profit_chart_success(String companyid);// 매년 승인된 업체수익을 찾아와서 차트화 시켜주기위한 정보 가져오기
	List<Map<String, String>> get_year_profit_chart_fail(String companyid);// 매년 취소 업체수익을 찾아와서 차트화 시켜주기위한 정보 가져오기
	List<Map<String, String>> get_month_profit_chart_succeess(Map<String, String> paraMap);// 선택한 년도의 매월 승인매출액을 가져와서 차트화 시켜준다.
	List<Map<String, String>> get_month_profit_chart_fail(Map<String, String> paraMap);// 선택한 년도의 매월 취소금액을 가져와서 차트화 시켜준다.
	List<Map<String, String>> get_day_profit_chart_success(Map<String, String> paraMap);// 선택한 월에서 매일의 승인된 매출액을 가져와서 차트화 시켜준다.
	List<Map<String, String>> get_day_profit_chart_fail(Map<String, String> paraMap);// 선택한 월에서 매일의 취소금액을 가져와서 차트화 시켜준다.
	int getTotalreservationCount(Map<String, String> paraMap);// tbl_reservation에서 자기 자신의 기업에 해당하는 모든 예약정보를 가져온다.
	List<Map<String, String>> select_company_all_Reservation_paging(Map<String, String> paraMap);// 기업이 소유하고있는 호텔의 총 예약건을 페이징 처리 해서 읽어온다.
	int updateReservationStatus(Map<String, String> paraMap);// 업체가 처리한 결과에 따라 reservation테이블에 status값 바꿔주기
	List<String> select_user_all_reservation(String userid);// 회원의 예약 목록을 가져와서 status별로 카운트를 해준다.
	List<Map<String, String>> select_user_all_Reservation_paging(Map<String, String> paraMap);// 개인회원이 한 예약정보를 페이징처리하여 읽어온다.
	int getTotalUserReservationCount(Map<String, String> paraMap);// tbl_reservation에서 자기 자신의 모든 예약정보를 카운트한다.
	String get_user_email(String reservation_code);// 회원의 이메일을 읽어온다.
	Map<String, String> get_email_map(String reservation_code);// 바우처에 입력할 정보를 가져오기 위해 예약정보등을 가져온다.
	int update_member_info(MemberVO membervo);// 입력한 값으로 회원 정보를 수정한다.
	boolean userEmailDuplicateCheckEdit(Map<String, String> paraMap);// 로그인한 유저 자기자신의 이메일을 제외한 다른 사람의 이메일중 중복값이 있는 지 알아오기.
	List<Calendar_schedule_VO> selectSchedule(String fk_userid);// 등록된 일정 가져오기
	Map<String, String> detailSchedule(String scheduleno);// 일정상세보기
	List<Calendar_small_category_VO> showMyCalendar(String fk_userid);// 내 캘린더에서 내캘린더 소분류  보여주기
	int getTotalScheduleCount(Map<String, String> paraMap);// 총 일정 검색 건수(totalCount)
	List<Map<String, String>> scheduleListSearchWithPaging(Map<String, String> paraMap);// 페이징 처리한 캘린더 가져오기(검색어가 없다라도 날짜범위 검색은 항시 포함된 것임)
	int registerSchedule_end(Map<String, String> paraMap);// 일정 등록하기
	List<MemberVO> searchJoinUserList(Map<String, String> paraMap);// 공유자 명단 불러오기
	int editSchedule_end(Calendar_schedule_VO svo);// 일정 수정 완료하기
	int deleteSchedule(String scheduleno);// 일정삭제하기
	String getCompanyidToTblReservation(String reservation_code);// 해당 예약에 관련된 companyid를 가져와야한다.
	Map<String, String> getCompanyIdAndLodgingNameToTblReservationCode(String reservation_code);// 예약 코드를 가지고 업체아이디와 업체명을 가져오기
	void insert_send_chatting(Map<String, String> chatMap);// 채팅을 보냈다는 기록을 남겨준다.
	int get_new_chatting(String userid);// 로그인을 하고 메인에 들어갔을 때 새로 온 채팅이 있는지 확인해준다.
	List<Map<String, String>> select_company_all_chatting_paging(Map<String, String> paraMap);// 기업으로 온 모든 채팅 목록을 읽어온다.
	int getTotalCompanyChattingCount(Map<String, String> paraMap);// 페이징 처리시 보여주는 순번을 나타내기 위한 것임.
	Map<String, String> getMemberIdAndNameToTblReservationCode(String reservation_code);// 채팅에 해당하는 고객 아이디와 이름을 가져온다.
	void update_chattinglog(String parameter);// 채팅 로그 테이블에 해당 예약건에 관련한 채팅을 읽음처리한다.
	void update_chattinglog_after_chatting(Map<String, String> paraMap);// 채팅방에서 나가게되면 채팅 기록방에서 나가기 직전까지의 읽음 컬럼을 읽음처리로 바꿔준다.
	List<Map<String, String>> select_member_all_chatting_paging(Map<String, String> paraMap);// 회원으로 온 모든 채팅 목록을 읽어온다.
	int getTotalMemberChattingCount(Map<String, String> paraMap);// 페이징 처리시 보여주는 순번을 나타내기 위한 것임.
	int get_all_chatting(String companyid);// 로그인을 했을 때 모든 채팅의 개수를 읽어온다.
	List<Map<String, String>> select_admin_all_chatting_paging(Map<String, String> paraMap);// 관리자로 온 모든 채팅 목록을 읽어온다.
	int getTotalAdminChattingCount(Map<String, String> paraMap);// 페이징 처리시 보여주는 순번을 나타내기 위한 것임.
	String getUserName(String userid);// 유저 아이디로 이름 가져오기
	int get_from_admin_chatting_exist(String userid);// 새로 온 관리자 메세지가 있는지 확인한다.
	int get_new_chatting_admin(String userid);// 로그인을 하고 메인에 들어갔을 때 새로 온 채팅이 있는지 확인해준다.
	int get_all_chatting_admin(String userid);// 로그인을 했을 때 모든 채팅의 개수를 읽어온다.
	List<Map<String, String>> get_rand_hotel_list();// 메인에 표시해줄 3개의 호텔을 랜덤으로 가져온다.
	List<Map<String, String>> get_rand_resort_list();// 메인에 표시해줄 3개의 리조트를 랜덤으로 가져온다.
	List<Map<String, String>> get_rand_guestHouse_list();// 메인에 표시해줄 3개의 게스트하우스를 랜덤으로 가져온다.
	

}
