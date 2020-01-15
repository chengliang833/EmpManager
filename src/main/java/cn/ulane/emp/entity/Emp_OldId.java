package cn.ulane.emp.entity;

public class Emp_OldId extends Emp {
	private int oldId;
	
	public Emp_OldId() {}
	public Emp_OldId(int oldId, int id, String name, String sex, int age, String post, String department, Double wage) {
		super(id, name, sex, age, post, department, wage);
		this.oldId = oldId;
	}
	
	public int getOldId() {
		return oldId;
	}
	public void setOldId(int oldId) {
		this.oldId = oldId;
	}
	
	@Override
	public String toString() {
		return "Emp_OldId [getId()=" + getId() + ", getName()=" + getName() + ", getSex()=" + getSex() + ", getAge()="
				+ getAge() + ", getPost()=" + getPost() + ", getDepartment()=" + getDepartment() + ", getWage()="
				+ getWage() + ", toString()=" + super.toString() + ", hashCode()=" + hashCode() + ", getClass()="
				+ getClass() + "]";
	}
	
}
