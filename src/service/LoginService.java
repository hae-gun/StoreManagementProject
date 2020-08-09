package service;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import dao.UserDao;
import vo.Customer;
import vo.Product;
import vo.Seller;
import vo.User;

public class LoginService implements marketService {

	UserDao userdao = new UserDao();
	Scanner sc;

	public LoginService() {
		loadData();
	}

	public LoginService(Scanner scan) {
		sc = scan;
	}

	public User startLogin() {
		
//		System.out.println(userdao.getSet());

		System.out.println("=========== Log in ===========");
		System.out.println("ID: ");
		String id = sc.nextLine();
		System.out.println("PW: ");
		String pw = sc.nextLine();
		User user = null;
		
		if ((user = userdao.login(id, pw)) != null) {
			System.out.println("======Login Success======");
			System.out.println(user);
		} else {
			System.out.println("=======Login Failed=======");
			System.out.println(user);
		}
		
		return user;
	}

	public void registerUser() {
		System.out.println("=========== Register ===========");
		System.out.println("=====판매자등록(1) 구매자등록(2)=====");
		try {
			int code = Integer.parseInt(sc.nextLine());

			System.out.println("ID: ");
			String id = sc.nextLine();
			System.out.println("PW: ");
			String pw = sc.nextLine();
			User user = new User(id, pw);

			Iterator<User> iterator = userdao.getSet().iterator();
			boolean flag = true;

			while (iterator.hasNext()) {
				User u = iterator.next();
				if (id.equals(u.getId())) {
					System.out.println("이미 존재하는 계정");
					flag = !flag;
				}
			}
			User registUser = null;
			if (flag) {
				switch (code) {
				case 1:
					registUser = new Seller(id, pw);
					userdao.getSet().add(registUser);
					break;
				case 2:
					registUser = new Customer(id, pw);
					userdao.getSet().add(registUser);
					break;
				default:
					break;
				}

				System.out.println("회원가입 완료!!");
			}
		} catch (InputMismatchException e) {
			System.out.println("잘못된 입력. 회원가입 실패");
		}
//		if() {
//			
//		}else {
//			System.out.println("=======Login Failed=======");
//		}

	}

	@Override
	public void loadData() {
		userdao.loadUserList();
	}

	@Override
	public void saveData() {
		userdao.saveUserList();
	}

	public void getPurchaseList(User user) {
		userdao.parchaseList(user);
	}

	public void getCustomers() {
		Iterator<User> iterator = userdao.getSet().iterator();
		System.out.println("\n\n========= Customer List =========");
		while (iterator.hasNext()) {
				User temp = iterator.next();
				if (temp instanceof Customer) {
					Customer cus = (Customer) temp;
					if(cus.getBuyList()!=null) {
						System.out.println(
								temp.getId() + " / Point: " + temp.getPoint() 
								+ " / 구매횟수: " + cus.getBuyList().size());
						}else {
							System.out.println(temp.getId() + " / Point: " + temp.getPoint() +  " / 구매횟수: 0" );
						}
				}
		}
		System.out.println("=================================\n\n");

	}

}
