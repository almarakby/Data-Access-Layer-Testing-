import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class Utest {

	@InjectMocks
	DAOImpl testDao = new DAOImpl();

	@Mock
	Connection testConnection;

	@Mock
	PreparedStatement testPreparedStatement;

	
	@SuppressWarnings("deprecation")
	@Test
	public void ShouldAssertTrueWhenClassInstantiated(){
		Product testProduct = new Product(11111111);
		int test = testProduct.getId();
		Assert.assertTrue(Integer.toString(test).equals("11111111"));
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void ShouldAssertTrueWhenTypeSetterCalled(){
		Product testProduct = new Product(11111111);
		testProduct.setType("mri");
		Assert.assertTrue(testProduct.getType().equals("mri"));
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void ShouldAssertTrueWhenManufacturerSetterCalled(){
		Product testProduct = new Product(11111111);
		testProduct.setManufacturer("GE");
		Assert.assertTrue(testProduct.getManufacturer().equals("GE"));
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void ShouldAssertTrueWhenProductionDateSetterCalled(){
		Product testProduct = new Product(11111111);
		testProduct.setProductionDate("5102010");
		Assert.assertTrue(testProduct.getProductionDate().equals("5102010"));
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void ShouldAssertTrueWhenExpiryDateSetterCalled(){
		Product testProduct = new Product(11111111);
		testProduct.setExpiryDate("5102020");
		Assert.assertTrue(testProduct.getExpiryDate().equals("5102020"));
	}
	
	@Test (expected = DAOException.class)
	public void ShouldThrowsExceptionwhenInsertCalled() throws SQLException,DAOException{
		when(testConnection.prepareStatement(anyString())).thenReturn(testPreparedStatement);
		when(testPreparedStatement.executeUpdate()).thenThrow(new SQLException());
		Product testProduct = new Product(11111111);
		testDao.insertProduct(testProduct);
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void ShouldAssertTrueWhenmentionedCapturedValueisTrue() throws SQLException, DAOException{
		when(testConnection.prepareStatement(anyString())).thenReturn(testPreparedStatement);
		ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		Product testProduct = new Product(11111111);
		testDao.insertProduct(testProduct);
		verify(testPreparedStatement,times(1)).setInt(anyInt(),intCaptor.capture());
		Assert.assertTrue(intCaptor.getAllValues().get(0).equals(11111111));
	}

	@Test
	@SuppressWarnings("deprecation")
	public void ShouldAssertNullIfProductDeleted() throws SQLException, DAOException{
		Product testProduct = new Product(12121212);
		Product testRretirevedProduct ;
		DAOImpl testDao = new DAOImpl();
		testProduct.setManufacturer("samsung");
		testProduct.setExpiryDate("12122012");
		testProduct.setProductionDate("12121912");
		testProduct.setType("mri");
		testDao.insertProduct(testProduct);
		testRretirevedProduct = testDao.getProduct(12121212);
		Assert.assertTrue(testRretirevedProduct.getExpiryDate().equals("12122012"));
		Assert.assertTrue(testRretirevedProduct.getProductionDate().equals("12121912"));
		testDao.deleteProduct(12121212);
		testRretirevedProduct = testDao.getProduct(12121212);
		Assert.assertNull(testRretirevedProduct);
	}
}
