package ex21jdbc.statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteQuery {
	//멤버변수: DB연결 및 쿼리실행
	private Connection con;
	private Statement stmt;

	//생성자: DB 연결 실행 
	public DeleteQuery() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id="education";
			String pass ="1234";
			
			con = DriverManager.getConnection(url,id,pass);
			System.out.println("오라클 DB 연결성공");
		}
		catch (Exception e) {
			System.out.println("연결실패");
		}
	}//end of InsertQuery
	private void close () {
		try {
			if(stmt!=null) stmt.close();
			if(con!=null) con.close();
			System.out.println("DB자원반납완료");
		}
		catch (SQLException e) {
			System.out.println("자원반납 시 오류가 발생하였습니다.");
		}
	}
	//쿼리실행 
	private void execute() {
		try {
			//Statement 객체 생성을 위한 메서드 호출
			stmt = con.createStatement();
			//정적 insert쿼리문 작성(인파라미터가 없는 쿼리문)
			String query = "DELETE FROM member WHERE id = 'test3' ";
			
			/*
			insert, delete, update와 같이 행의 변화가 발생되는 쿼리문을
			실행할 때는 excuteUpdate() 메서드를 사용한다.
			해당 쿼리를 실행 후 영향을 받은 레코드의 갯수가
			int타입으로 반환
			 */
					
			int affected = stmt.executeUpdate(query);
			System.out.println(affected +"행이 삭제됨");
			close();
		}
		catch( SQLException e){
			System.out.println("쿼리실행에 문제가 발생하였습니다.");
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new DeleteQuery().execute();
	}
}
