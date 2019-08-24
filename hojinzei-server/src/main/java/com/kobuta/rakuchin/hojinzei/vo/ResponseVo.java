package com.kobuta.rakuchin.hojinzei.vo;

import java.io.Serializable;

public class ResponseVo<T>  implements Serializable {

	private static final long serialVersionUID = -6926002272125453733L;

	private int code;
	private String error;
    private T data;
    private String message;
	private long timestamp;


    public ResponseVo(int status, T data, String message) {
        this.code = status;
        this.data = data;
        this.message = message;
        this.timestamp =  System.currentTimeMillis();
		this.error = "";
    }

	public ResponseVo(int status, String error, String message) {
		this.code = status;
		this.error = error;
		this.message = message;
		this.timestamp =  System.currentTimeMillis();
	}



	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getStatus() {
		return code;
	}

	public void setStatus(int status) {
		this.code = status;
	}
}
