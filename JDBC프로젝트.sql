/*
Java에서 처음으로 JDBC 프로그래밍 해보기
*/




--우선 system계정으로 연결한 후 새로운 계정을 생성한다.


alter session set "_ORACLE_SCRIPT"=true;

CREATE USER education IDENTIFIED BY 1234;

GRANT CONNECT, RESOURCE, unlimited tablespace TO education;


set serveroutput on;

drop table sh_product_code;
drop table sh_goods;

CREATE TABLE sh_product_code
(
    
    p_code NUMBER(30) NOT NULL, 
	category_name VARCHAR2(30) NOT NULL,
    --PRIMARY KEY (p_code)
    CONSTRAINT sh_product_code_pk PRIMARY KEY (p_code)
);
--앞에서 생성한 시퀀스를 이용해서 3~5개 정도의 상품코드 레코드를 입력한다.
--가전, 도서, 의류 등

insert into sh_product_code (p_code, category_name) values (1, 'outer');
insert into sh_product_code (p_code, category_name) values (2, 'inner');
insert into sh_product_code (p_code, category_name) values (3, 'denim');
insert into sh_product_code (p_code, category_name) values (4, 'acc');


--앞에서 생성한 시퀀스를 이용해서 5~10개 정도의 상품 레코드를 입력한다. 
--냉장고, 세탁기 / 사피엔스, 총균쇠 / 롱패딩, 레깅스, 청바지 등

CREATE TABLE sh_goods
(
    g_idx NUMBER(30) NOT NULL,
	goods_name VARCHAR2(30) NOT NULL,
	goods_price NUMBER(30) NOT NULL,
	regidate DATE,
    p_code NUMBER(30),
    CONSTRAINT sh_goods_pk PRIMARY KEY (g_idx),
    CONSTRAINT sh_goods_fk FOREIGN KEY (p_code) REFERENCES sh_product_code (p_code) 
    --FOREIGN KEY (p_code)
);

insert into sh_goods (g_idx, goods_name,goods_price, regidate, p_code)
    values (2201, 'duckdown_jumper', 200000, '2022-11-07', 1);
insert into sh_goods (g_idx, goods_name,goods_price, regidate, p_code)
    values (2111, 'wool_coat', 200000, '2022-10-22', 1);
insert into sh_goods (g_idx, goods_name,goods_price, regidate, p_code)
    values (1937, 'jacket', 119000, '2022-09-05', 1);    
insert into sh_goods (g_idx, goods_name,goods_price, regidate, p_code)
    values (383, 'slacks', 39000, '2022-08-29', 2);
insert into sh_goods (g_idx, goods_name,goods_price, regidate, p_code)
    values (2383, 'mid_skirt', 49000, '2022-10-22', 2);
insert into sh_goods (g_idx, goods_name,goods_price, regidate, p_code)
    values (2021, 'stone_denim', 29000, '2022-10-22', 3);
insert into sh_goods (g_idx, goods_name,goods_price, regidate, p_code)
    values (1038, 'washed_denim', 19000, '2022-10-22', 3);
insert into sh_goods (g_idx, goods_name,goods_price, regidate, p_code)
    values (203, 'bag', 30000, '2022-01-16', 4);
insert into sh_goods (g_idx, goods_name,goods_price, regidate, p_code)
    values (211, 'cap', 20000, '2022-04-22', 4);
   
drop table sh_goods_log;

CREATE TABLE sh_goods_log
(
    log_idx  NUMBER(30) NOT NULL,
	goods_name VARCHAR2(30) NOT NULL,
	goods_idx NUMBER(30) NOT NULL,
    p_action VARCHAR2(30) NOT NULL,
    CONSTRAINT sh_goods_log PRIMARY KEY (log_idx)
);

insert into member (log_idx, goods_name, goods_idx, p_action)values ();




create sequence seq_total_idx
    increment by 1 
    start with 1 
    minvalue 1 
    NoCycle 
    NoCache; 



alter sequence 시퀀스명
    [Increment by N] 
    [Maxvalue n | Minvalue n]
    [Cycle | NoCycle]
    [Cache | NoCache];


select  * from sh_goods;
select  * from sh_product_code;

-----------------------------------------------------------------------

*
20221101
*/
--------------------------------------------------------------
--JDBC > CallableStatement  인터페이스 사용하기

--substr(문자열 혹은 컬럼, 시작인덱스, 길이): 시작인덱스부터 길이만큼 잘라낸다.
select substr('김지연',1,1)from adbook;
--rpad(문자열 혹은 컬럼,길이, 체을 문자): 문자열의 남은 길이를 문자로 채운다.
select rpad('h',5, '*') from dual; --h****
select rpad('2',7, '*') from dual; --2******


--문자열(아이디)의 첫글자를 제외한 나머지 부분을 *로 채운다.
--아이디를 게시판 리스트에 출력 시 히든처리 해야할 때 활용할 수 있다.
select rpad(substr('김지연',1,1), length('김지연'),'*')
    from adbook;


/*
시나리오] 매개변수로 회원아이디(문자열)을 받으면 첫문자를 제외한 나머지 부분
*로 변환하는 함수를 생성하시오.
*/
create or replace function fillAsterik (
    idStr varchar2
) /*매개변수는 문자형으로 설정*/
return varchar2 /*반환값도 문자형으로 설정*/
is retStr varchar2(50); /*변수생성 : 반환값을 저장할 용도*/
begin
    --아이디를 히든 처리하기 위한 함수
    retStr := rpad(substr(idStr,1,1), length(idStr),'*');
    return retStr;
end;
/
--생성한 fillAsterik함수 호출
select fillAsterik('kosmo') from adbook;
select fillAsterik('nakasabal') from adbook;
select fillAsterik(id) from adbook;

-------------------------------------------

--substrb():바이트수대로 자름
--substr():글자씩 잘라짐

--한글 같은 경우는 테스트를 해보고선 위의 두 함수 중 선택해 
--사용해야할듯


---------------------------------------------------------
--예제2-1] 프로시저 : MyMemberInsert(id, pw, name, result)

/*
 시나리오] member테이블에 새로운 회원정보를 입력하는 프로시저를 생성하시오
    입력값: 아이디, 패스워드, 이름
*/

create or replace procedure MyMemberInsert (
    /* Java에서 전달한 인수를 받을 인파라미터    */
    p_id in varchar2,
    p_pass in varchar2,
    p_name in varchar2,
    /*입력 성공여부를 반환할 아웃파라미터*/
    returnVal out number
    )
is
begin
    --인파라미터를 통해 insert쿼리문을 작성한다.
    insert into member (id, pass, name)
        values (p_id, p_pass, p_name);
    --입력이 정상적으로 처리되면 true를 반환한다.    
    if sql%found then
        --입력에 성공한 행의 갯수를 얻어와서 아웃파라미터에 저장
        returnVal := sql%rowcount;
        --행의 변화가 생기므로 commit해야한다.
        commit;
    else
        --실패한 경우에는 0을 반환한다.
        returnVal := 0;
    end if;
    --프로시져는 별도의 반환 없이 아웃파라미터에 값을 설정하면 된다.
end;
/

set serveroutput on;
--i_result라는 바인드 변수
var i_result varchar2(10);
execute MyMemberInsert ('pro01', '1234', '프로시저1', :i_result);
print i_result;

select * from member;



---------------------------------------------------------
/*
--예제3-1] 프로시저 : MyMemberDelete()


시나리오] member테이블에서 레코드를 삭제하는 프로시저를 생성하시오
    파라미터 : In => member_id(아이디)
                    Out => returnVal(SUCCESS/FAIL 반환)   */
                    
                    
create or replace procedure MyMemberDelete (
   
   /*in, out 파라미터는 모두 문자형으로 정의*/
    member_id in varchar2,
    returnVal out varchar2
    )
is
    --변수가 필요 없는 경우 생략이 가능하다.
begin
    --인파라미터로 전달된 아이디 통해 delete쿼리문 작성
    delete from member where id=member_id;
    
    if SQL%Found then
        --회원 레코드가 정상적으로 삭제되면..
        returnVal := 'SUCCESS';
        --커밋한다.
        commit;
    else    
        --조건에 일치하는 레코드가 없다면 fail을 반환한다.
        returnVal := 'FAIL';
    end if;
end;
/

set serveroutput on;

--바인드 변수 생성 및 프로시져 실행 확인
var delete_var varchar2(10);
execute MyMemberDelete('dfds', :delete_var); --삭제되어서 성공
execute MyMemberDelete('pro01', :delete_var); --삭제되서 성공
execute MyMemberDelete('정지훈', :delete_var); --아이디 아이라 실패


print delete_var;


-----------------------------------------------------------

--예제4-1] 프로시저 : KosmoMemberAuth


/*
시나리오] 아이디와 패스워드를 매개변수로 전달받아서 
회원인지 여부를 판단하는 프로시저를 작성하시오. 


    매개변수 : 
        In -> user_id, user_pass
        Out -> returnVal
    반환값 : 
        0 -> 회원인증실패(둘다틀림)
        1 -> 아이디는 일치하나 패스워드가 틀린경우
        2 -> 아이디/패스워드 모두 일치하여 회원인증 성공
    프로시저명 : MyMemberAuth
*/




create or replace procedure MyMemberAuth (
    /*인파라미터: 자바에서 전달되는 아이디, 패스워드*/
    user_id in varchar2,
    user_pass in varchar2,
    /*아웃파라미터: 회원 인증 여부를 판단 후 반환할 값*/
    returnVal out number
)
is
    -- count(*)를 통해 반환되는 값을 저장한다.
    member_count number(1) := 0;
    --조회한 회원 정보의 패스워드를 저장한다.
    member_pw varchar(50);
    
begin
    --해당 아이디가 존재하는지 판단하는 select문 작성
    select count(*) into member_count
    from member where id=user_id;
    
    --회원아이디가 존재하는 경우라면..
    if member_count=1 then
        --패스워드 확인을 위해 두번째 쿼리문을 실행한다.
        select pass into member_pw
            from member where id=user_id;
        --인파라미터로 전달된 비번과 DB에서 가져온 비번을 비교한다.
        if member_pw=user_pass then
            --아이디/비번이 모두 일치하는 경우
            returnVal := 2;
        else
            --비번이 틀린 경우
            returnVal := 1;
        end if;
    else
        --회원 정보가 없는 경우
        returnVal := 0;
    end if;
end;
/

variable member_auth number;

execute MyMemberAuth('test1', '1234', :member_auth);
print member_auth; --2 아이디 패스워드 둘 다 일치
execute MyMemberAuth('test1', '1234 암호틀림', :member_auth);
print member_auth; --1 아이디 패스워드 중 하나 불일치

execute MyMemberAuth('yugeyeom틀림', '1234',:member_auth);
print member_auth; --0 인덱스 내 데이터 자체가 존재하지 않은 아이디





alter session set "_ORACLE_SCRIPT"=true;
--계정을 생성한 후 education계정을 등록하고 해당 워크시트에 연결한다.
create user education IDENTIFIED BY 1234;
grant CONNECT, RESOURCE, unlimited tablespace TO education;
--회원관리를 위한 member 테이블을 생성한다.

create table member
(
    id VARCHAR2(30) NOT NULL, /*회원아이디*/
    pass VARCHAR2(40) NOT NULL, /*패스워드*/
    name VARCHAR2(50) NOT NULL, /*이름*/
    regidate DATE DEFAULT SYSDATE, /*등록일*/
    PRIMARY KEY(id)
);    
--회원테이블에 더미데이터를 입력한다.
insert into member (id, pass, name) values ('test','1234','테스트');

commit;

--게시판 테이블을 생성한다.

create table board
(
    num number primary key, /*게시판 일련번호*/
    title varchar2(200) not null, /*제목*/
    content varchar2(2000) not null, /*내용*/
    id varchar2(30) not null, /*작성자의 아이디*/
    postdate date default sysdate not null, /*작성일*/
    visitcount number default 0 not null /*조회수*/
);    

delete from board;

------------------------------------------------------------------
--게시판에 테이블에 더미데이터를 입력한다.
delete from board;
/*
게시판 테이블에는 default 제약조건이 지정된 컬럼이 2개가 있으므로
디폴트값 입력을 위해서는 아래와 같이 컬럼명을 지정하는 것이 좋다.
*/

--더미데이터 입력방법1
insert into board (num, title, content, id ) 
    values (1,'더미1데이터','내용입니다.','test');
  
----더미데이터 입력방법2 
--컬럼명을 명시하지 않는 경우 전체 컬럼을 대상으로 기술해야 한다. 따라서
--postdate와 visitcount부분에 해당하는 sysdate, 0까지 다 기입해줌..
insert into board values (2, '더미2데이터', '내용2입니다.', 'test',
    sysdate, 0);

    
    
select * from member;    
select * from board; 
desc board;

commit;

/*
퀴즈] 위에서 생성한 테이블에 외래키와 시퀀스를 설정하시오.
1. 외래키명 : board_mem_fk
board 테이블의 id 컬럼이 member 테이블의 id 컬럼을 참조하도록 외래키를 생성
2. 시퀀스명 : seq_board_num
board 테이블에 데이터를 입력시 num 컬럼이 자동증가 할 수 있도록 시퀀스를 생성
*/


--외래키 생성: 이미 만들어진 기존 테이블에 수정(add)을 해주는 alter사용. 
alter table board add 
    constraint board_mem_fk
    foreign key (id) references member(id);

--만약 테이블 생성 시에는 아래와 같이 외래키를 넣어준다.

/*
create table board
(
id varchar2(30) not null constraint board_mem_fk
    references member(id),
   
);    
*/ 

--시퀀스 생성
create sequence seq_board_num
    start with 1
    increment by 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;


--데이터사전에서 확인하기

select * from user_cons_columns; /*외래키생성내역 확인*/
select * from user_sequences; /*시퀀스생성내역 확인*/

--시퀀스 값 확인하기
select seq_board_num.nextval from dual;
select seq_board_num.currval from dual;



--member 테이블에서 게시물 추출 시 날짜 형식 지정
SELECT id, pass, name, regidate,
    to_char(regidate, 'yyyy.mm.dd hh24:mi') d1 
FROM member;

select * from member;

--memeber 테이블에 새로운 레코드를  삽입해보자.
insert into member values ('test10', '1010', '테스터10', sysdate);

commit;

select * from member;

--memeber 테이블을 이름으로 검색하기

select * from member where name like'%st%';



/*
20221101
*/
--------------------------------------------------------------
--JDBC > CallableStatement  인터페이스 사용하기

--substr(문자열 혹은 컬럼, 시작인덱스, 길이): 시작인덱스부터 길이만큼 잘라낸다.
select substr('hongildong',1,1)from dual;
--rpad(문자열 혹은 컬럼,길이, 체을 문자): 문자열의 남은 길이를 문자로 채운다.
select rpad('h',5, '*') from dual; --h****
select rpad('2',7, '*') from dual; --2******


--문자열(아이디)의 첫글자를 제외한 나머지 부분을 *로 채운다.
--아이디를 게시판 리스트에 출력 시 히든처리 해야할 때 활용할 수 있다.
select rpad(substr('hongildong',1,1), length('hongildong'),'*')
    from dual;


/*
시나리오] 매개변수로 회원아이디(문자열)을 받으면 첫문자를 제외한 나머지 부분
*로 변환하는 함수를 생성하시오.
*/
create or replace function fillAsterik (
    idStr varchar2
) /*매개변수는 문자형으로 설정*/
return varchar2 /*반환값도 문자형으로 설정*/
is retStr varchar2(50); /*변수생성 : 반환값을 저장할 용도*/
begin
    --아이디를 히든 처리하기 위한 함수
    retStr := rpad(substr(idStr,1,1), length(idStr),'*');
    return retStr;
end;
/
--생성한 fillAsterik함수 호출
select fillAsterik('kosmo') from dual;
select fillAsterik('nakasabal') from dual;
select fillAsterik(id) from member;

-------------------------------------------

--substrb():바이트수대로 자름
--substr():글자씩 잘라짐

--한글 같은 경우는 테스트를 해보고선 위의 두 함수 중 선택해 
--사용해야할듯


---------------------------------------------------------
--예제2-1] 프로시저 : MyMemberInsert(id, pw, name, result)

/*
 시나리오] member테이블에 새로운 회원정보를 입력하는 프로시저를 생성하시오
    입력값: 아이디, 패스워드, 이름
*/

create or replace procedure MyMemberInsert (
    /* Java에서 전달한 인수를 받을 인파라미터    */
    p_id in varchar2,
    p_pass in varchar2,
    p_name in varchar2,
    /*입력 성공여부를 반환할 아웃파라미터*/
    returnVal out number
    )
is
begin
    --인파라미터를 통해 insert쿼리문을 작성한다.
    insert into member (id, pass, name)
        values (p_id, p_pass, p_name);
    --입력이 정상적으로 처리되면 true를 반환한다.    
    if sql%found then
        --입력에 성공한 행의 갯수를 얻어와서 아웃파라미터에 저장
        returnVal := sql%rowcount;
        --행의 변화가 생기므로 commit해야한다.
        commit;
    else
        --실패한 경우에는 0을 반환한다.
        returnVal := 0;
    end if;
    --프로시져는 별도의 반환 없이 아웃파라미터에 값을 설정하면 된다.
end;
/

set serveroutput on;
--i_result라는 바인드 변수
var i_result varchar2(10);
execute MyMemberInsert ('pro01', '1234', '프로시저1', :i_result);
print i_result;

select * from member;



---------------------------------------------------------
/*
--예제3-1] 프로시저 : MyMemberDelete()


시나리오] member테이블에서 레코드를 삭제하는 프로시저를 생성하시오
    파라미터 : In => member_id(아이디)
                    Out => returnVal(SUCCESS/FAIL 반환)   */
                    
                    
create or replace procedure MyMemberDelete (
   
   /*in, out 파라미터는 모두 문자형으로 정의*/
    member_id in varchar2,
    returnVal out varchar2
    )
is
    --변수가 필요 없는 경우 생략이 가능하다.
begin
    --인파라미터로 전달된 아이디 통해 delete쿼리문 작성
    delete from member where id=member_id;
    
    if SQL%Found then
        --회원 레코드가 정상적으로 삭제되면..
        returnVal := 'SUCCESS';
        --커밋한다.
        commit;
    else    
        --조건에 일치하는 레코드가 없다면 fail을 반환한다.
        returnVal := 'FAIL';
    end if;
end;
/

set serveroutput on;

--바인드 변수 생성 및 프로시져 실행 확인
var delete_var varchar2(10);
execute MyMemberDelete('dfds', :delete_var); --삭제되어서 성공
execute MyMemberDelete('pro01', :delete_var); --삭제되서 성공
execute MyMemberDelete('정지훈', :delete_var); --아이디 아이라 실패


print delete_var;


-----------------------------------------------------------

--예제4-1] 프로시저 : KosmoMemberAuth


/*
시나리오] 아이디와 패스워드를 매개변수로 전달받아서 회원인지 여부를 판단하는 프로시저를 작성하시오. 
    매개변수 : 
        In -> user_id, user_pass
        Out -> returnVal
    반환값 : 
        0 -> 회원인증실패(둘다틀림)
        1 -> 아이디는 일치하나 패스워드가 틀린경우
        2 -> 아이디/패스워드 모두 일치하여 회원인증 성공
    프로시저명 : MyMemberAuth
*/




create or replace procedure MyMemberAuth (
    /*인파라미터: 자바에서 전달되는 아이디, 패스워드*/
    user_id in varchar2,
    user_pass in varchar2,
    /*아웃파라미터: 회원 인증 여부를 판단 후 반환할 값*/
    returnVal out number
)
is
    -- count(*)를 통해 반환되는 값을 저장한다.
    member_count number(1) := 0;
    --조회한 회원 정보의 패스워드를 저장한다.
    member_pw varchar(50);
    
begin
    --해당 아이디가 존재하는지 판단하는 select문 작성
    select count(*) into member_count
    from member where id=user_id;
    
    --회원아이디가 존재하는 경우라면..
    if member_count=1 then
        --패스워드 확인을 위해 두번째 쿼리문을 실행한다.
        select pass into member_pw
            from member where id=user_id;
        --인파라미터로 전달된 비번과 DB에서 가져온 비번을 비교한다.
        if member_pw=user_pass then
            --아이디/비번이 모두 일치하는 경우
            returnVal := 2;
        else
            --비번이 틀린 경우
            returnVal := 1;
        end if;
    else
        --회원 정보가 없는 경우
        returnVal := 0;
    end if;
end;
/

variable member_auth number;

execute MyMemberAuth('test1', '1234', :member_auth);
print member_auth; --2 아이디 패스워드 둘 다 일치
execute MyMemberAuth('test1', '1234암호틀림', :member_auth);
print member_auth; --1 아이디 패스워드 중 하나 불일치

execute MyMemberAuth('yugeyeom틀림', '1234',:member_auth);
print member_auth; --0 인덱스 내 데이터 자체가 존재하지 않은 아이디









SELECT G_IDX, GOODS_NAME, GOODS_PRICE, to_char(regidate, 'yyyy.mm.dd hh24:mi') d1,P_CODE FROM sh_goods;

insert into sh_goods values (G_IDX, GOODS_NAME, GOODS_PRICE);