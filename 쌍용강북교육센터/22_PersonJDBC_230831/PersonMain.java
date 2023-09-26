import java.util.ArrayList;
import java.util.Scanner;

public class PersonMain { // 콘솔 작업을 하는 메인 클래스
	
	PersonDao dao = new PersonDao();
	public static Scanner sc = new Scanner(System.in);
	
	public PersonMain() {
		System.out.println("PersonMain() 생성자");
		init();
	}
	
	public void init() {
		System.out.println("init()");
		int menu;
		ArrayList<PersonBean> lists = null;
		while(true) {
			System.out.println("\n=== 메뉴 선택하기 ===");
			System.out.println("1. 전체 정보 조회");
			System.out.println("2. 성별로 조회");
			System.out.println("3. 정보 수정");
			System.out.println("4. 정보 삭제");
			System.out.println("5. 정보 추가");
			System.out.println("6. 프로그램 종료");
			System.out.print(">> 메뉴 번호 입력 : ");
			menu = sc.nextInt();
			
			switch(menu) {
				case 1: 
					lists = dao.getAllPerson(); // 콘솔창에서 할 작업 없음. 바로 dao 객체를 통한 메서드 호출.
					System.out.println("번호\t이름\t나이\t성별\t생일\t");
					for(int i=0;i<lists.size();++i) {
						PersonBean pb = lists.get(i);
						System.out.println(pb.getNum()+"\t"+
											pb.getName()+"\t"+
											pb.getAge()+"\t"+
											pb.getGender()+"\t"+
											pb.getBirth());
					}
					break;
				case 2: 
					lists = getPersonByGender(); // 아직 콘솔창에서 할 작업이 있기때문에, dao 객체를 통해 호출하지 않은것.
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
					System.out.println("1 ~ 6의 번호만 입력 가능\n");
			}
		}
	}
	
	public ArrayList<PersonBean> getPersonByGender() {
		System.out.print("찾으려는 성별 입력 : ");
		String gender = sc.next();
		PersonBean pb = null;
		
		ArrayList<PersonBean> lists = dao.getPersonByGender(gender);
		System.out.println("번호\t이름\t나이\t성별\t생일\t");
		for(int i=0;i<lists.size();++i) {
			pb = lists.get(i);
			System.out.println(pb.getNum()+"\t"+
								pb.getName()+"\t"+
								pb.getAge()+"\t"+
								pb.getGender()+"\t"+
								pb.getBirth());
		}
		return lists;
	}

	public void updateData() {
		System.out.print("수정할 번호 입력: ");
		int num = sc.nextInt();
		
		System.out.print("수정할 이름 입력: ");
		String name = sc.next();
		
		System.out.print("수정할 나이 입력: ");
		int age = sc.nextInt();
		
		System.out.print("수정할 성별 입력: ");
		String gender = sc.next();
		
		System.out.print("수정할 생년월일 입력: ");
		String birth = sc.next();
		
		dao.updateData(num, name, age, gender, birth);
		
	}
	
	public void deleteData() {
		System.out.print("삭제할 번호 입력: ");
		int num = sc.nextInt();

		dao.deleteData(num);
	}
	
	public void insertData() {
		System.out.println("번호는 시퀀스로 자동 입력(생략)");
		System.out.print("이름 입력: ");
		String name = sc.next();
		
		System.out.print("나이 입력: ");
		int age = sc.nextInt();
		
		System.out.print("성별 입력: ");
		String gender = sc.next();
		
		System.out.print("생년월일 입력: ");
		String birth = sc.next();
		
		dao.insertData(name, age, gender, birth); // 그제서야 dao 객체를 통해 호출
	}
	
	
	public static void main(String[] args) {
		PersonMain per = new PersonMain();
	}

}
