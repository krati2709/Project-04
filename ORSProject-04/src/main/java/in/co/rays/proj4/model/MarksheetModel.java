package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * Model class that handles CRUD operations for Marksheet entity.
 * Provides methods to add, update, delete, search, and retrieve
 * Marksheet records from the database. Also supports merit list generation.
 * 
 * @author krati
 */
public class MarksheetModel {

    /**
     * Retrieves the next primary key for the marksheet table.
     * 
     * @return the next primary key value
     * @throws DatabaseException if a database error occurs
     */
    public Integer nextPk() throws DatabaseException {
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_marksheet");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pk = rs.getInt(1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception in Marksheet getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /**
     * Adds a new Marksheet record to the database.
     * Sets student name based on studentId and checks for duplicate roll number.
     * 
     * @param bean the MarksheetBean containing marksheet details
     * @return the primary key of the newly inserted marksheet
     * @throws ApplicationException if a database error occurs
     * @throws DuplicateRecordException if a marksheet with the same roll number exists
     */
    public long add(MarksheetBean bean) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;
        int pk = 0;

        StudentModel studentModel = new StudentModel();
        StudentBean studentbean = studentModel.findByPk(bean.getStudentId());
        bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

        MarksheetBean duplicateMarksheet = findByRollNo(bean.getRollNo());
        if (duplicateMarksheet != null) {
            throw new DuplicateRecordException("Roll Number already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPk();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn
                    .prepareStatement("insert into st_marksheet values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getRollNo());
            pstmt.setLong(3, bean.getStudentId());
            pstmt.setString(4, bean.getName());
            pstmt.setInt(5, bean.getPhysics());
            pstmt.setInt(6, bean.getChemistry());
            pstmt.setInt(7, bean.getMaths());
            pstmt.setString(8, bean.getCreatedBy());
            pstmt.setString(9, bean.getModifiedBy());
            pstmt.setTimestamp(10, bean.getCreatedDatetime());
            pstmt.setTimestamp(11, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in add marksheet");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates an existing Marksheet record in the database.
     * Updates student name based on studentId and checks duplicate roll number.
     * 
     * @param bean the MarksheetBean containing updated marksheet details
     * @throws ApplicationException if a database error occurs
     * @throws DuplicateRecordException if another marksheet has the same roll number
     */
    public void update(MarksheetBean bean) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;

        MarksheetBean beanExist = findByRollNo(bean.getRollNo());
        if (beanExist != null && beanExist.getId() != bean.getId()) {
            throw new DuplicateRecordException("Roll No is already exist");
        }

        StudentModel studentModel = new StudentModel();
        StudentBean studentbean = studentModel.findByPk(bean.getStudentId());
        bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_marksheet set roll_no = ?, student_id = ?, name = ?, physics = ?, chemistry = ?, maths = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

            pstmt.setString(1, bean.getRollNo());
            pstmt.setLong(2, bean.getStudentId());
            pstmt.setString(3, bean.getName());
            pstmt.setInt(4, bean.getPhysics());
            pstmt.setInt(5, bean.getChemistry());
            pstmt.setInt(6, bean.getMaths());
            pstmt.setString(7, bean.getCreatedBy());
            pstmt.setString(8, bean.getModifiedBy());
            pstmt.setTimestamp(9, bean.getCreatedDatetime());
            pstmt.setTimestamp(10, bean.getModifiedDatetime());
            pstmt.setLong(11, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Update rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Marksheet ");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes a Marksheet record from the database.
     * 
     * @param bean the MarksheetBean to delete
     * @throws ApplicationException if a database error occurs
     */
    public void delete(MarksheetBean bean) throws ApplicationException {
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_marksheet where id = ?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in delete marksheet");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds a Marksheet by primary key.
     * 
     * @param pk the primary key of the marksheet
     * @return the MarksheetBean if found, else null
     * @throws ApplicationException if a database error occurs
     */
    public MarksheetBean findByPk(long pk) throws ApplicationException {
        StringBuffer sql = new StringBuffer("select * from st_marksheet where id = ?");
        MarksheetBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new MarksheetBean();
                bean.setId(rs.getLong(1));
                bean.setRollNo(rs.getString(2));
                bean.setStudentId(rs.getLong(3));
                bean.setName(rs.getString(4));
                bean.setPhysics(rs.getInt(5));
                bean.setChemistry(rs.getInt(6));
                bean.setMaths(rs.getInt(7));
                bean.setCreatedBy(rs.getString(8));
                bean.setModifiedBy(rs.getString(9));
                bean.setCreatedDatetime(rs.getTimestamp(10));
                bean.setModifiedDatetime(rs.getTimestamp(11));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in getting marksheet by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds a Marksheet by roll number.
     * 
     * @param rollNo the roll number of the marksheet
     * @return the MarksheetBean if found, else null
     * @throws ApplicationException if a database error occurs
     */
    public MarksheetBean findByRollNo(String rollNo) throws ApplicationException {
        StringBuffer sql = new StringBuffer("select * from st_marksheet where roll_no = ?");
        MarksheetBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, rollNo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new MarksheetBean();
                bean.setId(rs.getLong(1));
                bean.setRollNo(rs.getString(2));
                bean.setStudentId(rs.getLong(3));
                bean.setName(rs.getString(4));
                bean.setPhysics(rs.getInt(5));
                bean.setChemistry(rs.getInt(6));
                bean.setMaths(rs.getInt(7));
                bean.setCreatedBy(rs.getString(8));
                bean.setModifiedBy(rs.getString(9));
                bean.setCreatedDatetime(rs.getTimestamp(10));
                bean.setModifiedDatetime(rs.getTimestamp(11));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting marksheet by roll no");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * Searches for Marksheet records based on filters and supports pagination.
     * 
     * @param bean the MarksheetBean containing search criteria
     * @param pageNo the page number for pagination (starting from 1)
     * @param pageSize the number of records per page
     * @return a list of MarksheetBean matching the criteria
     * @throws ApplicationException if a database error occurs
     */
    public List<MarksheetBean> search(MarksheetBean bean, int pageNo, int pageSize) throws ApplicationException {
        StringBuffer sql = new StringBuffer("select * from st_marksheet where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) sql.append(" and id = " + bean.getId());
            if (bean.getRollNo() != null && bean.getRollNo().length() > 0)
                sql.append(" and roll_no like '" + bean.getRollNo() + "%'");
            if (bean.getName() != null && bean.getName().length() > 0)
                sql.append(" and name like '" + bean.getName() + "%'");
            if (bean.getPhysics() != null && bean.getPhysics() > 0)
                sql.append(" and physics = " + bean.getPhysics());
            if (bean.getChemistry() != null && bean.getChemistry() > 0)
                sql.append(" and chemistry = " + bean.getChemistry());
            if (bean.getMaths() != null && bean.getMaths() > 0)
                sql.append(" and maths = " + bean.getMaths());
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new MarksheetBean();
                bean.setId(rs.getLong(1));
                bean.setRollNo(rs.getString(2));
                bean.setStudentId(rs.getLong(3));
                bean.setName(rs.getString(4));
                bean.setPhysics(rs.getInt(5));
                bean.setChemistry(rs.getInt(6));
                bean.setMaths(rs.getInt(7));
                bean.setCreatedBy(rs.getString(8));
                bean.setModifiedBy(rs.getString(9));
                bean.setCreatedDatetime(rs.getTimestamp(10));
                bean.setModifiedDatetime(rs.getTimestamp(11));
                list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in searching marksheet " + e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }

    /**
     * Retrieves merit list of students based on total marks (students passing all subjects).
     * 
     * @param pageNo the page number for pagination (starting from 1)
     * @param pageSize the number of records per page
     * @return a list of top-performing MarksheetBean
     * @throws ApplicationException if a database error occurs
     */
    public List<MarksheetBean> getMeritList(int pageNo, int pageSize) throws ApplicationException {
        ArrayList<MarksheetBean> list = new ArrayList<MarksheetBean>();
        StringBuffer sql = new StringBuffer(
                "select id, roll_no, name, physics, chemistry, maths, (physics + chemistry + maths) as total from st_marksheet where physics > 33 and chemistry > 33 and maths > 33 order by total desc");

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                MarksheetBean bean = new MarksheetBean();
                bean.setId(rs.getLong(1));
                bean.setRollNo(rs.getString(2));
                bean.setName(rs.getString(3));
                bean.setPhysics(rs.getInt(4));
                bean.setChemistry(rs.getInt(5));
                bean.setMaths(rs.getInt(6));
                list.add(bean);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in getting merit list of Marksheet");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }
}
