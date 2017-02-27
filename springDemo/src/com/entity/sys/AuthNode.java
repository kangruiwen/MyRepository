package com.entity.sys;

import java.io.Serializable;
/**
 * 功能：目录节点
 * @user krw
 * 2017年2月26日
 */
public class AuthNode implements Serializable{

	private static final long serialVersionUID = 2751077469835566131L;
	
	private String id; 
	private boolean leaf;
	private String name;
	private String parentId;
	private int order;
	private String view;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	
}
