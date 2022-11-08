package ex21jdbc.prepared;

import java.sql.SQLException;

import ex21jdbc.connect.IConnectlmpl;

public class UpdateSQL extends IConnectlmpl{

	public UpdateSQL() {
		super("education", "1234");
	}
	@Override
	public void execute()
	{
		//update 쿼리문 생성
		String sql = "UPDATE member "
				+ " SET name=?, pass=? "
				+ "WHERE id=?";
		try {
			//prepared 객체 생성
			psmt = con.prepareStatement(sql);
			//사용자가 exit를 입력할 때까지 계속 실행되는 구조로 구현함.
			while(true) {
				/*
				인파라미터값 설정 시 인덱스만 일치하면 순서는 상관없다.
				scanValue() 메서드가 반환하는 값으로 즉시 인파라미터를 
				세팅한다.
				 */
				psmt.setString(3, scanValue("수정할 아이디"));
				psmt.setString(1, scanValue("이름"));	
				psmt.setString(2, scanValue("패스워드"));			
				
				//쿼리문 실행 및 결과 확인
				int affected = psmt.executeUpdate();
				System.out.println(affected + "행이 업데이트되었습니다.");
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
		new UpdateSQL().execute();
	}

}
