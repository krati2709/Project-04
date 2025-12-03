package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.DepartmentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * Model class that handles CRUD operations for the Department entity.
 * Provides methods to add, update, delete, search, and list departments
 * from the database.
 * 
 * @author krati
 */
public class DepartmentModel {

    /**
     * Retrieves the next primary key for the Department table.
     * 
     * @return the next primary key value
     * @throws DatabaseException if there is a database access error
     */
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_department");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /**
     * Adds a new Department record to the database.
     * 
     * @param bean the DepartmentBean containing department details
     * @throws DuplicateRecordException if a department with the same name already exists
     * @throws ApplicationException if there is a database access error
     */
    public void add(DepartmentBean bean) throws DuplicateRecordException, ApplicationException {

        Connection conn = null;
        int pk = 0;

        DepartmentBean existbean = findByName(bean.getName());

        if (existbean != null) {
            throw new DuplicateRecordException("department already exists");

        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("insert into st_department values(?, ?, ?, ?, ?, ?, ?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getName());
            pstmt.setString(3, bean.getDescription());
            pstmt.setString(4, bean.getCreatedBy());
            pstmt.setString(5, bean.getModifiedBy());
            pstmt.setTimestamp(6, bean.getCreatedDatetime());
            pstmt.setTimestamp(7, bean.getModifiedDatetime());

            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new ApplicationException("Exception : add rollback exception " + e1.getMessage());
            }
            throw new ApplicationException("Exception : Exception in add User");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

    }

    /**
     * Updates an existing Department record in the database.
     * 
     * @param bean the DepartmentBean containing updated department details
     * @throws DuplicateRecordException if a department with the same name already exists
     * @throws ApplicationException if there is a database access error
     */
    public void update(DepartmentBean bean) throws DuplicateRecordException, ApplicationException {

        Connection conn = null;

        DepartmentBean beanExist = findByName(bean.getName());

        if (beanExist != null && !(beanExist.getId() == bean.getId())) {
            throw new DuplicateRecordException("name is already exist");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_department set name = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getDescription());
            pstmt.setString(3, bean.getCreatedBy());
            pstmt.setString(4, bean.getModifiedBy());
            pstmt.setTimestamp(5, bean.getCreatedDatetime());
            pstmt.setTimestamp(6, bean.getModifiedDatetime());
            pstmt.setLong(7, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating User ");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes a Department record from the database.
     * 
     * @param bean the DepartmentBean containing the department to delete
     * @throws ApplicationException if there is a database access error
     */
    public void delete(DepartmentBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_department where id = ?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();

            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }

            throw new ApplicationException("Exception : Exception in delete User");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds a Department by its primary key.
     * 
     * @param pk the primary key of the department
     * @return the DepartmentBean if found, otherwise null
     * @throws ApplicationException if there is a database access error
     */
    public DepartmentBean findByPk(long pk) throws ApplicationException {

        DepartmentBean bean = null;
        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_department where id = ?");

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new DepartmentBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setCreatedBy(rs.getString(4));
                bean.setModifiedBy(rs.getString(5));
                bean.setCreatedDatetime(rs.getTimestamp(6));
                bean.setModifiedDatetime(rs.getTimestamp(7));
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in getting User by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds a Department by its name.
     * 
     * @param name the name of the department
     * @return the DepartmentBean if found, otherwise null
     * @throws ApplicationException if there is a database access error
     */
    public DepartmentBean findByName(String name) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_department where name = ?");

        DepartmentBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new DepartmentBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setCreatedBy(rs.getString(4));
                bean.setModifiedBy(rs.getString(5));
                bean.setCreatedDatetime(rs.getTimestamp(6));
                bean.setModifiedDatetime(rs.getTimestamp(7));
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in getting User by login");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Retrieves a list of all departments.
     * 
     * @return a list of DepartmentBean objects
     * @throws ApplicationException if there is a database access error
     */
    public List<DepartmentBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches for departments with optional filters and pagination.
     * 
     * @param bean the DepartmentBean containing filter criteria (optional)
     * @param pageNo the page number for pagination (starting from 1)
     * @param pageSize the number of records per page
     * @return a list of DepartmentBean objects matching the criteria
     * @throws ApplicationException if there is a database access error
     */
    public List<DepartmentBean> search(DepartmentBean bean, int pageNo, int pageSize) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_department where 1=1");

        // Dynamic filters
        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }
            if (bean.getName() != null && bean.getName().length() > 0) {
                sql.append(" and name like '" + bean.getName() + "%'");
            }
            if (bean.getDescription() != null && bean.getDescription().length() > 0) {
                sql.append(" and description like '" + bean.getDescription() + "%'");
            }
        }

        // Pagination
        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        Connection conn = null;
        ArrayList<DepartmentBean> list = new ArrayList<DepartmentBean>();

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new DepartmentBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setCreatedBy(rs.getString(4));
                bean.setModifiedBy(rs.getString(5));
                bean.setCreatedDatetime(rs.getTimestamp(6));
                bean.setModifiedDatetime(rs.getTimestamp(7));
                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in search department");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}
