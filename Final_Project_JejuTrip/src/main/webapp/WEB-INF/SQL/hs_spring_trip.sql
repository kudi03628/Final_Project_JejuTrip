show user;
-- USER��(��) "SYS"�Դϴ�.

-- ���� ���� ����Ŭ ���������� ������տ� c## ������ �ʰ� �����ϵ��� �ϰڴ�.
alter session set "_ORACLE_SCRIPT"=true;
-- Session��(��) ����Ǿ����ϴ�.

create user final_orauser2 identified by gclass default tablespace users;
-- User SEMI_ORAUSER2��(��) �����Ǿ����ϴ�.

grant connect, resource, create view, unlimited tablespace to final_orauser2;
-- Grant��(��) �����߽��ϴ�.