package shopping;

import java.sql.Date;
import java.util.Scanner;
import conn.Connimpl;

public class InsertShop extends Connimpl{

	//생성자 : 부모클래스의 생성자를 호출하여 연결한다.
	public InsertShop() {
		super(ORACLE_DRIVER, "education", "1234");
	}
	@Override
	public void execute() {
		try {
			//1. 쿼리문 준비: 값의 세팅이 필요한 부분을 ?(인파라미터)로 기술한다.
			String query= "INSERT INTO sh_goods VALUES (?, ?, ?, ?, ?, ?)";
			//2. Prepared 객체 생성 : 객체 생성 시 준비한 쿼리문을 인수로 전달한다.
			psmt = con.prepareStatement(query);
			//3.사용자로부터 insert할 내용을 입력받는다.
			Scanner scan = new Scanner(System.in);
			System.out.print("상품일련번호:");
			int g_idx = scan.nextInt();
			System.out.print("상품명:");
			String goods_name = scan.nextLine();
			System.out.println("상품가격:");
			int goods_price = scan.nextInt();
			System.out.println("등록일:");
			String regidate = scan.nextLine();
			System.out.println("상품코드:");
			int p_code = scan.nextInt();
			
			psmt.setInt(1, g_idx);
			psmt.setString(2, goods_name);
			psmt.setInt(3, goods_price );
			psmt.setString(4, "yyyy-mm-dd");
			psmt.setInt(5, p_code);
			
			//날짜입력1: 날짜를 문자열로 입력하는 경우
//			psmt.setString(4, "2022-10-27");

			//날짜 입력2: Date형으로 입력하는 경우
			/*
			현재날짜를 Java에서 입력하는 경우 아래와 같이 변환과정을 거쳐야 한다.
			util패키지로 시간을 얻어온 후 sql패키지로 타입을 변환한다.
			이 때는 date형으로 입력되므로 setDate()로 인파라미터를 설정해야한다.
			 */
//			java.util.Date utilDate = new java.util.Date();
//			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//			psmt.setDate(5,  sqlDate);
			int affected = psmt.executeUpdate();
			System.out.println(affected +"행이 입력되었습니다.");	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
	}
	public static void main(String[] args) {
		new InsertShop().execute();
	}
}
