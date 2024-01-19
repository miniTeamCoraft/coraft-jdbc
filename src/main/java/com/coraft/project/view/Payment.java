package com.coraft.project.view;

import com.coraft.project.controller.PayController;
import com.coraft.project.dto.LectureDTO;
import com.coraft.project.dto.MemberDTO;

import java.util.Scanner;
import static com.coraft.project.view.Menu.user;

public class Payment {

    private Scanner sc = new Scanner(System.in);
    PayController pc = new PayController();

    public void mainPayment(LectureDTO lecture) {

        System.out.println("\n= 결제진행 =========================================");
        System.out.println("1.카드결제");
        System.out.println("2.포인트 차감");  // 포인트 차감 후 카드결제로 넘어감
        System.out.println("9.결제 취소");   // 클래스 선택으로 넘어감
        System.out.println("-------------------------------------------------");
        System.out.print("결제진행 수단을 선택해 주세요 : ");
        int payProgress = sc.nextInt();
        System.out.println("-------------------------------------------------");

        switch (payProgress) {

            case 1 : pc.payCardMember(lecture); break;
            case 2 :
                System.out.println("포인트를 차감하고 부족한 금액은 카드결제로 진행합니다.");
                System.out.print("결제 진행여부를 선택해 주세요 (Y / N) ");
                char ch = sc.next().toUpperCase().charAt(0);
                if (ch == 'Y') {
                    System.out.println("포인트 결제를 선택하셨습니다.");
                    pc.payBonusMember(lecture);

                } else if (ch == 'N') {
                    System.out.println("\n포인트 결제가 취소되었습니다. 다른 메뉴를 선택해주세요."); break;

                } else {
                    System.out.println("잘못된 메뉴를 선택하셨습니다. 확인 후 재입력해주세요");
                } break;

            case 9 : System.out.println("결제가 취소되었습니다."); break;
            default: System.out.println("잘못된 메뉴를 선택하셨습니다. 확인 후 재입력해주세요"); break;

        }
    }
}
