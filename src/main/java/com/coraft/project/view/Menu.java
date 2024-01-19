package com.coraft.project.view;

import com.coraft.project.controller.MenuController;
import com.coraft.project.dto.MemberDTO;

import java.util.Scanner;

import static com.coraft.project.view.Login.memcont;
import static com.coraft.project.view.Login.paycont;

public class Menu {
    Scanner sc = new Scanner(System.in);

    public void mainMenu(MemberDTO user) {
        while (true) {
            System.out.println("\n= 메인메뉴 =========================================");
            System.out.println("1.강의 목록 보기");
            System.out.println("2.회원 정보 확인");
            System.out.println("3.수강신청 목록 확인");
            System.out.println("9.로그아웃");
            System.out.println("-------------------------------------------------");

            System.out.print("메뉴를 선택하세요 : ");
            String num = sc.nextLine();

            Login login = new Login();
            MenuController lectcont = new MenuController();

            switch (num) {
                case "1":
                    lectcont.showListLecture(user);
                    break;
                case "2":
                    memcont.memberInfo(user);
                    break;
                case "3": paycont.userSelectLec(user.getId());
                    break;
                case "9":
                    System.out.println("CORAFT를 로그아웃합니다. 감사합니다."); login.mainLogin(); break;
                    // 로그인 페이지로 바로 넘어가 로그아웃 후 다른 아이디로 로그인해도 수강이력이 남는 듯 수정 필요
                    // reture, break 둘다 사용 시 수강 목록, 수강선택 페이지로 넘어감 로그아웃 방법 다시 생각 필요
                default:
                    System.out.println("잘못된 메뉴를 선택하셨습니다.");
                    break;
            }
        }
    }
}
