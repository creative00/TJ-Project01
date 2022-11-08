package shopping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
 
public class InsertSQL  {
	
	//멤버변수
	Connection con; //DB 연결을 위한 객체
	Statement stmt; //정적쿼리문을 전송 및 실행하기 위한 객체
	
	//생성자
	public InsertSQL() {
		try {
			//드라이버 로드 및 오라클 DB 연결
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "education";
			String pass = "1234";	
			con = DriverManager.getConnection(url,id,pass);
			System.out.println("오라클 DB 연결성공");
		}	
		catch (Exception e) {
			System.out.println("연결실패");
		}
	}//end of InsertShop
	
	//DB자원해제
	private void close() {
		try {
			//연결 확인 후 자원을 해제한다. 
			if(stmt!=null) stmt.close();  
			if(con!=null) con.close(); 
			System.out.println("DB자원반납완료");
		}
		catch(SQLException e) {
			System.out.println("자원반납시 오류가 발생하였습니다.");
		}
	}//end of close
	
	//쿼리작성 및 전송, 실행을 위한 메서드
	private void execute()	{
		try {
			//Statement 객체 생성을 위한 메서드 호출
			stmt = con.createStatement();
			//정적 insert 쿼리문 작성(인파라미터가 없는 쿼리문)
			String sql = "INSERT INTO member VALUES "
					+ " ('test9', '9999', '테스터9', sysdate)";
			//쿼리문 전송 및 실행
			int affected = stmt.executeUpdate(sql);
			//insert문 실행에 대한 결과 출력
			System.out.println(affected +"행이 입력되었습니다.");
			//자원해제
			close();
		}
		catch(SQLException e) {
			System.out.println("쿼리실행에 문제가 발생하였습니다.");
			e.printStackTrace();
		}
	} 

	public static void main(String[] args) {
		InsertSQL iSQL = new InsertSQL();
		iSQL.execute();	
	}//end of main
}
