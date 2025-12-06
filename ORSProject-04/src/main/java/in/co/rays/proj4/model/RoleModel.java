package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * Model class that handles CRUD operations for Role entity.
 * Provides methods to add, update, delete, search, and list Role records
 * from the database. Supports duplicate checks by role name.
 * 
 * @author krati
 */
public class RoleModel {

    /**
     * Retrieves the next primary key for the role table.
     * 
     * @return next primary key
     * @throws DatabaseException if a database error occurs
     */
    public Integer nextPk() throws DatabaseException {
        int pk = 0;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_role");
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
     * Adds a new role to the database.
     * Checks for duplicate role name before insertion.
     * 
     * @param bean the RoleBean containing role details
     * @return primary key of the newly inserted role
     * @throws ApplicationException if a database error occurs
     * @throws DuplicateRecordException if a role with the same name exists
     * @throws SQLException if SQL error occurs
     */
    public long add(RoleBean bean) throws ApplicationException, DuplicateRecordException, SQLException {
        Connection conn = null;
        int pk = 0;

        RoleBean existBean = findByName(bean.getName());
        if (existBean != null) {
            throw new DuplicateRecordException("Role already exists");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("insert into st_role values(?, ?, ?, ?, ?, ?, ?)");
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
            try { conn.rollback(); } catch (Exception ex) {
                throw new ApplicationException("Add rollback exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in adding Role");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    /**
     * Updates an existing role in the database.
     * Checks for duplicate role name with another record.
     * 
     * @param bean the RoleBean containing updated role details
     * @throws ApplicationException if a database error occurs
     * @throws DuplicateRecordException if another role has the same name
     */
    public void update(RoleBean bean) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;

        RoleBean existBean = findByName(bean.getName());
        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Role already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                "update st_role set name = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?"
            );
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
            try { conn.rollback(); } catch (Exception ex) {
                throw new ApplicationException("Update rollback exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Role");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes a role from the database.
     * 
     * @param bean the RoleBean to delete
     * @throws ApplicationException if a database error occurs
     */
    public void delete(RoleBean bean) throws ApplicationException {
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("delete from st_role where id = ?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {
                throw new ApplicationException("Delete rollback exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in deleting Role");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds a role by name.
     * 
     * @param name the role name
     * @return RoleBean if found, else null
     * @throws ApplicationException if a database error occurs
     */
    public RoleBean findByName(String name) throws ApplicationException {
        String sql = "select * from st_role where name = ?";
        RoleBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new RoleBean();
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
            throw new ApplicationException("Exception in getting Role by name");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * Finds a role by primary key (ID).
     * 
     * @param pk the role ID
     * @return RoleBean if found, else null
     * @throws ApplicationException if a database error occurs
     */
    public RoleBean findByPk(long pk) throws ApplicationException {
        String sql = "select * from st_role where id = ?";
        RoleBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new RoleBean();
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
            throw new ApplicationException("Exception in getting Role by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * Returns a list of all roles.
     * 
     * @return list of RoleBean
     * @throws ApplicationException if a database error occurs
     */
    public List<RoleBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches for roles dynamically with optional filters and pagination.
     * 
     * @param bean RoleBean containing search criteria
     * @param pageNo page number (starting from 1)
     * @param pageSize number of records per page
     * @return list of RoleBean matching search criteria
     * @throws ApplicationException if a database error occurs
     */
    public List<RoleBean> search(RoleBean bean, int pageNo, int pageSize) throws ApplicationException {
        StringBuffer sql = new StringBuffer("select * from st_role where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) sql.append(" and id = " + bean.getId());
            if (bean.getName() != null && bean.getName().length() > 0) sql.append(" and name like '" + bean.getName() + "%'");
            if (bean.getDescription() != null && bean.getDescription().length() > 0) sql.append(" and description like '" + bean.getDescription() + "%'");
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        Connection conn = null;
        ArrayList<RoleBean> list = new ArrayList();

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new RoleBean();
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
            throw new ApplicationException("Exception in searching Role");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}
