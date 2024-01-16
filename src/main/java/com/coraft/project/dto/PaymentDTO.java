package com.coraft.project.dto;

public class PaymentDTO {

    private int payCard;            //카드결제

    public PaymentDTO() {}

    public PaymentDTO(int payCard) {
        this.payCard = payCard;
    }

    public void setPayCard(int payCard) {
        this.payCard = payCard;
    }

    public int getPayCard() {
        return payCard;
    }

}
