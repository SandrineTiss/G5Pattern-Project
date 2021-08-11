package fr.eni.g5pattern.bo;

public class BusinessException extends Exception {
	private String message;

	public BusinessException(String string) {
		this.message = string;
	}

	public BusinessException() {
	}

	private static final long serialVersionUID = 1L;

}
