package ex21jdbc.statement;

import java.sql.SQLException;

public class SelectQuery extends ConnectDB {
	//매개변수가 없는 부모의 생성자를 호출하여 DB 연결
	public SelectQuery() {
		super();
	}
	/*
	ResultSet 인터페이스
	: select문 실행 시 쿼리의 실행결과를 ResultSet 객체 통해 받는다.
	결과로 반환된 레코드의 처음부터 끝까지 next()메서드 통해 확인 후
	반복 인출된다.
	
	컬럼의 자료형이
	number > getInt()
	char/varchar2 getString()
	date getDate() 메서드 통해 출력한다.
	해당 메서드의 인수는 인덱스(1부터 시작) 혹은 컬럼명을 사용 가능.
	 */
	

	public void execute() {
		try {
			stmt = con.createStatement();
			
			String query = "SELECT id, pass, name, regidate,"
					+ " to_char(regidate, 'yyyy.mm.dd hh24:mi') d1 "
					+ "FROM member";
			/*
			쿼리 실행 시 사용하는 메서드
			executeUpdate()
				: 쿼리문이 insert, update, delete 와 같이
				기존 레코드에 변화가 생기는 경우에 사용한다.
				실행 수 적용된 행의 갯수를 int형으로 반환한다.
			executeQuery()
				: select 쿼리르 실행할 때 호출하는 메서드로 
				레코드에 영향을 미치지않고 조회만 하는 경우 사용한다.
				조희한 레코드를 반환값으로 받아야 하므로
				ResultSet객체가 반환 타입으로 정의되어있다.
			 */
			
			rs = stmt.executeQuery(query);
			while(rs.next()) {
				String id= rs.getString(1);//id컬럼
				String pw= rs.getString("pass");
				String name= rs.getString("name");
				java.sql.Date regidate = rs.getDate("regidate");
				
			/*
			오라클의 date타입을 getDate()로 추출하면 0000-00-00형태로 출력됨
			또한 자료형이 Date이르모 java.sql패키지의 클래스를 사용해야 한다.	
			 */
				String regidate2 = rs.getString("regidate");
				String regidate3 =
				rs.getString("regidate").substring(0,13);
				/*
				날짜를 getString()으로 추출하면 시간까지 포함되서 출력된다.
				적당한 크기로 자르고싶다면 substring() 메서드 활용		
				 */
				
				/*
				쿼리문에 별칭 사용할 경우 별칭 통해 출력 가능
				즉 컬럼명 인덱스 별칭을 통해 컬럼을 출력할 수 있다.
				 */
				String regidate4 = rs.getString("d1");
				System.out.printf("%s %s %s %s %s %s %s\n",
						id, pw, name, regidate,
						regidate2, regidate3, regidate4);
			}
		}
		catch(SQLException e) {
			System.out.println("쿼리오류발생");
			e.printStackTrace();
		}
		finally {
				close(); //DB 자원반납
		}
	}
	public static void main(String[] args) {
		SelectQuery selectSQL = new SelectQuery();
		selectSQL.execute();
	}
}
