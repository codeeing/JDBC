import java.util.ArrayList;
import java.util.Scanner;

public class BookMain {
	public static Scanner sc = new Scanner(System.in);
	BookDao dao = new BookDao(); // 드라이버 로드, 접속

	public BookMain() {
		menu();
	}

	public void menu() {
		int menuNum;

		while (true) {
			System.out.println("======메뉴 선택하기======");
			System.out.println("1. 전체 정보 조회");
			System.out.println("2. 조건 조회");
			System.out.println("3. 정보 수정");
			System.out.println("4. 정보 삭제");
			System.out.println("5. 정보 추가");
			System.out.println("6. 프로그램 종료");
			System.out.print(">> 메뉴 번호 입력 : ");
			menuNum = sc.nextInt();

			switch (menuNum) {
			case 1:
				ArrayList<BookBean> list = dao.getAllBook();
				show(list);
				break;
			case 2:
				getBookBy();
				break;
			case 3:
				updateData();
				break;
			case 4:
				deleteData();
				break;
			case 5:
				insertData();
				break;
			case 6:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
				break;
			default:
				System.out.println("1 ~ 6사이의 숫자를 입력하시오.");
			}
		}
	}

	public void deleteData() {
		System.out.print("삭제할 책번호 입력 : ");
		int no = sc.nextInt();
		
		int cnt = dao.deleteData(no);
		if(cnt == 1)
			System.out.println("데이터 삭제 성공\n");
		else if(cnt == 0)
			System.out.println("조건에 맞는 레코드 없음\n");
		else
			System.out.println("데이터 삭제 실패\n");
	}

	public void updateData() {
		int price = 0;
		System.out.print("수정할 번호 입력 : ");
		int no = sc.nextInt();
		System.out.print("책제목 입력 : ");
		String title = sc.next();
		System.out.print("저자 입력 : ");
		String author = sc.next();
		System.out.print("출판사 입력 : ");
		String publisher = sc.next();
		while(true) {
			try {
				System.out.print("가격 입력 : ");
				price = sc.nextInt();
				break;
			} catch(Exception e) {
				System.out.println("가격은 숫자로 입력하세요.");
				sc.next();
			}
		}
		System.out.print("출간일(yyyy/mm/dd 형식) 입력 : ");
		String pub_Day = sc.next();
		
		BookBean bean = new BookBean();
		bean.setNo(no);
		bean.setTitle(title);
		bean.setAuthor(author);
		bean.setPublisher(publisher);
		bean.setPrice(price);
		bean.setPub_day(pub_Day);
		
		int cnt = dao.updateData(bean);
		if(cnt == 1)
			System.out.println("데이터 수정 성공\n");
		else if(cnt == 0)
			System.out.println("조건에 맞는 레코드 없음\n");
		else
			System.out.println("데이터 수정 실패\n");
	}

	public void insertData() {
		int price = 0;
		System.out.println("번호는 시퀀스로 입력됩니다(생략)");
		System.out.print("책제목 입력 : ");
		String title = sc.next();
		System.out.print("저자 입력 : ");
		String author = sc.next();
		System.out.print("출판사 입력 : ");
		String publisher = sc.next();
		
		while(true) {
			try {
				System.out.print("가격 입력 : ");
				price = sc.nextInt();
				break;
			} catch(Exception e) {
				System.out.println("가격은 숫자로 입력하세요.");
				sc.next();
			}
		}
		System.out.print("출간일(yyyy/mm/dd 형식) 입력 : ");
		String pub_Day = sc.next();
		
		BookBean bean = new BookBean();
		bean.setTitle(title);
		bean.setAuthor(author);
		bean.setPublisher(publisher);
		bean.setPrice(price);
		bean.setPub_day(pub_Day);
		
		int cnt = dao.insertData(bean);
		if(cnt == 1)
			System.out.println("데이터 삽입 성공\n");
		else
			System.out.println("데이터 삽입 실패\n");
	}

	public void getBookBy() {
		System.out.print("제목:1\t저자:2\t출판사:3\t번호입력>> ");
		int num = sc.nextInt();
		String column = null;

		switch (num) {
		case 1:
			System.out.print("조회할 제목 : ");
			column = "title";
			break;
		case 2:
			System.out.print("조회할 저자 : ");
			column = "author";
			break;
		case 3:
			System.out.print("조회할 출판사 : ");
			column = "publisher";
			break;
		default:
			System.out.println("1 ~ 3사이의 숫자를 입력하시오.");
			return; // 나를 불러준 곳으로 return
		}

		String word = sc.next();
		ArrayList<BookBean> list = dao.getBookBy(column, word);
		if(list.size() == 0){
			System.out.println("해당 검색어는 존재하지 않음");
		} else {
			System.out.println("검색할 항목은 : " + list.size() + "권 입니다.");
			show(list);
		}
	}

	public void show(ArrayList<BookBean> list) {
		System.out.println("번호\t책제목\t저자\t출판사\t가격\t출간일");
		for (BookBean bean : list) {
			System.out.println(bean.getNo() + "\t" + bean.getTitle() + "\t" + bean.getAuthor() + "\t"
					+ bean.getPublisher() + "\t" + bean.getPrice() + "\t" + bean.getPub_day());
		}
		System.out.println();
	}

	public static void main(String[] args) {
		new BookMain();
	}

}
