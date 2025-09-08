package io.swagger.v3.oas.annotations.respones;

public class AppResponse {
	private String message;
	private boolean status;
	
	
	public AppResponse() {
		
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	

}
