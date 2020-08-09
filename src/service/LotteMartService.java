package service;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import dao.ProductDao;
import vo.Product;
import vo.ProductType;
import vo.Seller;
import vo.User;

public class LotteMartService implements marketService {

	ProductDao productDao = new ProductDao();
	Scanner scan;

	public LotteMartService(Scanner scan) {
		this.scan = scan;
	}

	public void registerProduct(Seller seller) {

		System.out.println("<<<물건등록>>>");
		try {

			System.out.println("nsn>>>>");
			String nsn = scan.nextLine();
			System.out.println("type(0:FOOD 1:MACHINE 2:STUFF)>>>");
			int code = Integer.parseInt(scan.nextLine());
			ProductType type = typeCheck(code);
			System.out.println("상품명>>>");
			String name = scan.nextLine();
			System.out.println("가격>>>");
			int price = Integer.parseInt(scan.nextLine());
			System.out.println("수량>>>");
			int amount = Integer.parseInt(scan.nextLine());

			Product product = new Product(nsn, type, name, price, amount);

			productDao.addProducts(product);

		} catch (Exception e) {
			System.out.println("잘못된 입력");
			return;
		}

	}

	public void updateProduct(Seller seller) {

	}

	public void showProductList() {
		productDao.productList();
	}

	public void loadData() {
		productDao.loadProductList();
	}

	public void saveData() {
		productDao.saveProductList();
	}

	public ProductType typeCheck(int code) {

		ProductType type;

		switch (code) {
		case 0:
			type = ProductType.FOOD;
			break;
		case 1:
			type = ProductType.MACHINE;
			break;
		case 2:
			type = ProductType.STUFF;
			break;
		default:
			type = null;
			break;
		}

		return type;
	}

	public void buyProduct(User user) {
		showProductList();
		System.out.print("구매할 물건 이름 입력: ");
		String name = scan.nextLine();
		Product product = productDao.findProduct(name);
		if (product.getAmount() > 0) {
			System.out.printf("구매하려는 물품: %s, 가격: %d, 남은수량: %d\n", product.getName(), product.getPrice(),
					product.getAmount());
			System.out.print("구매 갯수: ");
			try {
				int amount = Integer.parseInt(scan.nextLine());

				productDao.updateProduct(product, amount, user);

			} catch (InputMismatchException e) {
				System.out.println("입력오류");
				return;
			}
		} else {
			System.out.println("구매할수 없습니다.");
		}

	}

	public void getUserInfo(User user) {
		System.out.println(user + " / myPoint: " + user.getPoint());
	}

	public void depositPoint(User user) {
		System.out.println("충전할 포인트: ");
		System.out.print(">>>");
		int point = Integer.parseInt(scan.nextLine());
		user.setPoint(point);
		System.out.println("충전완료");
	}

	public void autoOrder() {
		Map<String, Product> m = productDao.getMap();
		System.out.println("======================  구매 필요 물품  (재고10개미만)  ===================");
		int count = 0;
		for (Product p : m.values()) {
			if (p.getAmount() < 10) {
				System.out.println(p.getNsn() + "---" + p);
				count++;
			}
		}
		if (count == 0)
			System.out.println("없음");
		else {
			System.out.println("y 입력시 재고충전 (30개)");
			String order = scan.nextLine().toLowerCase();
			if ("y".equals(order)) {
				for (Product p : m.values()) {
					if (p.getAmount() < 10)
						p.setAmount(p.getAmount() + 30);
				}
				System.out.println("주문완료.");
			}
		}

	}

}
