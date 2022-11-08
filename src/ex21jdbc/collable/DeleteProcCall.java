package ex21jdbc.collable;

import java.sql.SQLException;
import java.sql.Types;

import ex21jdbc.connect.IConnectlmpl;

public class DeleteProcCall extends IConnectlmpl {
	
	public DeleteProcCall() {
		super("education", "1234");
	}
	
	public void execute() {
		try {
			//프로시저 호출을 위한 CallableStatement객체 생성
			csmt = con.prepareCall("{call MyMemberDelete(?,?)}");
			//인파라미터 설정 : 사용자로부터 받은 입력값을 세팅
			csmt.setString(1, scanValue("삭제할아이디"));
			//아웃파라미터의 반환타입 설정
			csmt.registerOutParameter(2, Types.VARCHAR);
			//프로시저 실행
			csmt.execute();
			//아웃파라미터가 문자형이므로 getString()으로 출력~~~
			System.out.println("삭제프로시저 실행결과 ");
			System.out.println(csmt.getString(2));
			
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			close();
		}
		
	}
	public static void main(String[] args) {
		new DeleteProcCall().execute();

	}

}
