package in.co.rays.proj4.model;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.proj4.bean.HostelBean;
import in.co.rays.proj4.bean.LibraryBean;

public class TestLibrary {
	
	public static void main(String[] args) throws Exception {
		testAdd();
		
	}
	
	public static void testAdd() throws Exception {

		LibraryModel model = new LibraryModel();
		LibraryBean bean = new LibraryBean();

		bean.setTitle("jksef");;
		bean.setAuthor("hsdbfh");;
		bean.setIsbn("8475848375");;
		bean.setCategory("asjuhdf");
		bean.setPublisher("sdhakls");
		bean.setPublishedYear(2002);
		bean.setQuantity(10);
		bean.setDescription("dsdgdf");
		
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.add(bean);
		System.out.println("Data Added Successfully");

	}


}
