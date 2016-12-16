import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOImpl implements DAO {
	private String username = "root";
	private String password = "mohamed1995";
	private Connection conn;

	public DAOImpl() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/product",username, password);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void insertProduct (Product product) throws SQLException, DAOException{
		try{
			PreparedStatement psmt = conn.prepareStatement("insert into product values(?,?,?,?,?)");
			psmt.setInt(1, product.getId());
			psmt.setString(2, product.getType());
			psmt.setString(3, product.getManufacturer());
			psmt.setString(4, product.getProductionDate());
			psmt.setString(5, product.getExpiryDate());
			psmt.executeUpdate();
		}
		catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	public Product getProduct(int ID) throws SQLException, DAOException
	{
		Product product = null;
		try
		{
			conn = DriverManager.getConnection("jdbc:mysql://localhost/product",username, password);
			PreparedStatement psmt = conn.prepareStatement("select  Type , Production , Expiry_date, Manufacturer from product where Product_ID = ? ");

			psmt.setInt(1, ID);
			ResultSet res =  psmt.executeQuery();
			while (res.next()) {
				product = new Product(ID);
				product.setType(res.getString("Type"));
				product.setManufacturer(res.getString("manufacturer"));
				product.setProductionDate(res.getString("production"));
				product.setExpiryDate(res.getString("Expiry_Date"));
			}
		}
		catch (SQLException e) {
			throw new DAOException(e);
		}

		return product;
	}
	public void updateProduct (Product product) throws SQLException, DAOException{
		try{
			PreparedStatement psmt = conn.prepareStatement("UPDATE `store`.`product` SET `Type` = ?, `Manufacturer` = ?, `Production_Date` = ?, `Expiry_Date` = ? WHERE `product`.`Product_ID` = ?;");
			psmt.setString(1, product.getType());
			psmt.setString(2, product.getManufacturer());
			psmt.setString(3, product.getProductionDate());
			psmt.setString(4, product.getExpiryDate());
			psmt.setInt(5, product.getId());
			psmt.executeUpdate();
		}
		catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	public void deleteProduct (int id) throws SQLException, DAOException{
		try{
			PreparedStatement psmt = conn.prepareStatement("delete from product where Product_ID =?");
			psmt.setInt(1,id);
			psmt.executeUpdate();
		}
		catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}
