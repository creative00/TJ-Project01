package ex21jdbc.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/*
상속을 목적으로 생성하는 추상클래스. 오라클 DB연결과 자원반납을 담당한다.
추상클래스는 객체를 생성할 수는 없지만 참조변수선언, 상속 등의 기능은 사용할 수 있다.
 */
public abstract class ConnectDB {

		//멤버변수 : 상속관계에서 접근가능한 protected 접근지정자 사용
	protected Connection con; //DB연결 담당
	protected Statement stmt; //정적쿼리문 전송 및 싱행 담당
	protected ResultSet rs;	  //select쿼리 실행 결과를 반환 받음
		
		
		//생성자1 : 계성 아이디, 패스워드를 매개변수로 받은 후 DB연결
	public ConnectDB(String id, String pw) 
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					id, pw);
			System.out.println("오라클 DB 연결 성공");				
		}
		catch (ClassNotFoundException e){
			System.out.println("오라클 드라이버 로딩 실패");
		}
		catch (SQLException e){
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("알 수 없는 예외 발생");
			e.printStackTrace();
		}
	}
		
		//생성자2 : 앞에서 선언한 생성자를 복사하여 선언된 생성자로
		//고정된 값으로 DB연결을 담당한다. 생성자 오버로딩으로 정의되었다.
	public ConnectDB() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"education", "1234");
			System.out.println("오라클 DB 연결 성공");
		}
		catch (ClassNotFoundException e) {
			System.out.println("오라클 드라이버 로딩 실패");
			e.printStackTrace();
		}
		catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("알 수 없는 예외 발생");
		}
	}
		//자원반납
	public void close() {
		try {
			
			// Statement, Connection, ResultSet 객체에 대한 자원반납 처리 
			if(stmt!=null) stmt.close();
			if(con!=null) con.close();
			if(rs!=null) rs.close();
			System.out.println("DB자원반납완료");
		}
		catch (SQLException e) {
			System.out.println("자원 반납 시 오류가 발생하였습니다.");
			e.printStackTrace();
		}
	}//end of close
		/*
		상속관계에서 오버라이딩을 목적으로 생성한 추상메서드로 하위 클래스에서
		각 업무(update, select 등)에 따라 재정의 하게 될 것이다.
		
		추상메서드를 포함한 클래스는 반드시 추상클래스로 선언해야 에러가
		발생하지 않는다. 따라서 해당 클래스는 abstract로 선언해야 한다.
		 */
	abstract void execute();
}

