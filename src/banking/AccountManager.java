package banking;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.MissingFormatArgumentException;
import java.util.Scanner;

public class AccountManager {
	
	String account, name, grade;
	int balance, rate;
	
	Scanner scan = new Scanner(System.in);

	HashSet<Account> accounts;

	public AccountManager() {
		accounts = new HashSet<Account>();
		readAccInfo(); //생성과 동시에 복원할 파일 읽어오기
	}
	
	public void showAccInfo() { 
		//배열을 반복하면서 Account 클래스의 showAccount() 
		for( Account acc : accounts) {
			acc.showAccInfo();
			System.out.println("***신규계좌개설***");
		}
	}	
	
	public void makeAccount() {
		
		System.out.println();
		System.out.println("┌------신규개설계좌선택------┐");
		System.out.println("│ 1.보통계좌  2.신용신뢰계좌 │");
		System.out.println("└----------------------------┘");
		
		System.out.print(" 메뉴선택>>>");
		int selec = scan.nextInt();
		scan.nextLine();
		if(!(selec==1 || selec==2)) {
			InputMismatchException aa= new InputMismatchException();
			throw aa;	
		}
		
		if(selec ==1) {
			System.out.println("------신규개설계좌선택------");
			System.out.print("계좌번호: ");String account = scan.nextLine();
			System.out.print("고객이름: ");String name = scan.nextLine(); 
			System.out.print("잔고: ");	int balance = scan.nextInt();
			System.out.print("기본이자%(정수형태로입력): "); int rate = scan.nextInt(); 
			scan.nextLine();
			NormalAccount nomAcc =  new NormalAccount(account, name, balance, rate);			
			
			//중복계좌체크
			boolean duplicateCheck = accounts.add(nomAcc);
			if(duplicateCheck==false) {
				System.out.print("중복계좌 입니다. 덮어쓸까요?(y or n): ");
				String openAccount = scan.nextLine();
				if(openAccount.equalsIgnoreCase("y")) {
					accounts.remove(nomAcc);
					accounts.add(nomAcc);
					System.out.println("계좌 개설이 완료되었습니다.");
				}
				else if(openAccount.equalsIgnoreCase("n")) {
					System.out.println("계좌 개설을 종료합니다.");
				}
			}
			
		}
		
		else if (selec ==2) {
			System.out.println("┌------신규개설계좌선택------┐");
			System.out.print("계좌번호: ");String account = scan.nextLine();
			System.out.print("고객이름: ");String name = scan.nextLine();
			System.out.print("잔고: ");int balance = scan.nextInt();
			System.out.print("기본이자%(정수형태로입력): ");int rate = scan.nextInt(); 
			scan.nextLine();
			System.out.print("신용등급(A,B,C등급): ");String grade = scan.nextLine();
			HighCreditAccount highAcc =  new HighCreditAccount(account, name, balance, rate, grade);	
			
			boolean duplicateCheck = accounts.add(highAcc);
			if(duplicateCheck==false) {
				System.out.println("이미 등록된 계좌번호입니다. 덮어쓸까요?(y or n)");
				String openAccount = scan.nextLine();
				if(openAccount.equalsIgnoreCase("y")) {
					accounts.remove(highAcc);
					accounts.add(highAcc);
					System.out.println("계좌 개설이 완료되었습니다.");
				}
				else if(openAccount.equalsIgnoreCase("n")) {
					System.out.println("계좌 개설을 종료합니다.");
				}
			}
		}
//		catch(InputMismatchException aa) {
//			System.out.println("숫자 1,2로만 입력해주세요.");
//		}		
	}//publicvoid	
	
	
	public void depositMoney() {
		try {	
			System.out.println("┌---------------입금---------------┐");
			System.out.println("계좌번호와 입금할 금액을 입력하세요");
			System.out.println("입금을 500원 단위로 부탁드립니다.");
			Scanner scan = new Scanner(System.in);
			System.out.print("계좌번호: ");
			String account = scan.nextLine();		
			System.out.print("입금: ");
			int deposit = scan.nextInt();
			scan.nextLine();
			
			//이터레이터를 사용하여 계좌 보유여부 체크 및 입금
			boolean findAccount = false;
			Iterator<Account> itr = accounts.iterator();
			while(itr.hasNext()) {
				Account ac = itr.next(); //계정에 관한 설정	
				if(account.compareTo(ac.account)==0) { //계좌가 같을 때 
						if (deposit <0) {
							System.out.println("-숫자라 입금이 불가능합니다.");
							return;
						}
						else if (deposit==0) {
							System.out.println("입금액이 없어 입금이 불가능합니다.");
							return;
						}
						else if(deposit%500!=0){
							System.out.println("500원 단위가 아니라 입금 불가능합니다.");
						}
						else {
							ac.interest(deposit);
							System.out.println("입금이 완료되었습니다1 ");
							System.out.println("└--------------------------------┘");		
							findAccount = true;
						}
						
				}//else if
			}//while
			if(findAccount==false) {
				System.out.println("등록되지 않은 계좌번호입니다1."); 
			}
		}//try	
		catch (InputMismatchException a){ //숫자만 입력하고 그 외는 에러발생
			System.out.println("[오류발생] 문자는 입력할 수 없습니다.");
			System.out.println("숫자만 입력해주세요.");
			a.printStackTrace();
			return;
		}
	}		
	public void withdrawMoney() {
		try {			
			System.out.println("┌--------------출금--------------┐");
			System.out.println("계좌번호와 출금할 금액을 입력하세요");
			System.out.println("1000원 단위로 출금 가능합니다.");
			Scanner scan = new Scanner(System.in);
			System.out.print("계좌번호: ");
			String account = scan.nextLine();
			System.out.print("출금: ");
			int withdraw = scan.nextInt();
			scan.nextLine();//버퍼
			
			boolean findAccount = false;
			Iterator<Account> itr = accounts.iterator();
			while(itr.hasNext()) {
				Account ac = itr.next(); //계정에 관한 설정
				if(account.equals(ac.account)) {  //검색한 계좌번호가 저장된 계좌번호와 같을 때
					if(withdraw<0) { //-출금액
						System.out.println("- 숫자라 출금이 불가능합니다.");
						return; //실행잘됨
					}
					if(withdraw==0) {
						System.out.println("출금액이 0이라 불가능합니다.");
						return; //실행잘됨
					}
					if(withdraw%1000!=0){
						System.out.println("출금 단위가 아니라 출금이 불가능합니다.");
						System.out.println("└----------------------------------┘");
					}
					if(withdraw>0) {
						if(withdraw%1000==0){ //1000단위의 출금액일 때
							if(ac.balance < withdraw){ //잔고가 출금액보다 부족할 때 	
								//System.out.println("└--------------------------------┘");
								System.out.println("┌---------------출금---------------┐");
								System.out.println("■ 잔고가 부족합니다. 금액 전체를 출금할까요?");
								System.out.println("Yes면 1을, No면 2을 입력해주세요.");
								System.out.print("메뉴선택>>");String select= scan.nextLine();
								
								if(select.equalsIgnoreCase("1")) { //alldraw라는 함수 만들어서 
									ac.balance = 0;
									
									System.out.println("YES :금액전체 출금처리 되었습니다.");
									System.out.println("└----------------------------------┘");
									
								}
								else if(select.equalsIgnoreCase("2")) {
									System.out.println( "NO 출금요청 취소");
									System.out.println("└----------------------------------┘");
								}
							}	
							else{
								ac.balance -= withdraw;
								System.out.println("출금되었습니다."); //이쪽으로 
								System.out.println("└----------------------------------┘");	
							}	
						}
					}else if(findAccount==false) {
						System.out.println("등록되지 않은 계좌번호입니다.7"); 
					}	
				}	
			}
		}	
		catch (InputMismatchException a){
			System.out.println("예외가 발생하였습니다.");
			System.out.println("숫자만 입력해주세요.");
			a.printStackTrace();
			return;
		}
		catch(Exception e) {	
			System.out.println("예외가 발생하였습니다.");
			e.printStackTrace();
		}
	}

	
	public void saveAccInfo() {
		try {
			//인스턴스를 파일에 저장하기 위해 출력스트림 생성
			ObjectOutputStream out =
					new ObjectOutputStream(
							new FileOutputStream("src/banking/AccountInfo.obj"));
			
			for(Account acc : accounts) {
				out.writeObject(acc);
			}
			out.close();
		}
		catch (Exception e) {
			System.out.println("계좌 정보 파일 저장시 예외 발생");
		}
	}
//복원(역직렬화)을 위한 스트림 생성
	public void readAccInfo() {
		try {
			ObjectInputStream in = 
					new ObjectInputStream(
							new FileInputStream("src/banking/AccountInfo.obj"));
			
			//데이터 복원
			while(true) {
				Account acc = (Account)in.readObject();
				//읽어와서 다시 컬렉션에 저장
				accounts.add(acc);
				if(acc ==null) break;
			}
			in.close();	
		}
		catch (Exception e) {
			System.out.println("모든 계좌를 불러왔습니다.");
		}
		System.out.println("계좌 정보 복원 완료");
	}
	//자동저장 on/off
	 
	 
	public void autoSave(AutoSaver autoThread) {
		System.out.println("┌───────자동저장옵션선택──────┐");
		System.out.println("│ 1.자동저장on  2.자동저장off │");
		System.out.println("└─────────────────────────────┘");
		int autoselec = scan.nextInt();
		scan.nextLine();
		
		//자동저장on
		if(autoselec == 1) {
			if(autoThread.isAlive()) {
				System.out.println("이미 자동저장이 실행중입니다.");
			}
			else {
				//독립쓰레드를 종속쓰레드로 만듦
				autoThread.setDaemon(true);
				//쓰레드 실행
				autoThread.start();
				System.out.println("자동 저장을 실행합니다.");
			}
		}	
		//자동저장off
		else if(autoselec ==2) {
			if(autoThread.isAlive()) {
				autoThread.interrupt();
				System.out.println("자동 저장이 중지되었습니다.");
			}
		}
	}
	public void autoSaveFile() {
		try{
			PrintWriter out = new PrintWriter(
					new FileWriter("src/banking/AutoSaveAccount.txt"));
			
			for(Account acc: accounts) {
				if(acc instanceof HighCreditAccount) {
					out.printf("계좌번호:%s, 예금주:%s, 잔고:%d, 이율:%d, 신용등급:%s",
							((HighCreditAccount)acc).account, 
							((HighCreditAccount)acc).name,
							((HighCreditAccount)acc).balance,
							((HighCreditAccount)acc).rate,
							((HighCreditAccount)acc).grade);
					out.println();
				}
				else if(acc instanceof NormalAccount) {
					out.printf("계좌번호:%s, 예금주:%s, 잔고:%d, 이율:%d",
							((NormalAccount)acc).account, 
							((NormalAccount)acc).name,
							((NormalAccount)acc).balance,
							((NormalAccount)acc).rate);
					out.println();
				}
			}
			out.close();	
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}	
}