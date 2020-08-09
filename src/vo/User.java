package vo;

import java.io.Serializable;

public class User implements Serializable{
		
	private String id;
	private String pw;
	private int point=0;
	
	
	public User(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPw() {
		return pw;
	}


	public void setPw(String pw) {
		this.pw = pw;
	}


	public int getPoint() {
		return point;
	}


	public void setPoint(int point) {
		this.point = point;
	}
	
	@Override
	public int hashCode() {
		int code=0;
		
		for(int i=0; i<id.length(); i++) {
			code += id.charAt(i) * (int)Math.pow(10, i);
		}
		// TODO Auto-generated method stub
		return code;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(((User)obj).getId().equals(id) && ((User)obj).getPw().equals(pw))
			return true;
		return false;
	}
	
}
