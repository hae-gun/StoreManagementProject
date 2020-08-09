package main;

import service.HomeService;

public class MartEx {
	public static void main(String[] args) {
		
		HomeService service = HomeService.getInstance();
		service.launch();
		
	}
}
