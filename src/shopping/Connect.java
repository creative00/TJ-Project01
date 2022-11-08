package shopping;

public interface Connect {
	//멤버상수 : 오라클 드라이버명과 커넥션URL을 선언
			String 	ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
			String	ORACLE_URL = "jdbc:oracle:thin:@localhost:1521:xe";
			
			//멤버 추상 메서드
			void connect(String user, String pass); //DB 연결
			void execute(); //쿼리문 실행
			void close(); //자원 반납
			//사용자로부터 입력을 받기 위해 정의
			String scanValue(String title);
}
