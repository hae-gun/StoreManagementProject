package service;

import java.util.Scanner;

import vo.Customer;
import vo.Seller;
import vo.User;

public class HomeService implements marketService {

	Scanner scan = new Scanner(System.in);
	LoginService loginService = new LoginService(scan);
	LotteMartService lotteMartService = new LotteMartService(scan);
	private User user = null;
	private static HomeService service = new HomeService();

	private HomeService() {

	}

	public static HomeService getInstance() {
		return service;
	}

	public void launch() {
		loadData();
		System.out.println("안녕하세요 Lotte Mart 입니다.");
		Loop: while (true) {
			System.out.println("====로그인(1) 회원가입(2) 종료(3)====");
			System.out.print(">>>");
			try {
				int menu = Integer.parseInt(scan.nextLine());
				switch (menu) {
				case 1:
					System.out.println("======로그인======");
					while (user == null) {
						user = loginService.startLogin();
					}
					showMenu(user);
					break;
				case 2:
					System.out.println("======회원가입======");
					loginService.registerUser();
					break;
				case 3:
					System.out.println("종료");
					break Loop;
				default:
					System.out.println("1,2,3 중 하나를 입력하세요.");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("1,2,3 중 하나를 입력하세요.");
			}
		}
		exit();
	}

	public void showMenu(User user) {
		if (user instanceof Seller) {
			sellerMenu();
		} else if (user instanceof Customer) {
			CustomerMenu();
		}
	}

	private void CustomerMenu() {

		boolean flag = true;
		while (flag) {
			System.out.println("\n\n===========================================================================");
			System.out.println("===========                    Lotte Mart                      ============");
			System.out.println("===========================================================================");
			System.out.println("=======  1.물건리스트  2.물건구매  3.내정보  4.포인트충전  5.구매내역  6.로그아웃 =======");
			System.out.println("===========================================================================");
			System.out.print(">>>");
			int menu = Integer.parseInt(scan.nextLine());
			switch (menu) {
			case 1:
				lotteMartService.showProductList();
				break;
			case 2:
				lotteMartService.buyProduct(user);
				break;
			case 3:
				lotteMartService.getUserInfo(user);
				break;
			case 4:
				lotteMartService.depositPoint(user);
				break;
			case 5:
				loginService.getPurchaseList(user);
				break;
			case 6:
				flag = !flag;
				user = null;
//				exit();
				break;
			default:
				break;
			}
		}
		saveData();
		System.out.println("Logout 성공");
	}

	private void sellerMenu() {

		boolean flag = true;
		while (flag) {
			System.out.println("\n\n======================================================================");
			System.out.println("=========================  매장 관리 시스템  =============================");
			System.out.println("======================================================================");
			System.out.println("=======  1.재고목록   2.물건등록   3.물건주문   4.회원리스트   5.로그아웃  =======");
			System.out.println("======================================================================");
			System.out.print(">>>");
			int menu = Integer.parseInt(scan.nextLine());
			switch (menu) {
			case 1:
				lotteMartService.showProductList();
				break;
			case 2:
				lotteMartService.registerProduct((Seller) user);
				break;
			case 3:
				lotteMartService.autoOrder();
				break;
			case 4:
				loginService.getCustomers();
				break;
			case 5:
				flag = !flag;
				user = null;
//				exit();
				break;
			default:
				break;
			}
		}
		saveData();
		System.out.println("Logout 성공");
	}

	public void exit() {
		saveData();
		scan.close();
	}

	@Override
	public void loadData() {
		loginService.loadData();
		lotteMartService.loadData();
	}

	@Override
	public void saveData() {
		loginService.saveData();
		lotteMartService.saveData();
	}

}
