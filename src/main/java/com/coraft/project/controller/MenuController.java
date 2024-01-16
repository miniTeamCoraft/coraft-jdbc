package com.coraft.project.controller;

import com.coraft.project.dto.LectureDTO;
import com.coraft.project.dto.MemberDTO;
import com.coraft.project.view.Payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuController {
    private List<LectureDTO> lectures;
    Scanner sc = new Scanner(System.in);
    Payment payment = new Payment();

    public MenuController() {
        lectures = new ArrayList<LectureDTO>();
        lectures.add(new LectureDTO("보컬 레슨 클래스", "2024년 1월 28일", "오후 6시30분 ~ 7시30분", 70000));
        lectures.add(new LectureDTO("천연 비누 만들기 클래스", "2024년 1월 15일", "오후 3시 ~ 4시30분", 50000));
        lectures.add(new LectureDTO("과자 만들기 클래스", "2024년 1월 20일", "오전 11시 ~ 오후 12시30분", 40000));
        lectures.add(new LectureDTO("레진 손거울 만들기 클래스", "2024년 2월 3일", "오후 1시 ~ 3시", 30000));
        lectures.add(new LectureDTO("전통 유리 공예 클래스", "2024년 2월 5일", "오후 1시30분 ~ 3시", 70000));
    }

    public void showListLecture(MemberDTO user) {

        System.out.println("\n= 강의목록 =========================================================================================");
        for (int i = 0; i < lectures.size(); i++) {
            System.out.println("강의 이름 : " + lectures.get(i).getLecName() + "  ||  날짜(시간) : " + lectures.get(i).getDate() + " ( " + lectures.get(i).getTime() + " )  ||  가격 : " + lectures.get(i).getLecPrice() + "원");
        }
        System.out.println("---------------------------------------------------------------------------------------------------");

        System.out.print("강의를 선택하시겠습니까? (Y / N) ");
        char answer = sc.next().toUpperCase().charAt(0);

        if (answer == 'Y') {
            selectLecture(user);
        } else if (answer == 'N') {
            System.out.println("이전 페이지로 돌아갑니다.");
        } else {
            System.out.println("잘못된 메뉴를 선택하셨습니다. 강의 목록으로 돌아갑니다.");
            showListLecture(user);        
        }
    }

    public void selectLecture(MemberDTO user) {
        String num;
        while (true) {
            System.out.println("\n= 강의선택 =========================================");
            System.out.println("1.'보컬 클래스'");
            System.out.println("2.'천연 비누 만들기'");
            System.out.println("3.'과자 만들기'");
            System.out.println("4.'레진 손거울 만들기'");
            System.out.println("5.'전통 유리 공예 클래스'");
            System.out.println("9. 뒤로가기");
            System.out.println("-------------------------------------------------");
            System.out.print("신청할 강의를 선택하세요 : ");
            sc.nextLine();
            num = sc.next();

            switch (num) {
                case "1":
                    System.out.println(lectures.get(0).toString());
                    payment.mainPayment(user, lectures.get(0));
                    break;
                case "2":
                    System.out.println(lectures.get(1).toString());
                    payment.mainPayment(user, lectures.get(1));
                    break;
                case "3":
                    System.out.println(lectures.get(2).toString());
                    payment.mainPayment(user, lectures.get(2));
                    break;
                case "4":
                    System.out.println(lectures.get(3).toString());
                    payment.mainPayment(user, lectures.get(3));
                    break;
                case "5":
                    System.out.println(lectures.get(4).toString());
                    payment.mainPayment(user, lectures.get(4));
                    break;
                case "9":
                    System.out.println("뒤로가기");
                    showListLecture(user); break;
                default: System.out.println("잘못된 강의를 선택하셨습니다.");
                    break;

            }
        }
    }
}
