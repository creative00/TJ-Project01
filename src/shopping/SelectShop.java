package shopping;

import java.sql.SQLException;

import conn.Connimpl;

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
				String sql = "SELECT * FROM sh_product_code "
						//+" WHERE name LIKE '%?%' "; //에러발생:부적합한 열 인덱스
						+ " WHERE name LIKE '%'||?||'%'";
				psmt = con.prepareStatement(sql);
				psmt.setString(1, scanValue("찾는이름"));
				rs = psmt.executeQuery();
				while(rs.next()) {
					int g_idx = rs.getInt(1);
					String goods_name = rs.getString(2);
					String good_price = rs.getString(3);
					String regidate = rs.getString(4).substring(0,10);
					int p_code = rs.getInt(5);
					System.out.printf("%d %s %s %s %d\n",
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