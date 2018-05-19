package com.znsd.ssm.entity;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

public class Employee implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer eId;

	@Pattern(regexp="(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})",message="username必须是2-5")
    private String eName;

    private String gender;

    @Email(message="邮箱格式错误")
    private String email;

    private Integer dId;
    
    //查询员工的时候，部分信息也查出来
    private Department department;		//员工所属部门
    
    

    public Employee() {
		super();
	}

	public Employee(Integer eId, String eName, String gender, String email, Integer dId) {
		super();
		this.eId = eId;
		this.eName = eName;
		this.gender = gender;
		this.email = email;
		this.dId = dId;
	}

	@Override
	public String toString() {
		return "Employee [eId=" + eId + ", eName=" + eName + ", gender=" + gender + ", email=" + email + ", dId=" + dId
				+ ", department=" + department + "]";
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName == null ? null : eName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }
}