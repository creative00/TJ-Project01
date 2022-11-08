package banking;

public interface ICustomDefine {
	int MAKE=1;
	int DEPOSIT=2;
	int WITHDRAW=3;
	int INQUIRE=4;
	int AUTOSAVE=5;
	int EXIT=6;
	
	int RATE_C=2;
	int RATE_B=4;
	int RATE_A=7;
	//뱅킹시스템메인 안에 케이스문을 인터페이스형 상수로 적용시키고
	//뱅킹시스템이 이 인터페이스를 상속한다.
}

