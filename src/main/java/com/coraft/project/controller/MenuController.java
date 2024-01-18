package com.coraft.project.controller;

import com.coraft.project.dto.LectureDTO;
import com.coraft.project.dto.MemberDTO;
import com.coraft.project.view.Login;
import com.coraft.project.view.Payment;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static com.coraft.project.common.JDBCTemplate.close;
import static com.coraft.project.common.JDBCTemplate.getConnection;

public class MenuController {
    Scanner sc = new Scanner(System.in);
    Payment payment = new Payment();

    Properties prop = new Properties();

    public MenuController() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/coraft/project/mapper/lectures-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showListLecture(MemberDTO user) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("showAllLectures");

        try {
            pstmt = con.prepareStatement(query);

            rset = pstmt.executeQuery();

            while (rset.next()) {
                System.out.println("강의 이름 : " + rset.getString("LEC_NAME") + "  ||  날짜(시간) : " + rset.getString("LEC_DATE") + " ( " + rset.getString("LEC_TIME") + " )  ||  가격 : " + rset.getString("LEC_PRICE") + "원");
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
            // showListLecture(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
            close(con);
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
                    System.out.println(Login.lectures.get(0).toString());
                    payment.mainPayment(user, Login.lectures.get(0));
                    break;
                case "2":
                    System.out.println(Login.lectures.get(1).toString());
                    payment.mainPayment(user, Login.lectures.get(1));
                    break;
                case "3":
                    System.out.println(Login.lectures.get(2).toString());
                    payment.mainPayment(user, Login.lectures.get(2));
                    break;
                case "4":
                    System.out.println(Login.lectures.get(3).toString());
                    payment.mainPayment(user, Login.lectures.get(3));
                    break;
                case "5":
                    System.out.println(Login.lectures.get(4).toString());
                    payment.mainPayment(user, Login.lectures.get(4));
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
