package cn.ulane.emp.web;

import java.io.Serializable;

/**
 * 用于封装JSON返回数据的值对象类
 */
public class JsonResult<T> implements Serializable {
	public static final int SUCCESS = 0;
	public static final int ERROR = 1;
	public static final int ADMIN = 0;
	public static final int USER = 1;
	
	private int state;
	private int type;
	private String message;
	private T data;

	public JsonResult() {}
	public JsonResult(Exception e) {
		this(ERROR, e.getMessage(), null);
  	}
	public JsonResult(String errorMessage) {
		this(ERROR, errorMessage, null);
	}
	public JsonResult(T data) {
		this(SUCCESS, "", data);
	}
	public JsonResult(int state, String message) {
		this(state, message, null);
	}
	public JsonResult(int state, String message, T data) {
		this.state = state;
		this.message = message;
		this.data = data;
	}
	public JsonResult(int type, T data) {
		this(SUCCESS, type, "", data);
	}
	public JsonResult(int state, int type, String message, T data) {
		super();
		this.state = state;
		this.type = type;
		this.message = message;
		this.data = data;
	}

	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", type=" + type + ", message=" + message + ", data=" + data + "]";
	}

}
