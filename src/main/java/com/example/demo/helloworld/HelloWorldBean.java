package com.example.demo.helloworld;

public class HelloWorldBean {

	private String msg;

	public HelloWorldBean(String msg) {
		this.msg = msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	@Override
	public String toString() {
		return "HelloWorldBean [msg=" + msg + "]";
	}
	
}
