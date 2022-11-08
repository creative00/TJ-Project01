package banking;
class MenuSelectException extends Exception{
	
	public MenuSelectException() {
		super("1~6사이의 정수만 입력해주세요.");
	}
}