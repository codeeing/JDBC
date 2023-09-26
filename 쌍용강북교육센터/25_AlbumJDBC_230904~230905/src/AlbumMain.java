// 230904-230905
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class AlbumMain {
	Scanner sc = new Scanner(System.in);
	AlbumDao dao = new AlbumDao();
	
	BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in));
	
	public AlbumMain() throws IOException {
		menu();
	}
	
	public void menu() throws IOException {
		int menuNum = 0;
		while (true) {
			System.out.println("======메뉴 선택하기======");
			System.out.println("1. 전체 정보 조회");
			System.out.println("2. 조건 조회");
			System.out.println("3. 가격 범위 조건 검색");
			System.out.println("4. 정보 추가");
			System.out.println("5. 정보 수정");
			System.out.println("6. 정보 삭제");
			System.out.println("7. 정렬");
			System.out.println("8. 그룹화");
			System.out.println("9. 프로그램 종료");
			System.out.print(">> 메뉴 번호 입력 : ");
			menuNum = sc.nextInt();
			switch(menuNum) {
				case 1: 
					ArrayList<AlbumBean> list = dao.getAllAlbums();
					show(list);
					break;
				case 2: 
					getAlbumsBy();
					break;
				case 3: 
					getAlbumsByPrice();
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
					align();
					break;
				case 8: 
					groupAvg();
					break;
				case 9: 
					System.out.println("프로그램을 종료합니다.");
					System.exit(0);
					break;
				default: System.out.println("1 ~ 9 사이의 숫자를 입력하시오.\n");
			}
		}
	}
	
	public void groupAvg() throws IOException {
		System.out.print("그룹화할 회사 입력 : ");
		String company = br.readLine();
		
		AlbumBean bean = dao.groupAvg(company);
		if(bean == null) {
			System.out.println("없는 회사명입니다.\n");
		} else {
			System.out.println("회사\t회사별 평균");
			System.out.println(bean.getCompany()+"\t"+bean.getPrice()+"\n");
		}
	}

	public void align() {
		System.out.println("정렬할 항목을 선택하세요.");
		System.out.print("번호:1\t\t노래제목:2\t가수명:3\t번호입력>> ");
		int num = sc.nextInt();
		String column = null;
		String align = null;
		
		switch(num) {
			case 1:
				column = "num";
				break;
			case 2:
				column = "song";
				break;
			case 3:
				column = "singer";
				break;
			default:
				System.out.println("1 ~ 3 사이의 숫자를 입력하시오.");
				return;
		}
		
		System.out.println("정렬방법 선택하세요.");
		System.out.print("오름차순:1\t내림차순:2\t번호입력>> ");
		num = sc.nextInt();
		switch(num) {
		case 1:
			align = "";
			break;
		case 2:
			align = "desc";
			break;
		default: 
			System.out.println("1 ~ 2 사이의 숫자를 입력하시오.");
			return;
		}
		
		ArrayList<AlbumBean> list = dao.align(column, align);
		System.out.println("검색한 항목은 : "+list.size()+"건 입니다.");
		show(list);
		
	}

	public void deleteData() {
		int num;
		while(true) {
			try {
				System.out.print("삭제할 번호 입력 : ");
				num = sc.nextInt();
				break;
			} catch(Exception e) {
				System.out.println("숫자로 입력하세요.");
				sc.next();
			}
		}
		int cnt = dao.deleteData(num);
		if (cnt == 1)
			System.out.println("삭제 성공했습니다.\n");
		else if (cnt == 0)
			System.out.println("조건에 맞는 레코드가 없습니다.\n");
		else
			System.out.println("삭제 실패했습니다.\n");
	}

	public void updateData() throws IOException {
		AlbumBean bean = new AlbumBean();
		int price = 0;
		System.out.print("수정할 번호 입력 : ");
		int num = sc.nextInt();
		System.out.print("노래제목 입력 : ");
		String song = br.readLine();
		System.out.print("가수명 입력 : ");
		String singer = br.readLine();
		System.out.print("회사 입력 : ");
		String company = br.readLine();
		while(true) {
			try {
				
				System.out.print("가격 입력 : ");
				price = sc.nextInt();
				break;
			} catch(Exception e) {
				System.out.println("숫자로 입력하세요.");
				sc.next();
			}
			
		}
		System.out.print("입고일자(yyyy/mm/dd 형식) 입력 : ");
		String pub_day = br.readLine();
		
		bean.setNum(num);
		bean.setSong(song);
		bean.setSinger(singer);
		bean.setCompany(company);
		bean.setPrice(price);
		bean.setPub_day(pub_day);
		
		int cnt = dao.updateData(bean);
		if (cnt == 1)
			System.out.println("수정 성공했습니다.\n");
		else if (cnt == 0)
			System.out.println("조건에 맞는 레코드가 없습니다.\n");
		else
			System.out.println("수정 실패했습니다.\n");
	}

	public void insertData() throws IOException {
		AlbumBean bean = new AlbumBean();
		int price = 0;
		System.out.println("번호는 시퀀스로 입력됩니다(생략)");
		System.out.print("노래제목 입력 : ");
		String song = br.readLine();
		System.out.print("가수명 입력 : ");
		String singer = br.readLine();
		System.out.print("회사 입력 : ");
		String company = br.readLine();
		while(true) {
			try {
				
				System.out.print("가격 입력 : ");
				price = sc.nextInt();
				break;
			} catch(Exception e) {
				System.out.println("숫자로 입력하세요.");
				sc.next();
			}
			
		}
		System.out.print("입고일자(yyyy/mm/dd 형식) 입력 : ");
		String pub_day = br.readLine();
		
		bean.setSong(song);
		bean.setSinger(singer);
		bean.setCompany(company);
		bean.setPrice(price);
		bean.setPub_day(pub_day);
		
		int cnt = dao.insertData(bean);
		if (cnt == 1)
			System.out.println("삽입 성공했습니다.\n");
		else
			System.out.println("삽입 실패했습니다.\n");
	}

	public void getAlbumsByPrice() {
		System.out.print("시작등수 입력 : ");
		int start = sc.nextInt();
		System.out.print("끝등수 입력 : ");
		int end = sc.nextInt();
		ArrayList<AlbumBean> list = dao.getAlbumsByPrice(start, end);
		show(list);
	}

	public void getAlbumsBy() {
		System.out.println("제목검색:1\t가수검색:2\t회사검색:3");
		System.out.print("검색할 항목을 선택 : ");
		int num = sc.nextInt();
		String column = null;
		switch(num) {
			case 1:
				System.out.print("검색할 노래제목 입력 : ");
				column = "song";
				break;
			case 2:
				System.out.print("검색할 가수 입력 : ");
				column = "singer";
				break;
			case 3:
				System.out.print("검색할 회사 입력 : ");
				column = "company";
				break;
			default:
				System.out.println("1 ~ 3 사이의 숫자를 입력하시오.\n");
				return;
		}
		String word = sc.next();
		ArrayList<AlbumBean> list = dao.getAlbumsBy(column, word);
		if(list.size() == 0)
			System.out.println("검색한 단어의 레코드가 없습니다.\n");
		else
			show(list);
	}

	public void show(ArrayList<AlbumBean> list) {
		System.out.println("번호\t노래제목\t가수\t소속사\t가격\t발매일");
		for(AlbumBean bean : list) {
			System.out.println(bean.getNum()+"\t"+
								bean.getSong()+"\t"+
								bean.getSinger()+"\t"+
								bean.getCompany()+"\t"+
								bean.getPrice()+"\t"+
								bean.getPub_day()
								);
		}
		System.out.println();
	}

	public static void main(String[] args) throws IOException {
		new AlbumMain();
	}

}
