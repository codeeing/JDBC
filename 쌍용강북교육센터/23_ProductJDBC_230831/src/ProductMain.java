import java.util.ArrayList;
import java.util.Scanner;

public class ProductMain {
	ProductDao dao = new ProductDao();
	Scanner sc = new Scanner(System.in);

	public ProductMain() {
		System.out.println("ProductMain 생성자");
		init();
	}

	public void init() {
		int menu;
		ArrayList<ProductBean> list = null;

		while (true) {
			System.out.println("=== 메뉴 선택하기 ===");
			System.out.println("1. 모든 상품 조회");
			System.out.println("2. 특정 상품 조회(ID를 이용)");
			System.out.println("3. 특정 상품 조회(category를 이용)");
			System.out.println("4. 상품 추가");
			System.out.println("5. 상품 수정");
			System.out.println("6. 상품 삭제");
			System.out.println("7. 프로그램 종료");
			System.out.print("번호 입력>>");
			menu = sc.nextInt();
			System.out.println();

			switch (menu) {
			case 1:
				list = dao.getAllProduct();
				showProducts(list);
				break;
			case 2:
				getProductById();
				break;
			case 3:
				getProductByCategory();
				break;
			case 4:
				insertData();
				break;
			case 5:
				updateData();
				break;
			case 6:
				deleteData();
				break;
			case 7:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
				break;

			}

		}
	}

	public void deleteData() {
		System.out.println("ID 입력:");
		int id = sc.nextInt();
		
		int cnt = dao.deleteData(id);
		if(cnt == 0)
			System.out.println("조건에 맞는 레코드 없음");
		else if(cnt == 1)
			System.out.println("삭제 성공했습니다.");
		else // cnt == -1 => 실행 조차 못했다. sql문에 오타가 있을 경우.
			System.out.println("삭제 실패했습니다.");
	}

	public void updateData() {
		
		System.out.print("ID 입력:");
		int id = sc.nextInt();
		System.out.print("제품명 입력:");
		String name = sc.next();
		System.out.print("재고 입력:");
		int stock = sc.nextInt();
		System.out.print("단가 입력:");
		int price = sc.nextInt();
		System.out.print("카테고리 입력:");
		String category = sc.next();
		System.out.print("입고일 입력:");
		String inputdate = sc.next();

		ProductBean pb = new ProductBean();
		pb.setId(id);
		pb.setName(name);
		pb.setStock(stock);
		pb.setPrice(price);
		pb.setCategory(category);
		pb.setInputdate(inputdate);
		int cnt = dao.updateData(pb);
		if(cnt == 0)
			System.out.println("조건에 맞는 레코드 없음");
		else if(cnt == 1)
			System.out.println("수정 성공했습니다.");
		else
			System.out.println("수정 실패했습니다.");
	}

	public void insertData() {
		System.out.println("id는 시퀀스로 자동 입력");
		System.out.print("제품명 입력:");
		String name = sc.next();
		System.out.print("재고 입력:");
		int stock = sc.nextInt();
		System.out.print("단가 입력:");
		int price = sc.nextInt();
		System.out.print("카테고리 입력:");
		String category = sc.next();
		System.out.print("입고일 입력:");
		String inputdate = sc.next();

		// 이번엔 Main 클래스에서 Bean 클래스를 사용. 반드시 Dao 클래스에서 Bean 클래스를 사용하는게 아님.
		// Bean 클래스는 여러 데이터를 묶기 위한 용도.
		ProductBean pb = new ProductBean();
		pb.setName(name);
		pb.setStock(stock);
		pb.setPrice(price);
		pb.setCategory(category);
		pb.setInputdate(inputdate);
		int cnt = dao.insertData(pb);
		if(cnt == -1)
			System.out.println("삽입된 데이터가 없습니다.");
		else
			System.out.println("삽입 성공했습니다.");
	}

	public void getProductByCategory() {
		System.out.print("찾는 카테고리 입력:");
		String category = sc.next();
		ArrayList<ProductBean> list = dao.getProductByCategory(category);

		showProducts(list);
	}

	public void getProductById() {
		System.out.print("찾는 아이디 입력:");
		int id = sc.nextInt();
		ProductBean pb = dao.getProductById(id);
		if (pb != null) {
			System.out.println("번호\t제품명\t재고\t단가\t카테고리\t입고일");
			System.out.println(pb.getId() + "\t" + pb.getName() + "\t" + pb.getStock() + "\t" + pb.getPrice() + "\t"
					+ pb.getCategory() + "\t" + pb.getInputdate() + "\n");
		} else {
			System.out.println("해당 아이디를 찾을 수 없습니다.\n");
		}
	}

	public void showProducts(ArrayList<ProductBean> list) {
		if (list.size() > 0) {
			System.out.println("번호\t제품명\t재고\t단가\t카테고리\t입고일");
			for (ProductBean pb : list) {
				System.out.println(pb.getId() + "\t" + pb.getName() + "\t" + pb.getStock() + "\t" + pb.getPrice() + "\t"
						+ pb.getCategory() + "\t" + pb.getInputdate());
			}
			System.out.println();
		} else {
			System.out.println("없는 카테고리 입니다.\n");
		}

	}

	public static void main(String[] args) {
		new ProductMain();
	}

}
