package banking;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingSystemMain implements ICustomDefine {
	static void ShowMenu() {
		Scanner scan = new Scanner(System.in);
		System.out.println("┌---------------Menu---------------┐");
		System.out.print("  1.계좌개설 ");System.out.print("2.입 금 ");System.out.println("3.출 금 ");
		System.out.print("  4.계좌정보출력 ");System.out.println("5.자동저장옵션 ");
		System.out.print("  6.프로그램종료 ");System.out.print("메뉴선택>>>");

		//System.out.println("└--------------------------------┘");
	}
	public static void main(String[] args) {
		
		System.out.println("1차프로젝트(학원)");
		
		AccountManager manager = new AccountManager();
		Scanner scan = new Scanner(System.in);
		AutoSaver autoSaver= null; //프로그램 실행 시 쓰레드 참조변수 선언
		// 메뉴를 출력한다.
			while (true) {
				try {
					ShowMenu(); 
					int choice = scan.nextInt();
					
					if(choice<1 || choice>7) {
						MenuSelectException ex = new MenuSelectException();
						throw ex;					
					}
					switch (choice) {
					case ICustomDefine.MAKE:
						manager.makeAccount();
						break;
					case ICustomDefine.DEPOSIT:
						manager.depositMoney();
						break;
					case ICustomDefine.WITHDRAW:
						manager.withdrawMoney();	
						break;
					case ICustomDefine.INQUIRE:
						manager.showAccInfo();
						break;
					case ICustomDefine.AUTOSAVE:
						//자동옵션 선택 시 쓰레드객체 생성
						if(autoSaver==null || (!autoSaver.isAlive())) {
							autoSaver = new AutoSaver(manager);}
						manager.autoSave(autoSaver);
						break;	
					case ICustomDefine.EXIT:
						System.out.println("프로그램 종료");
						manager.saveAccInfo();
						return;
						
					}	
				}
				catch(InputMismatchException a) {
					scan.nextLine();
					System.out.println("  숫자만 입력해주세요."+ a.getMessage());
					//a.printStackTrace();
				}
				catch (MenuSelectException ex) {
					System.out.println(""+ ex.getMessage());	
					ex.printStackTrace();
				}	
				catch(Exception e) {	
					System.out.println("예외3가 발생하였습니다.");
					e.printStackTrace();
				}	//while	
		}
	}//publc	
}
