package ex21jdbc.collable;

import java.sql.SQLException;
import java.sql.Types;


/*
 Java에서 Oracle 함수 호출하기
 오라클에서 정의한 함수 실행하기 위한 callableStatement 객체생성
 -Connection 객체의 prepareCall() 메서드 호출 통해 생성한다.
 -형식
 	prepareCall("{? = call 함수명(인수1, 인수2, ... N}");
 -첫번째 물음표는 반환값을 의미한다.
 	반환값은 피라미터 설정 시 java.sql.Types 클래스의 자료형으로 설정한다.
 */

import ex21jdbc.connect.IConnectlmpl;

public class FunctionCall extends IConnectlmpl {
	
	//부모클래스의 생성자 호출을 통해 DB연결
	public FunctionCall() {
		super("education", "1234");
	}
	
	public void execute() {
		try {
			/*
			callableStatement 객체 생성
			? = call 함수명(?)
			첫번째 파라미터 : 함수 실행 후 반환값 저장
			두번째 파라미터 : 함수 호출 시 전달할 값(인수)
			*/
			csmt = con.prepareCall("{? = call fillAsterik(?)}");
			//반환값의 자료형 설정
			csmt.registerOutParameter(1, Types.VARCHAR);
			//인수에 대한 파라미터 설정
			csmt.setString(2, scanValue("아이디"));
			//호출해 함수 실행
			csmt.execute();
			//반환값 출력
			System.out.println("함수의반환값:"+ csmt.getString(1));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
	}

	public static void main(String[] args) {
		new FunctionCall().execute();
	}

}
