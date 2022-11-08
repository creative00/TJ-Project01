package banking;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;

abstract class Account implements Serializable{

	String account; // 계좌번호
	String name; // 고객명
	String grade;
	int balance; // 잔고
	int rate;
	

	public Account(String account, String name, int balance) {
		this.account = account;
		this.name = name;
		this.balance = balance;	
	}
	public void showAccInfo() {
		System.out.println("--------------");
		System.out.println("계좌번호:" + account);
		System.out.println("고객이름:" + name);
		System.out.println("잔고:" + balance);
	}
	public void interest(int deposit) {
	}
	
//	public String toString() {
//		return String.format("계좌번호:%s", account);
//	}
//	
	
	public int hashCode() { //오버라이딩용
		return Objects.hash(account);
	}
	
	//첫번째 방식 계좌번호가 중복되지 않기위한 이퀄스 
	public boolean equals(Object obj) { //두번째 방식인데 전체 정보를 다 집어넣는줄 알았음
		if(!(obj instanceof Account)) return false;
		
		Account tmp = (Account)obj;
		return this.account.equals(tmp.account); 
	}
}	
