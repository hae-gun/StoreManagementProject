package dao;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import vo.Customer;
import vo.Product;
import vo.ProductType;
import vo.User;

public class ProductDao {

	private HashMap<String, Product> map;
	private HashMap<String, String> productList;

	@SuppressWarnings("unchecked")
	public void loadProductList() {

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/dat/products.dat"))) {
			while (true) {
				try {
					map = (HashMap<String, Product>) ois.readObject();
					productList = (HashMap<String, String>) ois.readObject();
				} catch (EOFException e) {
//					System.out.println("물품정보 읽기 완료");
					break;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}

//			System.out.println(map);
//			System.out.println(productList);

		} catch (FileNotFoundException e) {
			System.out.println("저장된 데이터 없음.");
		} catch (IOException e) {
			System.out.println("읽어오기 실패.");
		}

		if (map == null) {
			Product temp = new Product("000001", ProductType.MACHINE, "Laptop", 800000, 10);
			map = new HashMap<String, Product>();
			map.put(temp.getNsn(), temp);
		}

		if (productList == null) {
			productList = new HashMap<String, String>();
			for (Product p : map.values()) {
				productList.put(p.getName(), p.getNsn());
			}
		}

	}

	public void saveProductList() {

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/dat/products.dat"))) {
			oos.writeObject(map);
			oos.writeObject(productList);
			oos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Product> getMap() {
		return map;
	}

	public void addProducts(Product product) {
		map.put(product.getNsn(), product);
		if (productList == null) {
			productList = new HashMap<String, String>();
		}
		productList.put(product.getName(), product.getNsn());
	}

	public void productList() {
		int i = 0;
		System.out.println("============================ 물건 리스트 ===========================");
		for (Product p : map.values()) {
			System.out.println(++i + ". NSN: "+p.getNsn()+"---"+ p);
		}
		System.out.println("=================================================================");
	}

	public Product findProduct(String name) {
		Product product = null;
		
		for(Product m : map.values()) {
			if(m.getName().toLowerCase().equals(name.toLowerCase())) {
				product = m;
			}
		}
		return product;
	}

	public void updateProduct(Product product, int amount, User user) {

		if (map.containsKey(product.getNsn())) {

			int totalPrice = amount * product.getPrice();

			if ((product.getAmount() >= amount) && (user.getPoint() >= totalPrice)) {

				user.setPoint(user.getPoint() - totalPrice);

				product.setAmount(product.getAmount() - amount);

				map.put(product.getNsn(), product);
				Customer custom = (Customer) user;
				addPurchaseList(custom, product.getName(), amount);

			} else {
				System.out.println("구매할수 없습니다.");
			}
		} else {
			System.out.println("존재하지 않는 상품입니다.");
		}

	}

	public void addPurchaseList(Customer user, String productName, int purchaseAmount) {

		Date today = new Date();
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String purchaseList = sDate.format(today) + " / " + productName + "--구매수량: " + purchaseAmount;

		if (user.getBuyList() == null) {
			user.setBuyList(new ArrayList<String>());
		}

		user.putList(purchaseList);

	}

}
