package com.coraft.project.controller;

import com.coraft.project.dto.LectureDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.coraft.project.common.JDBCTemplate.close;
import static com.coraft.project.common.JDBCTemplate.getConnection;
import static com.coraft.project.view.Login.memcont;
import static com.coraft.project.view.Menu.user;

public class PayController {
    Scanner sc = new Scanner(System.in);
    Properties prop = new Properties();

    public PayController() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/coraft/project/mapper/regist-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 포인트 차감 결제
    public void payBonusMember(LectureDTO lecture) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("insertLecture");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getId());
            pstmt.setInt(2, lecture.getLecCode());

            usePayPoint(lecture);

            result = pstmt.executeUpdate();

            if(result > 0) {
                System.out.println("수강신청 성공했습니다.");
                userSelectLec(user.getId());
            }else {
                System.out.println("수강신청 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
            close(con);
        }
    }

    // 카드결제
    public void payCardMember(LectureDTO lecture) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        String query = prop.getProperty("insertLecture");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getId());
            pstmt.setInt(2, lecture.getLecCode());

            useCardPay(lecture);
            pay();

            result = pstmt.executeUpdate();

            if(result > 0) {
                System.out.println("-------------------------------------------------");
                System.out.println("\n수강신청 성공했습니다.");
                userSelectLec(user.getId());
            }else {
                System.out.println("-------------------------------------------------");
                System.out.println("수강신청 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
            close(con);
        }
    }

    public void usePayPoint(LectureDTO lecture) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("userLecture");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getId());

            rset = pstmt.executeQuery();

            System.out.println("-------------------------------------------------");
            System.out.println("CORAFT 규정에 따라 포인트차감 금액을 제외한 \n결제금액의 5%가 포인트로 적립됩니다.");
            System.out.println("적립된 포인트는 다음 수강신청 시 사용하실 수 있습니다.");
            System.out.println("-------------------------------------------------");

            while(rset.next()) {
                System.out.println(rset.getString("MEM_NAME") + "님의 결제금액은 " + rset.getInt("LEC_PRICE") + "원 입니다.");
                System.out.println("보유하신 포인트는 " + rset.getInt("MEM_POINT") + "포인트 입니다.");
                System.out.println("-------------------------------------------------");

                System.out.print("사용하실 포인트를 입력해 주세요 : ");
                int usePoint = sc.nextInt();

                int payNewPrice = rset.getInt("LEC_PRICE") - usePoint;
                if (payNewPrice > 0) {
                    int minusPoint = rset.getInt("MEM_POINT") - usePoint;
                    System.out.println(usePoint + "포인트 사용하여 " + minusPoint + "포인트 남았습니다.");
                    System.out.println("-------------------------------------------------");
                    System.out.println(usePoint + "포인트를 차감한 " + payNewPrice + "원은 자동 카드결제됩니다.");
                    int getPoint = (int) (payNewPrice * 0.05);
                    int upPoint = minusPoint + getPoint;
                    pay();
                    memcont.updatePoint(upPoint);
                    break;
                } else if (payNewPrice < 0) {
                    System.out.println("포인트 사용 금액이 결제할 금액보다 많습니다. \n포인트를 다시 입력해 주세요"); ;
                    usePayPoint(lecture);
                } else {
                    System.out.println(usePoint + "포인트를 사용하여 전액 포인트 결제 되었습니다.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(pstmt);
            close(con);
        }
    }

    public void useCardPay(LectureDTO lecture) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("userLecture");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getId());

            rset = pstmt.executeQuery();

            if(rset.next()) {
                int getPoint = (int) (rset.getInt("LEC_PRICE") * 0.05);
                System.out.println(rset.getString("MEM_NAME") + "님의 결제금액은 " + rset.getInt("LEC_PRICE") + "원 입니다.");
                System.out.println("CORAFT 규정에 따라 결제금액의 5%인 " + getPoint + "포인트 적립됩니다.");
                System.out.println("적립된 포인트는 다음 수강신청 시 사용하실 수 있습니다.");
                int upPoint = getPoint + rset.getInt("MEM_POINT");
                memcont.updatePoint(upPoint);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(pstmt);
            close(con);
        }
    }

    public void pay() {
        System.out.println("-------------------------------------------------");
        System.out.println("카드결제가 진행됩니다.");
        System.out.println(". . .");
        System.out.println("카드 결제가 완료되었습니다. ");
    }

    public void userSelectLec(String id) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("showUserLecture");
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);

            rset = pstmt.executeQuery();

            System.out.println("\n[ 신청 내역 ]");
            while(rset.next()) {
                if(rset.getString("LEC_CODE").isEmpty()) {
                    System.out.println("수강 신청 내역이 존재하지 않습니다.");
                }else {
                    System.out.println("강의 이름 : " + rset.getString("LEC_NAME")
                                        + " || 날짜 : " + rset.getString("LEC_DATE")
                                        + " || 시간 : " + rset.getString("LEC_TIME")
                                        + " || 가격 : " + rset.getInt("LEC_PRICE") + "원");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(pstmt);
            close(con);
        }
    }
}

