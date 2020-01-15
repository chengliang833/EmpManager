package cn.ulane.emp.entity;

import java.io.Serializable;

public class Department implements Serializable {
	private Integer dtmt_id;
	private String dtmt_name;
	private Integer emp_num;
	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Department(Integer dtmt_id, String dtmt_name, Integer emp_num) {
		super();
		this.dtmt_id = dtmt_id;
		this.dtmt_name = dtmt_name;
		this.emp_num = emp_num;
	}
	public Integer getDtmt_id() {
		return dtmt_id;
	}
	public void setDtmt_id(Integer dtmt_id) {
		this.dtmt_id = dtmt_id;
	}
	public String getDtmt_name() {
		return dtmt_name;
	}
	public void setDtmt_name(String dtmt_name) {
		this.dtmt_name = dtmt_name;
	}
	public Integer getEmp_num() {
		return emp_num;
	}
	public void setEmp_num(Integer emp_num) {
		this.emp_num = emp_num;
	}
	@Override
	public String toString() {
		return "Department [dtmt_id=" + dtmt_id + ", dtmt_name=" + dtmt_name + ", emp_num=" + emp_num + "]";
	}

	
}
