package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * Model class for College entity.
 * <p>
 * Provides CRUD operations along with search and pagination support for
 * {@link CollegeBean}. Handles manual primary key generation, duplicate
 * validation, and database transaction management.
 * </p>
 * 
 * @author Krati
 */
public class CollegeModel {

    /**
     * Generates the next primary key for the st_college table.
     * 
     * @return next primary key as Integer
     * @throws DatabaseException if database access fails
     */
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_college");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pk = rs.getInt(1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception: Error getting primary key");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /**
     * Adds a new college record.
     * <p>
     * Steps performed:
     * <ul>
     * <li>Checks for duplicate college name</li>
     * <li>Generates the next primary key</li>
     * <li>Inserts the record into st_college</li>
     * <li>Commits transaction</li>
     * </ul>
     * 
     * @param bean CollegeBean object to be added
     * @return generated primary key
     * @throws ApplicationException       for database errors
     * @throws DuplicateRecordException   if college name already exists
     */
    public long add(CollegeBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        CollegeBean duplicateCollegeName = findByName(bean.getName());

        if (duplicateCollegeName != null) {
            throw new DuplicateRecordException("College name already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPk();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_college values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getName());
            pstmt.setString(3, bean.getAddress());
            pstmt.setString(4, bean.getState());
            pstmt.setString(5, bean.getCity());
            pstmt.setString(6, bean.getPhoneNo());
            pstmt.setString(7, bean.getCreatedBy());
            pstmt.setString(8, bean.getModifiedBy());
            pstmt.setTimestamp(9, bean.getCreatedDatetime());
            pstmt.setTimestamp(10, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception: Rollback failed");
            }
            throw new ApplicationException("Exception: Error adding College");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates an existing college record.
     * <p>
     * Ensures the college name is not duplicated for other records.
     * </p>
     * 
     * @param bean CollegeBean containing updated information
     * @throws ApplicationException       on DB failure
     * @throws DuplicateRecordException   if duplicate college name found
     */
    public void update(CollegeBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        CollegeBean beanExist = findByName(bean.getName());

        if (beanExist != null && beanExist.getId() != bean.getId()) {
            throw new DuplicateRecordException("College already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_college set name = ?, address = ?, state = ?, city = ?, phone_no = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getAddress());
            pstmt.setString(3, bean.getState());
            pstmt.setString(4, bean.getCity());
            pstmt.setString(5, bean.getPhoneNo());
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreatedDatetime());
            pstmt.setTimestamp(9, bean.getModifiedDatetime());
            pstmt.setLong(10, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception: Rollback failed");
            }
            throw new ApplicationException("Exception updating College");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes a college record.
     * 
     * @param bean CollegeBean containing the ID to delete
     * @throws ApplicationException if delete operation fails
     */
    public void delete(CollegeBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("delete from st_college where id = ?");
            pstmt.setLong(1, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception: Rollback failed");
            }

            throw new ApplicationException("Exception deleting College");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds a college by its primary key.
     * 
     * @param pk primary key of the college
     * @return CollegeBean or null if not found
     * @throws ApplicationException if DB error occurs
     */
    public CollegeBean findByPk(long pk) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_college where id = ?");
        CollegeBean bean = null;

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new CollegeBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setAddress(rs.getString(3));
                bean.setState(rs.getString(4));
                bean.setCity(rs.getString(5));
                bean.setPhoneNo(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifiedDatetime(rs.getTimestamp(10));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception getting College by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * Finds a college by its name.
     * <p>
     * Useful for checking duplicate records.
     * </p>
     * 
     * @param name college name
     * @return CollegeBean or null if not found
     * @throws ApplicationException on DB failure
     */
    public CollegeBean findByName(String name) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_college where name = ?");
        CollegeBean bean = null;

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new CollegeBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setAddress(rs.getString(3));
                bean.setState(rs.getString(4));
                bean.setCity(rs.getString(5));
                bean.setPhoneNo(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifiedDatetime(rs.getTimestamp(10));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception getting College by name");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * Returns list of all colleges.
     * 
     * @return list of CollegeBean
     * @throws ApplicationException if DB error occurs
     */
    public List<CollegeBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches colleges based on filter criteria.
     * <p>
     * Supports:
     * <ul>
     * <li>Dynamic conditions (ID, Name, Address, State, etc.)</li>
     * <li>Pagination using pageNo and pageSize</li>
     * </ul>
     * 
     * @param bean     search filter
     * @param pageNo   page number
     * @param pageSize number of records per page
     * @return list of CollegeBeans
     * @throws ApplicationException if DB operation fails
     */
    public List<CollegeBean> search(CollegeBean bean, int pageNo, int pageSize) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_college where 1 = 1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }
            if (bean.getName() != null && bean.getName().length() > 0) {
                sql.append(" and name like '" + bean.getName() + "%'");
            }
            if (bean.getAddress() != null && bean.getAddress().length() > 0) {
                sql.append(" and address like '" + bean.getAddress() + "%'");
            }
            if (bean.getState() != null && bean.getState().length() > 0) {
                sql.append(" and state like '" + bean.getState() + "%'");
            }
            if (bean.getCity() != null && bean.getCity().length() > 0) {
                sql.append(" and city like '" + bean.getCity() + "%'");
            }
            if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
                sql.append(" and phone_no = " + bean.getPhoneNo());
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<CollegeBean> list = new ArrayList<CollegeBean>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new CollegeBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setAddress(rs.getString(3));
                bean.setState(rs.getString(4));
                bean.setCity(rs.getString(5));
                bean.setPhoneNo(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifiedDatetime(rs.getTimestamp(10));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception searching College");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}
