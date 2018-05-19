package com.znsd.ssm.entity;

import java.util.HashMap;
import java.util.Map;

import com.znsd.ssm.utils.Constant;

/**
 * 通用返回json的类
 * @author Administrator
 *
 */
public class Message {
	
	private Integer code;		//状态码 100成功，200失败
	private String message;		//提示信息
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public Message add(String key,Object value){
		this.getMap().put(key, value);
		return this;
	}
	
	/**
	 * 处理成功返回的信息
	 * @return
	 */
	public static Message success(){
		Message msg = new Message();
		msg.setCode(Constant.SUCCESS_STATUS);//100
		msg.setMessage("处理成功");
		return msg;
	}
	
	/**
	 * 处理失败返回的信息
	 * @return
	 */
	public static Message fail(){
		Message msg = new Message();
		msg.setCode(Constant.FAIL_STATUS);//200
		msg.setMessage("处理失败");
		return msg;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	@Override
	public String toString() {
		return "Message [code=" + code + ", message=" + message + ", map=" + map + "]";
	}
	
	
	
}
