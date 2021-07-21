package dao;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import vo.Customer;
import vo.Seller;
import vo.User;

public class UserDao {

	private Set<User> set = new HashSet<>();

	public void saveUserList() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/dat/users.dat"))) {
			oos.writeObject(set);
			oos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void loadUserList() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/dat/users.dat"))) {

			while (true) {
				try {
					set = (Set<User>) ois.readObject();
//					User user = (User) ois.readObject();
//					set.add(user);
				} catch (EOFException e) {
//					System.out.println("유저정보 읽기 완료");
					break;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("저장된 데이터 없음.");
		} catch (IOException e) {
			System.out.println("읽어오기 실패.");
		}
	}

	public void createUser(User user) {
		set.add(user);
	}

	public Set<User> getSet() {
		return set;
	}

	public User login(String id, String pw) {
		User result = null;
		Iterator<User> it = set.iterator();
		if (set.contains(new User(id, pw))) {
			result = getUser(id, it);
			if (result != null) return result;
		}
		return result;
	}

	private User getUser(String id, Iterator<User> it) {
		while (it.hasNext()) {
			User temp = (User) it.next();
			User temp1 = idValidation(id, temp);
			if (temp1 != null) return temp1;
		}
		return null;
	}

	private User idValidation(String id, User temp) {
		if (temp.getId().equals(id)) {
			return checkUserType(temp);
		}
		return null;
	}

	private User checkUserType(User temp) {
		if (temp instanceof Seller) {
			return (Seller) temp;
		} else if (temp instanceof Customer) {
			return (Customer) temp;
		}
		return null;
	}

	public void purchaseList(User user) {

		Customer custom = (Customer) user;
		if (custom.getBuyList() == null)
			custom.setBuyList(new ArrayList<String>());

		if (custom.getBuyList().isEmpty()) {
			System.out.println("구매내역이 없습니다.");
		} else {
			System.out.println("구매내역");
			for (String p : custom.getBuyList()) {
				System.out.println(p);
			}
		}
	}

}
