package cn.ulane.emp.entity;

import java.io.Serializable;
import java.util.Date;

public class Wage implements Serializable {
	private Integer id;
	private String name;
	private Double wage;
	private Integer wagetype;
	private Double basewage;
	private Double bonus;
	private Double deductwage;
	private Date grantdate;
	
	public Wage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Wage(Integer id, String name, Double wage, Integer watetype, Double basewage, Double bonus,
			Double deductwage, Date grantdate) {
		super();
		this.id = id;
		this.name = name;
		this.wage = wage;
		this.wagetype = watetype;
		this.basewage = basewage;
		this.bonus = bonus;
		this.deductwage = deductwage;
		this.grantdate = grantdate;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getWage() {
		return wage;
	}
	public void setWage(Double wage) {
		this.wage = wage;
	}
	public Integer getWagetype() {
		return wagetype;
	}
	public void setWagetype(Integer watetype) {
		this.wagetype = watetype;
	}
	public Double getBasewage() {
		return basewage;
	}
	public void setBasewage(Double basewage) {
		this.basewage = basewage;
	}
	public Double getBonus() {
		return bonus;
	}
	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	public Double getDeductwage() {
		return deductwage;
	}
	public void setDeductwage(Double deductwage) {
		this.deductwage = deductwage;
	}
	public Date getGrantdate() {
		return grantdate;
	}
	public void setGrantdate(Date grantdate) {
		this.grantdate = grantdate;
	}
	
	@Override
	public String toString() {
		return "Wage [id=" + id + ", name=" + name + ", wage=" + wage + ", wagetype=" + wagetype + ", basewage="
				+ basewage + ", bonus=" + bonus + ", deductwage=" + deductwage
				+ ", grantdate=" + grantdate + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Wage other = (Wage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
