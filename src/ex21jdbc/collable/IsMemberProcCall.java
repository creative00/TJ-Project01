package ex21jdbc.collable;

import java.sql.SQLException;
import java.sql.Types;

import ex21jdbc.connect.IConnectlmpl;

public class IsMemberProcCall extends IConnectlmpl {

	public IsMemberProcCall() {
		super("education", "1234");
	}
	public void execute() {
		try {
		
			csmt = con.prepareCall("{call MyMemberAuth(?,?,?)}");
			
			csmt.setString(1, scanValue("회원 아이디"));
			csmt.setString(2, scanValue("회원 패스워드"));
			csmt.registerOutParameter(3, Types.NUMERIC);
			csmt.execute();
		
			int outParamResult = csmt.getInt(3);
			
			switch(outParamResult) {
			case 0:
				System.out.println("회원아이디가 없어요. 회원가입해주삼");
				break;
			case 1:
				System.out.println("패스워드가 일치하지 않아요. 비번찾기하삼");
				break;
			case 2:	
				System.out.println("회원님 방가방가^^*");
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally {
			close();
		}
		
	}
	public static void main(String[] args) {
		IsMemberProcCall mapc = new IsMemberProcCall();
		mapc.execute();
	}
}

/*
 IConnectlmpl 인자생성자 호출
회원 아이디을(를) 입력(exit->종료):bbbb
회원 패스워드을(를) 입력(exit->종료):bbbb
회원님 방가방가^^*
자원 반납 완료


회원 아이디을(를) 입력(exit->종료):test1
회원 패스워드을(를) 입력(exit->종료):0000
패스워드가 일치하지 않아요. 비번찾기하삼
자원 반납 완료
 */