package shopping;
import java.sql.Date;
import java.sql.SQLException;

public class SelectShop extends Connimpl{
	public SelectShop(){
		super("education", "1234");
	}	
	@Override
	public void execute() {
		try 
		{
			while(true) 
			{
				String sql = " SELECT * FROM sh_goods "
						//+" WHERE name LIKE '%?%' "; //에러발생:부적합한 열 인덱스
						+ " WHERE goods_name LIKE '%'||?||'%' ";
				//select * from sh_goods where goods_name like '%abc%'
				psmt = con.prepareStatement(sql);
				psmt.setString(1, scanValue("찾는이름"));
				System.out.println(sql);
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					int g_idx = rs.getInt(1);
					String goods_name = rs.getString(2);
					int good_price = rs.getInt(3);
					Date regidate = rs.getDate(4);
					int p_code = rs.getInt(5);
					System.out.printf("%d %s %d %s %d\n",
						g_idx, goods_name, good_price, regidate, p_code);
				}
				
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
		new SelectShop().execute();
	}
}