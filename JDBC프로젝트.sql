/*
Java���� ó������ JDBC ���α׷��� �غ���
*/




--�켱 system�������� ������ �� ���ο� ������ �����Ѵ�.


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
--�տ��� ������ �������� �̿��ؼ� 3~5�� ������ ��ǰ�ڵ� ���ڵ带 �Է��Ѵ�.
--����, ����, �Ƿ� ��

insert into sh_product_code (p_code, category_name) values (1, 'outer');
insert into sh_product_code (p_code, category_name) values (2, 'inner');
insert into sh_product_code (p_code, category_name) values (3, 'denim');
insert into sh_product_code (p_code, category_name) values (4, 'acc');


--�տ��� ������ �������� �̿��ؼ� 5~10�� ������ ��ǰ ���ڵ带 �Է��Ѵ�. 
--�����, ��Ź�� / ���ǿ���, �ѱռ� / ���е�, ���뽺, û���� ��

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



alter sequence ��������
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
--JDBC > CallableStatement  �������̽� ����ϱ�

--substr(���ڿ� Ȥ�� �÷�, �����ε���, ����): �����ε������� ���̸�ŭ �߶󳽴�.
select substr('������',1,1)from adbook;
--rpad(���ڿ� Ȥ�� �÷�,����, ü�� ����): ���ڿ��� ���� ���̸� ���ڷ� ä���.
select rpad('h',5, '*') from dual; --h****
select rpad('2',7, '*') from dual; --2******


--���ڿ�(���̵�)�� ù���ڸ� ������ ������ �κ��� *�� ä���.
--���̵� �Խ��� ����Ʈ�� ��� �� ����ó�� �ؾ��� �� Ȱ���� �� �ִ�.
select rpad(substr('������',1,1), length('������'),'*')
    from adbook;


/*
�ó�����] �Ű������� ȸ�����̵�(���ڿ�)�� ������ ù���ڸ� ������ ������ �κ�
*�� ��ȯ�ϴ� �Լ��� �����Ͻÿ�.
*/
create or replace function fillAsterik (
    idStr varchar2
) /*�Ű������� ���������� ����*/
return varchar2 /*��ȯ���� ���������� ����*/
is retStr varchar2(50); /*�������� : ��ȯ���� ������ �뵵*/
begin
    --���̵� ���� ó���ϱ� ���� �Լ�
    retStr := rpad(substr(idStr,1,1), length(idStr),'*');
    return retStr;
end;
/
--������ fillAsterik�Լ� ȣ��
select fillAsterik('kosmo') from adbook;
select fillAsterik('nakasabal') from adbook;
select fillAsterik(id) from adbook;

-------------------------------------------

--substrb():����Ʈ����� �ڸ�
--substr():���ھ� �߶���

--�ѱ� ���� ���� �׽�Ʈ�� �غ��� ���� �� �Լ� �� ������ 
--����ؾ��ҵ�


---------------------------------------------------------
--����2-1] ���ν��� : MyMemberInsert(id, pw, name, result)

/*
 �ó�����] member���̺� ���ο� ȸ�������� �Է��ϴ� ���ν����� �����Ͻÿ�
    �Է°�: ���̵�, �н�����, �̸�
*/

create or replace procedure MyMemberInsert (
    /* Java���� ������ �μ��� ���� ���Ķ����    */
    p_id in varchar2,
    p_pass in varchar2,
    p_name in varchar2,
    /*�Է� �������θ� ��ȯ�� �ƿ��Ķ����*/
    returnVal out number
    )
is
begin
    --���Ķ���͸� ���� insert�������� �ۼ��Ѵ�.
    insert into member (id, pass, name)
        values (p_id, p_pass, p_name);
    --�Է��� ���������� ó���Ǹ� true�� ��ȯ�Ѵ�.    
    if sql%found then
        --�Է¿� ������ ���� ������ ���ͼ� �ƿ��Ķ���Ϳ� ����
        returnVal := sql%rowcount;
        --���� ��ȭ�� ����Ƿ� commit�ؾ��Ѵ�.
        commit;
    else
        --������ ��쿡�� 0�� ��ȯ�Ѵ�.
        returnVal := 0;
    end if;
    --���ν����� ������ ��ȯ ���� �ƿ��Ķ���Ϳ� ���� �����ϸ� �ȴ�.
end;
/

set serveroutput on;
--i_result��� ���ε� ����
var i_result varchar2(10);
execute MyMemberInsert ('pro01', '1234', '���ν���1', :i_result);
print i_result;

select * from member;



---------------------------------------------------------
/*
--����3-1] ���ν��� : MyMemberDelete()


�ó�����] member���̺��� ���ڵ带 �����ϴ� ���ν����� �����Ͻÿ�
    �Ķ���� : In => member_id(���̵�)
                    Out => returnVal(SUCCESS/FAIL ��ȯ)   */
                    
                    
create or replace procedure MyMemberDelete (
   
   /*in, out �Ķ���ʹ� ��� ���������� ����*/
    member_id in varchar2,
    returnVal out varchar2
    )
is
    --������ �ʿ� ���� ��� ������ �����ϴ�.
begin
    --���Ķ���ͷ� ���޵� ���̵� ���� delete������ �ۼ�
    delete from member where id=member_id;
    
    if SQL%Found then
        --ȸ�� ���ڵ尡 ���������� �����Ǹ�..
        returnVal := 'SUCCESS';
        --Ŀ���Ѵ�.
        commit;
    else    
        --���ǿ� ��ġ�ϴ� ���ڵ尡 ���ٸ� fail�� ��ȯ�Ѵ�.
        returnVal := 'FAIL';
    end if;
end;
/

set serveroutput on;

--���ε� ���� ���� �� ���ν��� ���� Ȯ��
var delete_var varchar2(10);
execute MyMemberDelete('dfds', :delete_var); --�����Ǿ ����
execute MyMemberDelete('pro01', :delete_var); --�����Ǽ� ����
execute MyMemberDelete('������', :delete_var); --���̵� ���̶� ����


print delete_var;


-----------------------------------------------------------

--����4-1] ���ν��� : KosmoMemberAuth


/*
�ó�����] ���̵�� �н����带 �Ű������� ���޹޾Ƽ� 
ȸ������ ���θ� �Ǵ��ϴ� ���ν����� �ۼ��Ͻÿ�. 


    �Ű����� : 
        In -> user_id, user_pass
        Out -> returnVal
    ��ȯ�� : 
        0 -> ȸ����������(�Ѵ�Ʋ��)
        1 -> ���̵�� ��ġ�ϳ� �н����尡 Ʋ�����
        2 -> ���̵�/�н����� ��� ��ġ�Ͽ� ȸ������ ����
    ���ν����� : MyMemberAuth
*/




create or replace procedure MyMemberAuth (
    /*���Ķ����: �ڹٿ��� ���޵Ǵ� ���̵�, �н�����*/
    user_id in varchar2,
    user_pass in varchar2,
    /*�ƿ��Ķ����: ȸ�� ���� ���θ� �Ǵ� �� ��ȯ�� ��*/
    returnVal out number
)
is
    -- count(*)�� ���� ��ȯ�Ǵ� ���� �����Ѵ�.
    member_count number(1) := 0;
    --��ȸ�� ȸ�� ������ �н����带 �����Ѵ�.
    member_pw varchar(50);
    
begin
    --�ش� ���̵� �����ϴ��� �Ǵ��ϴ� select�� �ۼ�
    select count(*) into member_count
    from member where id=user_id;
    
    --ȸ�����̵� �����ϴ� �����..
    if member_count=1 then
        --�н����� Ȯ���� ���� �ι�° �������� �����Ѵ�.
        select pass into member_pw
            from member where id=user_id;
        --���Ķ���ͷ� ���޵� ����� DB���� ������ ����� ���Ѵ�.
        if member_pw=user_pass then
            --���̵�/����� ��� ��ġ�ϴ� ���
            returnVal := 2;
        else
            --����� Ʋ�� ���
            returnVal := 1;
        end if;
    else
        --ȸ�� ������ ���� ���
        returnVal := 0;
    end if;
end;
/

variable member_auth number;

execute MyMemberAuth('test1', '1234', :member_auth);
print member_auth; --2 ���̵� �н����� �� �� ��ġ
execute MyMemberAuth('test1', '1234 ��ȣƲ��', :member_auth);
print member_auth; --1 ���̵� �н����� �� �ϳ� ����ġ

execute MyMemberAuth('yugeyeomƲ��', '1234',:member_auth);
print member_auth; --0 �ε��� �� ������ ��ü�� �������� ���� ���̵�





alter session set "_ORACLE_SCRIPT"=true;
--������ ������ �� education������ ����ϰ� �ش� ��ũ��Ʈ�� �����Ѵ�.
create user education IDENTIFIED BY 1234;
grant CONNECT, RESOURCE, unlimited tablespace TO education;
--ȸ�������� ���� member ���̺��� �����Ѵ�.

create table member
(
    id VARCHAR2(30) NOT NULL, /*ȸ�����̵�*/
    pass VARCHAR2(40) NOT NULL, /*�н�����*/
    name VARCHAR2(50) NOT NULL, /*�̸�*/
    regidate DATE DEFAULT SYSDATE, /*�����*/
    PRIMARY KEY(id)
);    
--ȸ�����̺� ���̵����͸� �Է��Ѵ�.
insert into member (id, pass, name) values ('test','1234','�׽�Ʈ');

commit;

--�Խ��� ���̺��� �����Ѵ�.

create table board
(
    num number primary key, /*�Խ��� �Ϸù�ȣ*/
    title varchar2(200) not null, /*����*/
    content varchar2(2000) not null, /*����*/
    id varchar2(30) not null, /*�ۼ����� ���̵�*/
    postdate date default sysdate not null, /*�ۼ���*/
    visitcount number default 0 not null /*��ȸ��*/
);    

delete from board;

------------------------------------------------------------------
--�Խ��ǿ� ���̺� ���̵����͸� �Է��Ѵ�.
delete from board;
/*
�Խ��� ���̺��� default ���������� ������ �÷��� 2���� �����Ƿ�
����Ʈ�� �Է��� ���ؼ��� �Ʒ��� ���� �÷����� �����ϴ� ���� ����.
*/

--���̵����� �Է¹��1
insert into board (num, title, content, id ) 
    values (1,'����1������','�����Դϴ�.','test');
  
----���̵����� �Է¹��2 
--�÷����� ������� �ʴ� ��� ��ü �÷��� ������� ����ؾ� �Ѵ�. ����
--postdate�� visitcount�κп� �ش��ϴ� sysdate, 0���� �� ��������..
insert into board values (2, '����2������', '����2�Դϴ�.', 'test',
    sysdate, 0);

    
    
select * from member;    
select * from board; 
desc board;

commit;

/*
����] ������ ������ ���̺� �ܷ�Ű�� �������� �����Ͻÿ�.
1. �ܷ�Ű�� : board_mem_fk
board ���̺��� id �÷��� member ���̺��� id �÷��� �����ϵ��� �ܷ�Ű�� ����
2. �������� : seq_board_num
board ���̺� �����͸� �Է½� num �÷��� �ڵ����� �� �� �ֵ��� �������� ����
*/


--�ܷ�Ű ����: �̹� ������� ���� ���̺� ����(add)�� ���ִ� alter���. 
alter table board add 
    constraint board_mem_fk
    foreign key (id) references member(id);

--���� ���̺� ���� �ÿ��� �Ʒ��� ���� �ܷ�Ű�� �־��ش�.

/*
create table board
(
id varchar2(30) not null constraint board_mem_fk
    references member(id),
   
);    
*/ 

--������ ����
create sequence seq_board_num
    start with 1
    increment by 1
    minvalue 1
    nomaxvalue
    nocycle
    nocache;


--�����ͻ������� Ȯ���ϱ�

select * from user_cons_columns; /*�ܷ�Ű�������� Ȯ��*/
select * from user_sequences; /*�������������� Ȯ��*/

--������ �� Ȯ���ϱ�
select seq_board_num.nextval from dual;
select seq_board_num.currval from dual;



--member ���̺��� �Խù� ���� �� ��¥ ���� ����
SELECT id, pass, name, regidate,
    to_char(regidate, 'yyyy.mm.dd hh24:mi') d1 
FROM member;

select * from member;

--memeber ���̺� ���ο� ���ڵ带  �����غ���.
insert into member values ('test10', '1010', '�׽���10', sysdate);

commit;

select * from member;

--memeber ���̺��� �̸����� �˻��ϱ�

select * from member where name like'%st%';



/*
20221101
*/
--------------------------------------------------------------
--JDBC > CallableStatement  �������̽� ����ϱ�

--substr(���ڿ� Ȥ�� �÷�, �����ε���, ����): �����ε������� ���̸�ŭ �߶󳽴�.
select substr('hongildong',1,1)from dual;
--rpad(���ڿ� Ȥ�� �÷�,����, ü�� ����): ���ڿ��� ���� ���̸� ���ڷ� ä���.
select rpad('h',5, '*') from dual; --h****
select rpad('2',7, '*') from dual; --2******


--���ڿ�(���̵�)�� ù���ڸ� ������ ������ �κ��� *�� ä���.
--���̵� �Խ��� ����Ʈ�� ��� �� ����ó�� �ؾ��� �� Ȱ���� �� �ִ�.
select rpad(substr('hongildong',1,1), length('hongildong'),'*')
    from dual;


/*
�ó�����] �Ű������� ȸ�����̵�(���ڿ�)�� ������ ù���ڸ� ������ ������ �κ�
*�� ��ȯ�ϴ� �Լ��� �����Ͻÿ�.
*/
create or replace function fillAsterik (
    idStr varchar2
) /*�Ű������� ���������� ����*/
return varchar2 /*��ȯ���� ���������� ����*/
is retStr varchar2(50); /*�������� : ��ȯ���� ������ �뵵*/
begin
    --���̵� ���� ó���ϱ� ���� �Լ�
    retStr := rpad(substr(idStr,1,1), length(idStr),'*');
    return retStr;
end;
/
--������ fillAsterik�Լ� ȣ��
select fillAsterik('kosmo') from dual;
select fillAsterik('nakasabal') from dual;
select fillAsterik(id) from member;

-------------------------------------------

--substrb():����Ʈ����� �ڸ�
--substr():���ھ� �߶���

--�ѱ� ���� ���� �׽�Ʈ�� �غ��� ���� �� �Լ� �� ������ 
--����ؾ��ҵ�


---------------------------------------------------------
--����2-1] ���ν��� : MyMemberInsert(id, pw, name, result)

/*
 �ó�����] member���̺� ���ο� ȸ�������� �Է��ϴ� ���ν����� �����Ͻÿ�
    �Է°�: ���̵�, �н�����, �̸�
*/

create or replace procedure MyMemberInsert (
    /* Java���� ������ �μ��� ���� ���Ķ����    */
    p_id in varchar2,
    p_pass in varchar2,
    p_name in varchar2,
    /*�Է� �������θ� ��ȯ�� �ƿ��Ķ����*/
    returnVal out number
    )
is
begin
    --���Ķ���͸� ���� insert�������� �ۼ��Ѵ�.
    insert into member (id, pass, name)
        values (p_id, p_pass, p_name);
    --�Է��� ���������� ó���Ǹ� true�� ��ȯ�Ѵ�.    
    if sql%found then
        --�Է¿� ������ ���� ������ ���ͼ� �ƿ��Ķ���Ϳ� ����
        returnVal := sql%rowcount;
        --���� ��ȭ�� ����Ƿ� commit�ؾ��Ѵ�.
        commit;
    else
        --������ ��쿡�� 0�� ��ȯ�Ѵ�.
        returnVal := 0;
    end if;
    --���ν����� ������ ��ȯ ���� �ƿ��Ķ���Ϳ� ���� �����ϸ� �ȴ�.
end;
/

set serveroutput on;
--i_result��� ���ε� ����
var i_result varchar2(10);
execute MyMemberInsert ('pro01', '1234', '���ν���1', :i_result);
print i_result;

select * from member;



---------------------------------------------------------
/*
--����3-1] ���ν��� : MyMemberDelete()


�ó�����] member���̺��� ���ڵ带 �����ϴ� ���ν����� �����Ͻÿ�
    �Ķ���� : In => member_id(���̵�)
                    Out => returnVal(SUCCESS/FAIL ��ȯ)   */
                    
                    
create or replace procedure MyMemberDelete (
   
   /*in, out �Ķ���ʹ� ��� ���������� ����*/
    member_id in varchar2,
    returnVal out varchar2
    )
is
    --������ �ʿ� ���� ��� ������ �����ϴ�.
begin
    --���Ķ���ͷ� ���޵� ���̵� ���� delete������ �ۼ�
    delete from member where id=member_id;
    
    if SQL%Found then
        --ȸ�� ���ڵ尡 ���������� �����Ǹ�..
        returnVal := 'SUCCESS';
        --Ŀ���Ѵ�.
        commit;
    else    
        --���ǿ� ��ġ�ϴ� ���ڵ尡 ���ٸ� fail�� ��ȯ�Ѵ�.
        returnVal := 'FAIL';
    end if;
end;
/

set serveroutput on;

--���ε� ���� ���� �� ���ν��� ���� Ȯ��
var delete_var varchar2(10);
execute MyMemberDelete('dfds', :delete_var); --�����Ǿ ����
execute MyMemberDelete('pro01', :delete_var); --�����Ǽ� ����
execute MyMemberDelete('������', :delete_var); --���̵� ���̶� ����


print delete_var;


-----------------------------------------------------------

--����4-1] ���ν��� : KosmoMemberAuth


/*
�ó�����] ���̵�� �н����带 �Ű������� ���޹޾Ƽ� ȸ������ ���θ� �Ǵ��ϴ� ���ν����� �ۼ��Ͻÿ�. 
    �Ű����� : 
        In -> user_id, user_pass
        Out -> returnVal
    ��ȯ�� : 
        0 -> ȸ����������(�Ѵ�Ʋ��)
        1 -> ���̵�� ��ġ�ϳ� �н����尡 Ʋ�����
        2 -> ���̵�/�н����� ��� ��ġ�Ͽ� ȸ������ ����
    ���ν����� : MyMemberAuth
*/




create or replace procedure MyMemberAuth (
    /*���Ķ����: �ڹٿ��� ���޵Ǵ� ���̵�, �н�����*/
    user_id in varchar2,
    user_pass in varchar2,
    /*�ƿ��Ķ����: ȸ�� ���� ���θ� �Ǵ� �� ��ȯ�� ��*/
    returnVal out number
)
is
    -- count(*)�� ���� ��ȯ�Ǵ� ���� �����Ѵ�.
    member_count number(1) := 0;
    --��ȸ�� ȸ�� ������ �н����带 �����Ѵ�.
    member_pw varchar(50);
    
begin
    --�ش� ���̵� �����ϴ��� �Ǵ��ϴ� select�� �ۼ�
    select count(*) into member_count
    from member where id=user_id;
    
    --ȸ�����̵� �����ϴ� �����..
    if member_count=1 then
        --�н����� Ȯ���� ���� �ι�° �������� �����Ѵ�.
        select pass into member_pw
            from member where id=user_id;
        --���Ķ���ͷ� ���޵� ����� DB���� ������ ����� ���Ѵ�.
        if member_pw=user_pass then
            --���̵�/����� ��� ��ġ�ϴ� ���
            returnVal := 2;
        else
            --����� Ʋ�� ���
            returnVal := 1;
        end if;
    else
        --ȸ�� ������ ���� ���
        returnVal := 0;
    end if;
end;
/

variable member_auth number;

execute MyMemberAuth('test1', '1234', :member_auth);
print member_auth; --2 ���̵� �н����� �� �� ��ġ
execute MyMemberAuth('test1', '1234��ȣƲ��', :member_auth);
print member_auth; --1 ���̵� �н����� �� �ϳ� ����ġ

execute MyMemberAuth('yugeyeomƲ��', '1234',:member_auth);
print member_auth; --0 �ε��� �� ������ ��ü�� �������� ���� ���̵�









SELECT G_IDX, GOODS_NAME, GOODS_PRICE, to_char(regidate, 'yyyy.mm.dd hh24:mi') d1,P_CODE FROM sh_goods;

insert into sh_goods values (G_IDX, GOODS_NAME, GOODS_PRICE);