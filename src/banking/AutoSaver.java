package banking;

public class AutoSaver extends Thread
{	
	AccountManager mgr;
	
	public AutoSaver(AccountManager mgr) {
		this.mgr = mgr;
	}
	
	@Override
	public void run(){
		try {
				while(true) {
				mgr.autoSaveFile();
				sleep(5000); //5초 동안 블럭상태로 전환
				System.out.println("자동 저장중...(5초)");
			}
		}
		catch(InterruptedException e) {
		} 
	}

}
