package com.coraft.project.controller;

import com.coraft.project.dto.MemberDTO;
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
import static com.coraft.project.view.Menu.user;


public class MemberController {
    Scanner sc = new Scanner(System.in);
    Menu menu = new Menu();
    Properties prop = new Properties();


    public MemberController() {
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/coraft/project/mapper/members-query.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(MemberDTO userlogin) {
        Connection con = getConnection();
        String query = prop.getProperty("selectIdPwd");
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        int result = 0;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, userlogin.getId());

            rset = pstmt.executeQuery();

            if (rset.next()) {
                user = new MemberDTO();
                if (rset.getString(1).equals(userlogin.getId())) {
                    if (rset.getString(2).equals(userlogin.getPwd())) {
                        user.setId(rset.getString("MEM_ID"));
                        user.setPwd(rset.getString("MEM_PWD"));
                        user.setName(rset.getString("MEM_NAME"));
                        user.setAge(rset.getInt("MEM_AGE"));
                        user.setGender(rset.getString("MEM_GENDER"));
                        user.setPhone(rset.getString("PHONE"));
                        user.setEmail(rset.getString("EMAIL"));
                        user.setPoint(rset.getInt("MEM_POINT"));
                        result = 1;
                    } else {
                        result = -1;
                    }
                } else {
                    result = -1;
                }
            }
            if (result > 0) {
                System.out.println(user.getId() + "님 로그인에 성공했습니다 :)");
                menu.mainMenu();
            } else {
                System.out.println("로그인에 실패했습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
    }

    public int regist(MemberDTO user) {
        Connection con = getConnection();
        String query = prop.getProperty("insertMember");
        PreparedStatement pstmt = null;
        int result = 0;

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
        } finally {
            close(pstmt);
            close(con);
        }

        return result;
    }

    public void memberInfo(MemberDTO user) {
        Connection con = getConnection();
        String query = prop.getProperty("selectMemberInfo");
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getId());
            System.out.println(user.getId());

            rset = pstmt.executeQuery();

            while (rset.next()) {
                System.out.println("\n= 회원정보 =========================================");
                System.out.println("이름 : " + rset.getString("MEM_NAME"));
                System.out.println("나이 : " + rset.getInt("MEM_AGE") + "세");
                System.out.println("성별 : " + rset.getString("MEM_GENDER"));
                System.out.println("전화번호 : " + rset.getString("PHONE"));
                System.out.println("이메일 : " + rset.getString("EMAIL"));
                System.out.println("포인트 : " + rset.getInt("MEM_POINT"));
                System.out.println("-------------------------------------------------");
            }

            while (true) {
                System.out.print("메인으로 돌아갑니다. (Y / N) ");
                char answer = sc.next().toUpperCase().charAt(0);

                if (answer == 'Y') {
                    menu.mainMenu();

                } else {
                    System.out.println("다시 입력해주세요");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
    }

    public String checkId() {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = prop.getProperty("checkId");
        String id = "";

        /*    try {
                while(true) {
                System.out.print("아이디를 입력하세요 : ");
                id = sc.nextLine();

                pstmt = con.prepareStatement(query);
                pstmt.setString(1, id);

                rset = pstmt.executeQuery();
                if(rset.next()) {
                    if (rset.getString("MEM_ID") != null) {
                        System.out.println("중복된 아이디입니다. 다시 입력해주세요.");
                    } else {
                        System.out.println("사용 가능한 아이디입니다."); break;
                    }
                }
                    if (result > 0) {
                        System.out.println("사용 가능한 아이디입니다.");
                        break;
                    } else {
                        System.out.println("중복된 아이디입니다. 다시 입력해주세요");
                    }
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                close(rset);
                close(pstmt);
                close(con);
            }


        return id;*/


        try {
            System.out.print("아이디를 입력하세요 : ");
            id = sc.nextLine();

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);

            rset = pstmt.executeQuery();
            while (rset.next()) {
                if (rset.getString("MEM_ID").isEmpty()) {
                    System.out.println("사용 가능한 아이디입니다.");
                } else {
                    System.out.println("중복된 아이디입니다. 다시 입력해주세요");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
        return id;

    }

    public void updatePoint(int upPoint) {
        Connection con = getConnection();
        PreparedStatement pstmt  = null;
        int result = 0;
        String query = prop.getProperty("updatePoint");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, upPoint);
            pstmt.setString(2, user.getId());

            result = pstmt.executeUpdate();

            if(result > 0) {
                System.out.println("포인트 업데이트 성공");
            }else {
                System.out.println("포인트 업데이트 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
            close(con);
        }
    }
}
