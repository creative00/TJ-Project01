package ex21jdbc.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertQuery {

	Connection con;
	Statement stmt;
	
	public InsertQuery() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id="education";
			String pass ="1234";
			
			con = DriverManager.getConnection(url,id,pass);
			System.out.println("오라클 DB 연결성공");
		}
		catch (Exception e){
			System.out.println("연결실패");
		}
	}//end of InsertQuery
	
	private void close() {
		try {
			
			
			if(stmt!=null) stmt.close();
			if(con!=null) con.close();
			System.out.println("DB자원반납완료");
		}
		catch (SQLException e){
			System.out.println("자원반납 시 오류가 발생하였습니다.");
			
		}
	}//end of close
	
	//쿼리 작성 및 전송, 실행 위한 메서드
	private void execute() {
		try {
			//Statement 객체 생성을 위한 메서드 호출
			stmt = con.createStatement();
			//정적 insert쿼리문 작성(인파라미터가 없는 쿼리문)
			String sql = "INSERT INTO member VALUES "
					+ " ('test4', '4444','테스터4',sysdate) ";
			//쿼리문 전송 및 실행
			int affected = stmt.executeUpdate(sql);
			System.out.println(affected +"행이 입력되었습니다.");
			close();
		}
		catch( SQLException e){
			System.out.println("쿼리실행에 문제가 발생하였습니다.");
			e.printStackTrace();
		
		}
	}
	public static void main(String[] args) {
		InsertQuery iSQL = new InsertQuery();
		iSQL.execute();
	}//end of main
}


//insert라 한번만 실행하고 두번째는 같은 데이터 입력이라 오류남.