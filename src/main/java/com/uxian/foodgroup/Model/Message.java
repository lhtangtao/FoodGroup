package com.uxian.foodgroup.Model;

import java.util.UUID;

public class Message {
	private String messageContent = null;
	private UUID foodPostID = null;
	private UUID operatorID = null;
	
	public String getMessageContent() {
		return messageContent;
	}
	
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	public UUID getFoodPostID() {
		return foodPostID;
	}
	
	public void setFoodPostID(UUID foodPostID) {
		this.foodPostID = foodPostID;
	}
	
	public UUID getOperatorID() {
		return operatorID;
	}
	
	public void setOperatorID(UUID operatorID) {
		this.operatorID = operatorID;
	}
	
}
