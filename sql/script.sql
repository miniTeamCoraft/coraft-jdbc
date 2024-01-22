DROP TABLE IF EXISTS MEMBERS;
DROP TABLE IF EXISTS REGIST;
DROP TABLE IF EXISTS LECTURE_INFO;

CREATE TABLE IF NOT EXISTS MEMBERS(
	MEM_ID VARCHAR(20) PRIMARY KEY COMMENT '회원아이디',
	MEM_PWD VARCHAR(20) NOT NULL COMMENT '회원비밀번호',
	MEM_NAME VARCHAR(50) NOT NULL COMMENT '회원이름',
	MEM_AGE INT NOT NULL COMMENT '회원나이',
	MEM_GENDER CHAR(3) NOT NULL COMMENT '회원성별',
	PHONE VARCHAR(20) NOT NULL COMMENT '전화번호',
	EMAIL VARCHAR(50) COMMENT '이메일',
	MEM_POINT INT NOT NULL COMMENT '회원포인트',
	CHECK (MEM_GENDER IN ('남', '여'))
) ENGINE=INNODB COMMENT '회원정보';

INSERT INTO MEMBERS(MEM_ID, MEM_PWD, MEM_NAME, MEM_AGE, MEM_GENDER, PHONE, EMAIL, MEM_POINT)
VALUES
('id01', 'pwd01', '홍길동', 25, '남', '010-1054-5078', 'user01@mail.com', 100000),
('id02', 'pwd02', '김지윤', 21, '여', '010-3455-2343', 'user02@mail.com', 50000),
('id03', 'pwd03', '김현지', 18, '여', '010-3864-7642', 'user03@mail.com', 30000),
('id04', 'pwd04', '박다은', 45, '여', '010-2789-1087', 'user04@mail.com', 10000);

CREATE TABLE LECTURE_INFO (
	LEC_CODE INT AUTO_INCREMENT PRIMARY KEY COMMENT '강의코드',
	LEC_NAME VARCHAR(50) NOT NULL COMMENT '강의명',
	LEC_DATE VARCHAR(50) NOT NULL COMMENT '강의일',
	LEC_TIME VARCHAR(50) NOT NULL COMMENT '강의시간',
	LEC_PRICE INT NOT NULL COMMENT '강의가격'
) ENGINE=INNODB COMMENT '강의정보';


CREATE TABLE REGIST (
	MEM_ID VARCHAR(20) NOT NULL COMMENT '회원아이디',
	LEC_CODE INT NOT NULL COMMENT '강의코드',
	FOREIGN KEY (MEM_ID) REFERENCES MEMBERS (MEM_ID),
	FOREIGN KEY (LEC_CODE) REFERENCES LECTURE_INFO (LEC_CODE)
	-- foreign key 나열해 작성 가능한지 확인
) ENGINE=INNODB COMMENT '수강신청';


INSERT INTO LECTURE_INFO (LEC_CODE, LEC_NAME, LEC_DATE, LEC_TIME, LEC_PRICE)
VALUES
(null, '보컬 레슨 클래스', '2024년 1월 28일', '오후 6시30분 ~ 7시30분', 70000),
(null, '천연 비누 만들기 클래스', '2024년 1월 15일', '오후 3시 ~ 4시30분', 50000),
(null, '과자 만들기 클래스', '2024년 1월 20일', '오전 11시 ~ 오후 12시30분', 40000),
(null, '레진 손거울 만들기 클래스', '2024년 2월 3일', '오후 1시 ~ 3시', 30000),
(null, '전통 유리 공예 클래스', '2024년 2월 5일', '오후 1시30분 ~ 3시', 70000);