package com.cache.sbcache.bean;

public class Department {
	
	private Integer id;
	private String departName;
	
	
	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Department(Integer id, String departmentName) {
		super();
		this.id = id;
		this.departName = departmentName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDepartmentName() {
		return departName;
	}
	public void setDepartmentName(String departmentName) {
		this.departName = departmentName;
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentName=" + departName + "]";
	}
	
	
	
	

}
