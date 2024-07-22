show user;
-- USER이(가) "SYS"입니다.

-- 이제 부터 오라클 계정생성시 계정명앞에 c## 붙이지 않고 생성하도록 하겠다.
alter session set "_ORACLE_SCRIPT"=true;
-- Session이(가) 변경되었습니다.

create user final_orauser2 identified by gclass default tablespace users;
-- User SEMI_ORAUSER2이(가) 생성되었습니다.

grant connect, resource, create view, unlimited tablespace to final_orauser2;
-- Grant을(를) 성공했습니다.



insert into tbl_member(userid, email, pw, user_name, mobile, address, detail_address, birthday, gender)
values('admin', 'G3a1oS69mAbA7+x7+opiiA==', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '관리자', 'XvX9PItBFtYP5Fm1q/4T+g==', '서울 마포구 월드컵북로 21', '301호', '2000-01-01', '2');
--    ('admin', 'admin@gmail.com', 'qwer1234$', '관리자', '01099999999', '', '', '', '')
commit;



-- 로그인 처리하기 (일반회원, 관리자)
SELECT userid, user_name, pwdchangegap,
       NVL(lastlogingap, trunc(months_between(sysdate, registerday))) AS lastlogingap, 
       idle, email, mobile, address, detail_address, birthday, gender
FROM 
( select userid, user_name,
         trunc( months_between(sysdate, lastpwdchangedate) ) AS pwdchangegap,
         registerday, idle, email, mobile, address, detail_address, birthday, gender
  from tbl_member 
  where status = 1 and userid = 'kimdy' and pw = '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382'
) M 
CROSS JOIN 
( select trunc( months_between(sysdate, max(logindate)) ) as lastlogingap 
  from tbl_member_loginhistory 
  where fk_userid = 'kimdy' ) H;



desc tbl_member_loginhistory;



update tbl_member set lastpwdchangedate = '24/03/01'
where userid = 'eomjh';
commit;



-- 업체 로그인
SELECT companyid, company_name, pwdchangegap,
       NVL(lastlogingap, trunc(months_between(sysdate, registerday))) AS lastlogingap, 
       idle, email, mobile
FROM 
( select companyid, company_name,
         trunc( months_between(sysdate, lastpwdchangedate) ) AS pwdchangegap,
         registerday, idle, email, mobile
  from tbl_company
  where status = 1 and companyid = 'naver' and pw = '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382'
) M 
CROSS JOIN 
( select trunc( months_between(sysdate, max(logindate)) ) as lastlogingap 
  from tbl_company_loginhistory 
  where fk_companyid = 'naver' ) H;





select userid, user_name
from tbl_member
where user_name = '엄정화' and email = 'yl0AhhmUdz58RxnXBNcWyQ==';



select companyid, company_name
from tbl_company
where company_name = '네이버' and email = 'q8o3ujFmPcgaRxMme3F+XQ==';



select userid
from tbl_member
where userid = 'kimdy' and email = 'iqOrelu2llBP6cnC6yu77REzaKuiYpaUDPuSnEzJZX4=';



-- 맛집 카테고리별 최신 1개씩 조회
select food_category, food_name, food_content
from
(
    select food_category, food_name, food_content
         , row_number() over(partition by food_category order by food_store_code desc) as rno
    from tbl_food_store
)
where rno = 1;



select *
from user_sequences;



select *
from tbl_member
where userid = 'kimdy';

select *
from tbl_member_loginhistory
where fk_userid = 'kimdy'
order by logindate desc;

update tbl_member_loginhistory
set logindate = '23/06/30'
where fk_userid = 'kimdy';

commit;



select pw
from tbl_member
where userid = 'kimdy' and pw = '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382';


update tbl_company set pw = '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382'
where companyid = 'comkimdy';
commit;


-- 자유게시판 총 게시물 건수 조회하기
select count(*)
from tbl_board
where status = 1 and category = 1
and lower(subject) like '%' || lower('추천') || '%';



-- 자유게시판 글 목록 조회하기
SELECT seq, fk_userid, name, subject, readCount, regDate, commentCount
FROM
(
    select row_number() over(order by seq desc) AS rno
         , seq, fk_userid, name, subject
         , readCount, to_char(regDate, 'yyyy-mm-dd hh24:mi') AS regDate
         , commentCount
    from tbl_board
    where status = 1 and category = 1
    -- and lower(subject) like '%' || lower('추천') || '%'
) V
WHERE V.rno between 1 and 10;




-- 검색 조건이 있을 시 글 1개 조회하기
SELECT previousseq, previoussubject
     , seq, fk_userid, name, subject, content, readCount, regDate, pw
     , nextseq, nextsubject
     , fileName, orgFilename, fileSize, category
FROM
(
    select lag(seq, 1) over(order by seq desc) AS previousseq
         , lag(subject, 1) over(order by seq desc) AS previoussubject
         , seq, fk_userid, name, subject, content, readCount
         , to_char(regDate, 'yyyy-mm-dd hh24:mi') as regDate, pw
         , lead(seq, 1) over(order by seq desc) AS nextseq
         , lead(subject, 1) over(order by seq desc) AS nextsubject
         , fileName, orgFilename, fileSize, category
    from tbl_board
    where status = 1 and category = 1
    -- and lower(subject) like '%' || lower('추천') || '%'
) V
WHERE V.seq = 5;



----- **** 댓글 테이블 생성 **** -----
drop table tbl_comment purge;
drop sequence commentSeq;

create table tbl_comment
(seq           number               not null   -- 댓글번호
,fk_userid     varchar2(20)         not null   -- 사용자ID
,name          varchar2(20)         not null   -- 성명
,content       varchar2(1000)       not null   -- 댓글내용
,regDate       date default sysdate not null   -- 작성일자
,parentSeq     number               not null   -- 원게시물 글번호
,status        number(1) default 1  not null   -- 글삭제여부
                                               -- 1 : 사용가능한 글,  0 : 삭제된 글
                                               -- 댓글은 원글이 삭제되면 자동적으로 삭제되어야 한다.

,groupno       number                not null    -- 답변글쓰기에 있어서 그룹번호 
                                                 -- 원글(부모글)과 답변글은 동일한 groupno 를 가진다.
                                                 -- 답변글이 아닌 원글(부모글)인 경우 groupno 의 값은 groupno 컬럼의 최대값(max)+1 로 한다.

,fk_seq         number default 0      not null   -- fk_seq 컬럼은 절대로 foreign key가 아니다.!!!!!!
                                                 -- fk_seq 컬럼은 자신의 글(답변글)에 있어서 
                                                 -- 원글(부모글)이 누구인지에 대한 정보값이다.
                                                 -- 답변글쓰기에 있어서 답변글이라면 fk_seq 컬럼의 값은 
                                                 -- 원글(부모글)의 seq 컬럼의 값을 가지게 되며,
                                                 -- 답변글이 아닌 원글일 경우 0 을 가지도록 한다.

,depthno        number default 0       not null  -- 답변글쓰기에 있어서 답변글 이라면
                                                 -- 원글(부모글)의 depthno + 1 을 가지게 되며,
                                                 -- 답변글이 아닌 원글일 경우 0 을 가지도록 한다.
,constraint PK_tbl_comment_seq primary key(seq)
,constraint FK_tbl_comment_userid foreign key(fk_userid) references tbl_member(userid)
,constraint FK_tbl_comment_parentSeq foreign key(parentSeq) references tbl_board(seq) on delete cascade
,constraint CK_tbl_comment_status check( status in(1,0) ) 
);
-- Table TBL_COMMENT이(가) 생성되었습니다.

create sequence commentSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;
-- Sequence COMMENTSEQ이(가) 생성되었습니다.




-- 댓글 목록 조회
SELECT seq, fk_userid, name, content, regDate
FROM
(
    select row_number() over(order by seq desc) AS rno
         , seq, fk_userid, name, content, to_char(regDate, 'yyyy-mm-dd hh24:mi') AS regDate
    from tbl_comment
    where status = 1 and parentSeq = 5
) V
where V.rno between 1 and 5;


-- 게시물당 댓글 개수
select count(*)
from tbl_comment
where status = 1 and parentSeq = 5;


select NVL(max(groupno), 0)
from tbl_comment;




