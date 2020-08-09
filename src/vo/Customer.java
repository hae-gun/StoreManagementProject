package vo;

import java.io.Serializable;
import java.util.List;

public class Customer extends User implements Serializable {

	private static final long serialVersionUID = 2L;
	private final String ROLL = "CUSTOMER";
	
	private List<String> buyList;
	
	public Customer(String id, String pw) {
		super(id, pw);
	}

	public String getROLL() {
		return ROLL;
	}
	
	

	public List<String> getBuyList() {
		return buyList;
	}

	public void setBuyList(List<String> buyList) {
		this.buyList = buyList;
	}
	
	public void putList(String list) {
		buyList.add(list);
	}

	@Override
	public String toString() {
		return "ID: " + super.getId() + " / Roll: " + ROLL;
	}

}
