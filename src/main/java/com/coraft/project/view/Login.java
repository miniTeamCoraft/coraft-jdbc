package com.coraft.project.view;

import com.coraft.project.controller.MemberController;
import com.coraft.project.controller.PayController;
import com.coraft.project.dto.MemberDTO;

import java.util.Scanner;

public class Login {
    public static MemberController memcont = new MemberController();
    public static PayController paycont = new PayController();

    Scanner sc = new Scanner(System.in);

    public void mainLogin() {
        System.out.println("=================================================");
        System.out.println("\n  CORAFT에 오신걸 환영합니다.😊");
        while(true) {
            System.out.println("\n= 로그인 =========================================");
            System.out.println("1.로그인");
            System.out.println("2.회원가입");
            System.out.println("9.종료");
            System.out.println("-------------------------------------------------");
            System.out.print("메뉴를 선택하세요 : ");
            String num = sc.next();
            System.out.println("-------------------------------------------------");

            switch (num) {
                case "1" : memcont.login(doLogin()); break;
                case "2" : doRegist(); break;
                case "9" : System.out.println("CORAFT를 종료합니다."); return;
                default : System.out.println("잘못된 메뉴를 선택하셨습니다. 다시 입력해주세요."); break;
            }
        }
    }

    public MemberDTO doLogin() {
        sc.nextLine();
        System.out.print("아이디를 입력하세요 : ");
        String id = sc.nextLine();
        System.out.print("비밀번호를 입력하세요 : ");
        String pwd = sc.nextLine();
        System.out.println("-------------------------------------------------");
        return new MemberDTO(id, pwd);
    }

    public void doRegist() {
        System.out.println("\n= 회원가입 =========================================");
        sc.nextLine();
        String id = memcont.checkId();
        System.out.print("비밀번호를 입력하세요 : ");
        String pwd = sc.nextLine();
        System.out.print("이름을 입력하세요 : ");
        String name = sc.nextLine();
        System.out.print("나이를 입력하세요 : ");
        int age = sc.nextInt();
        System.out.print("성별을 입력하세요 (여 / 남) : ");
        String gender = sc.next();
        System.out.print("핸드폰 번호를 입력하세요(예시 : 010-0000-0000) : ");
        sc.nextLine();
        String phone = sc.nextLine();
        System.out.print("이메일을 입력하세요 : ");
        String email = sc.nextLine();

        System.out.println("-------------------------------------------------");

        MemberDTO user = new MemberDTO();
        user.setId(id);
        user.setPwd(pwd);
        user.setName(name);
        user.setAge(age);
        user.setGender(gender);
        user.setPhone(phone);
        user.setEmail(email);

        int result = memcont.regist(user);

        if(result > 0) {
            System.out.println("회원 등록 성공!");
        }else {
            System.out.println("회원 등록 실패!");
        }
    }
}
