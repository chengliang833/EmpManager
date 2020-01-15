package cn.ulane.emp.entity;

import java.io.Serializable;

public class Emp implements Serializable {
	private Integer id;
	private String name;
	private String sex;
	private Integer age;
	private String post;
	private String department;
	private Double wage;
	
	public Emp() {}
	public Emp(int id, String name, String sex, int age, String post, String department, double wage) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.post = post;
		this.department = department;
		this.wage = wage;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public double getWage() {
		return wage;
	}
	public void setWage(double wage) {
		this.wage = wage;
	}
	
	@Override
	public String toString() {
		return "Emp [id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", post=" + post + ", department="
				+ department + ", wage=" + wage + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Emp other = (Emp) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
