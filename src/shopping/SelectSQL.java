package shopping;

import java.util.Date;

import shopping.ConnectDBforShopping;

public class SelectSQL extends ConnectDBforShopping {
		public SelectSQL() {
			super();
		}
		@Override
		public void execute() {
			try {
				stmt = con.createStatement();
				 
				String query = "SELECT G_IDX, GOODS_NAME, GOODS_PRICE,"
						+ " to_char(regidate, 'yyyy.mm.dd hh24:mi') d1, "
						+ " P_CODE"+ " FROM sh_goods";
				/*
				쿼리실행시 사용하는 메서드
				executeUpdate()
				 	: 쿼리문이 insert, update, delete와 같이 기존 레코드에 
				 	변화가 생기는 경우에 사용한다. 실행후 적용된 행의 갯수를
				 	int형으로 반환한다. 			
				executeQuery() 
					: select 쿼리를 실행할때 호출하는 메서드로 레코드에 영향을
					미치지않고 조회만 하는 경우 사용한다. 
					조회한 레코드를 반환값으로 받아야 하므로 ResultSet객체가
					반환타입으로 정의되어있다. 
				 */
				rs = stmt.executeQuery(query);
				while(rs.next()) {
					int g_idx = rs.getInt(1);
					String goods_name = rs.getString(2);
					String good_price = rs.getString(3);
					Date regidate = rs.getDate(4);
					int p_code = rs.getInt(5);
					System.out.printf("%d %s %s %t %d\n",
						g_idx, goods_name, good_price, regidate, p_code);
					/*
					오라클의 date타입을 getDate()로 추출하면 0000-00-00형태로
					출력된다. 또한 자료형이 Date이므로 java.sql패키지의 
					클래스를 사용해야한다.  
					 */
					//java.sql.Date regidate = rs.getDate("regidate");
					/*
					날짜를 getString()으로 추출하면 시간까지 포함되서 출력된다. 
					0000-00-00 00:00:00 형식이므로 적당한 크기로 자르고 싶다면
					substring()메서드를 사용하면 된다. 
					 */
					String regidate2 = rs.getString("regidate");
					String regidate3 = rs.getString("regidate").substring(0,13);
					/*
					쿼리문에 별칭을 사용한 경우 별칭을 통해 출력할 수 있다. 
					즉 컬럼명, 인덱스, 별칭을 통해 컬럼에 저장된 값을 추출한다.
					 */
					String regidate4 = rs.getString("d1");
					System.out.printf("%d %s %d %s %d %s %s %s\n",
							g_idx, goods_name, good_price, regidate, p_code,
							regidate2, regidate3, regidate4);
				}
			}
			catch(Exception e) {
				System.out.println("쿼리오류발생");
				e.printStackTrace();
			}
			finally {
				close();
			}
		}
		public static void main(String[] args) {
			SelectSQL selectSQL = new SelectSQL();
			selectSQL.execute();
		}	
}
