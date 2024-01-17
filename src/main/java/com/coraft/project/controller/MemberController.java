package com.coraft.project.controller;

import com.coraft.project.dto.MemberDTO;
import com.coraft.project.view.Login;
import com.coraft.project.view.Menu;

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

public class MemberController {

    Scanner sc = new Scanner(System.in);
    Menu menu = new Menu();

    /* jdbc 연결 */
    Connection con = getConnection();
    int result = 0;
    Properties prop = new Properties();
    /* */

    public MemberController() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/coraft/project/mapper/members-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(MemberDTO user) {
        String query = prop.getProperty("selectIdPwd");
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getId());
//            pstmt.setString(2, member.getPwd());

            rset = pstmt.executeQuery();

           if(rset.next()) {
               if(rset.getString(1).equals(user.getId())) {
                   menu.mainMenu(user);
                   result = 1;
               }else {
                   result = -1;
               }

           }

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rset);
            close(pstmt);
            close(con);
        }

        menu.mainMenu(user);
       /* MemberDTO member = FindById(memberDTO.getId());
        if(member == null){
            System.out.println("등록되지 않은 ID입니다.");
        }else if(member.getPwd().equals(memberDTO.getPwd())) {
            System.out.println(member.getName() + "님 로그인 확인되었습니다.");
            Menu menu = new Menu();
            menu.mainMenu(member);
        }else {
            System.out.println("비밀번호를 틀렸습니다.");
        }*/
    }

    private MemberDTO FindById(String id) {
        for(MemberDTO memberDTO : Login.members) {
            if(memberDTO.getId().equals(id)) {
                return memberDTO;
            }
        }
        return null;
    }

    public int regist(MemberDTO user) {
        String query = prop.getProperty("insertMember");
        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getPwd());
            pstmt.setString(3, user.getName());
            pstmt.setInt(4, user.getAge());
            pstmt.setString(5, user.getGender());
            pstmt.setString(6, user.getPhone());
            pstmt.setString(7, user.getEmail());
            pstmt.setInt(8, user.getPoint());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
            close(con);
        }

        System.out.println(user.toString());

        return result;
    }

    public void memberInfo(MemberDTO user) {
        String query = prop.getProperty("selectMemberInfo");
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getId());

            rset = pstmt.executeQuery();

            while(rset.next()) {
                System.out.println("\n= 회원정보 =========================================");
                System.out.println("이름 : " + rset.getString("MEM_NAME"));
                System.out.println("나이 : " + rset.getInt("MEM_AGE") + "세");
                System.out.println("성별 : " + rset.getString("MEM_GENDER"));
                System.out.println("전화번호 : " + rset.getString("PHONE"));
                System.out.println("이메일 : " + rset.getString("EMAIL"));
                System.out.println("포인트 : " + rset.getInt("MEM_POINT"));
                System.out.println("-------------------------------------------------");
            }




            while(true) {
                System.out.print("메인으로 돌아갑니다. (Y / N) ");
                char answer = sc.next().toUpperCase().charAt(0);

                if(answer == 'Y') {
                    menu.mainMenu(user);
                }else if(answer == 'N') {
                    memberInfo(user);
                }else {
                    System.out.println("다시 입력해주세요");
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

    public String checkId() {
        System.out.print("아이디를 입력하세요 : ");
        String id = sc.nextLine();

        for(int i = 0; i < Login.members.size(); i++) {
            if(Login.members.get(i).getId().equals(id)) {
                System.out.println("중복된 아이디 입니다. 다시 입력해주세요.");
                System.out.println("-------------------------------------------------");
                return checkId();
            }
        }

        return id;
    }
}
