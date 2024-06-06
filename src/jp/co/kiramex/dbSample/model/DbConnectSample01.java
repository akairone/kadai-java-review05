package jp.co.kiramex.dbSample.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnectSample01 {

	public static void main(String[] args) {
		// 3.データベース接続と結果取得のための変数制限
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;


		try {
			// 1.ドライバのクラスをJava上で読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2.DBと接続する
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
				"root",
				"KaniKaniKorokke2"
					);

			// 4.DBとやりとりをする窓口(Statementオブジェクト)の作成
			stmt = con.createStatement();


			// 5,6.Select文の実行と結果を格納/代入
			String sql = "SELECT * FROM country where Code = 'ABW'";
			rs = stmt.executeQuery(sql);

			// 7.1
			System.out.println("更新前===================");
			if(rs.next()) {
				String name = rs.getString("Name");
				int population = rs.getInt("Population");
				System.out.println(name + "\n" + population);
			}

			// 7.2
			System.out.println("更新処理実行=============");
			String updateSql = "update country set Population = 105000 where Code = 'ABW'";
			int count = stmt.executeUpdate(updateSql);
			System.out.println("更新行数：" + count);

			//7.3
			rs.close();
			System.out.println("更新後=================");
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				String name = rs.getString("Name");
				int population = rs.getInt("Population");
				System.out.println(name + "\n" + population);
			}
			while(rs.next()) {
				String name = rs.getString("Name");
				int population = rs.getInt("Population");
				System.out.println(name);
				System.out.println(population);


			}
		} catch (ClassNotFoundException e) {
			System.err.println("JDBCのドライバーのロードに失敗しました。");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("データベースに異常が発生しました。");
			e.printStackTrace();
		}finally{

			// 8.接続を閉じる
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException e) {
					System.err.println("ResultSetを閉じるときにエラーが発生しました。");
					e.printStackTrace();
				}
			}
			if(stmt != null) {
				try {
					stmt.close();
				}catch(SQLException e) {
					System.err.println("Statementを閉じるときにエラーが発生しました。");
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(SQLException e) {
					System.err.println("データベース切断時にエラーが発生しました。");
					e.printStackTrace();
				}
			}
		}


	}

}
