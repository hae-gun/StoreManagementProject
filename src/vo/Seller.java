package vo;

import java.io.Serializable;

public class Seller extends  User implements Serializable{

	private static final long serialVersionUID = 1L;
	private final String ROLL = "SELLER";
	
	public Seller(String id, String pw) {
		super(id, pw);
	}




	public String getROLL() {
		return ROLL;
	}
	
	
	@Override
	public String toString() {
		return "ID: "+super.getId()+" / Roll: "+ROLL;
	}
}
