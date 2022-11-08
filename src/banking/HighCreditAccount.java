package banking;

import java.util.Objects;

/*
 HighCreditAccount : 신용신뢰계좌 > 신용도가 높은 고객에게 개설이 허용되며 높은 이율의 계좌이다.
NormalAccount 와 마찬가지로 생성자를 통해서 이율정보(이자비율의정보)를 초기화 할수있도록 정의한다.
고객의 신용등급을 A, B, C로 나누고 계좌개설시 이 정보를 등록한다.
A,B,C 등급별로 각각 기본이율에 7%, 4%, 2%의 이율을 추가로 제공한다.
 
 */


public class HighCreditAccount extends Account implements ICustomDefine {
	int rate;
	String grade;
	int extrarate;
	public HighCreditAccount (String account, String name, int balance,int rate, String grade) {
		super(account, name, balance);
		this.rate =rate;
		this.grade=grade;
	}
	
	
	@Override
	public void showAccInfo() {
		// TODO Auto-generated method stub
		super.showAccInfo();
		System.out.println("기본이자: "+ rate+"%");
		System.out.println("신용등급: "+ grade);
	}
	
	
	
//	public int hashCode() { //오버라이딩용
//		return Objects.hash(account);
//	}
//	
//
//	public boolean equals(Object obj) { 
//		if(!(obj instanceof Account)) return false;
//		
//		Account tmp = (Account)obj;
//		return account.equals(tmp.account); 
//	}
//	

	
//	@Override
//	public String toString() {
//		return String.format("계좌번호:%s", account);
//	}
//	

	@Override
	public void interest(int deposit)
	{
		if(grade.equalsIgnoreCase("A")) {
			extrarate= RATE_A;		
		}
		else if(grade.equalsIgnoreCase("b")) {
			extrarate= RATE_B;	
		}
		else if(grade.equalsIgnoreCase("c")) {
			extrarate= RATE_C;	
		}
		balance = (int)(balance + (balance*rate/100) + (balance*extrarate/100)  + deposit);
		System.out.println("HighCreditAccount>interest호출됨");
	}
}	

