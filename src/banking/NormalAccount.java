package banking;
import java.util.Objects;

/*
AccountManager라는 이름의 컨트롤클래스를 정의하고, 기능에 관련된 로직을 이동시킨다. 
Account  객체의 저장을 위해 선언한 배열과 변수도 이 클래스의 멤버에 포함시킨다.
AccountManager 클래스 기반으로 프로그램이 실행되도록 main 함수를 변경한다.
Account 클래스를 상속하는 다음 두 클래스를 정의한다.

*NormalAccount : 보통예금계좌 > 최소한의 이자를 지급하는 자율 입출금식 계좌
보통예금계좌를 의미
생성자를 통해 이율정보(이자비율의정보)를 초기화 할 수 있도록 정의

 
 */
class NormalAccount extends Account {
	int rate;
	public NormalAccount(String account, String name, int balance,int rate) {
		super(account, name, balance);
		this.rate= rate;
	}
	
	
	@Override
	public void showAccInfo() {
		// TODO Auto-generated method stub
		super.showAccInfo();
		System.out.println("기본이자: "+ rate+"%");
	}	
	
	@Override
	public void interest(int deposit)
	{
		balance = (int)(balance + (balance*rate/100) + deposit);
		System.out.println("NormalAccount>interest호출됨");
	}
	
//	
//	
//	
//	public int hashCode() { //오버라이딩용
//		return Objects.hash(account);
//	}
//	
//	
//	public boolean equals(Object obj) { 
//		if(!(obj instanceof Account)) return false;
//		
//		Account tmp = (Account)obj;
//		return this.account.equals(tmp.account); 
//	}	
}

