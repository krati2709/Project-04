package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * StudentModel provides CRUD operations for Student entity.
 * Supports add, update, delete, find, list, and search operations.
 * Includes duplicate check for email and manages CollegeName from CollegeId.
 * 
 * @author krati
 */
public class StudentModel {

    /**
     * Returns next primary key of student table.
     * 
     * @return next primary key
     * @throws DatabaseException if database error occurs
     */
    public Integer nextPk() throws DatabaseException {
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_student");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
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
     * Adds a new student to database.
     * Checks for duplicate email and sets college name from CollegeId.
     * 
     * @param bean StudentBean containing student info
     * @return primary key of inserted student
     * @throws ApplicationException if database error occurs
     * @throws DuplicateRecordException if email already exists
     */
    public long add(StudentBean bean) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;
        int pk = 0;

        // Set College Name
        CollegeModel collegeModel = new CollegeModel();
        CollegeBean collegeBean = collegeModel.findByPk(bean.getCollegeId());
        bean.setCollegeName(collegeBean.getName());

        // Check duplicate email
        StudentBean existBean = findByEmailId(bean.getEmail());
        if (existBean != null) {
            throw new DuplicateRecordException("Email already exists");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                "insert into st_student values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getFirstName());
            pstmt.setString(3, bean.getLastName());
            pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setString(5, bean.getGender());
            pstmt.setString(6, bean.getMobileNo());
            pstmt.setString(7, bean.getEmail());
            pstmt.setLong(8, bean.getCollegeId());
            pstmt.setString(9, bean.getCollegeName());
            pstmt.setString(10, bean.getCreatedBy());
            pstmt.setString(11, bean.getModifiedBy());
            pstmt.setTimestamp(12, bean.getCreatedDatetime());
            pstmt.setTimestamp(13, bean.getModifiedDatetime());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {
                throw new ApplicationException("Add rollback exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in adding Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates an existing student in database.
     * Checks for duplicate email with other records.
     * 
     * @param bean StudentBean containing updated info
     * @throws ApplicationException if database error occurs
     * @throws DuplicateRecordException if email is already used by another student
     */
    public void update(StudentBean bean) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;

        // Check duplicate email
        StudentBean existBean = findByEmailId(bean.getEmail());
        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Email already exists");
        }

        // Set College Name
        CollegeModel collegeModel = new CollegeModel();
        CollegeBean collegeBean = collegeModel.findByPk(bean.getCollegeId());
        bean.setCollegeName(collegeBean.getName());

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                "update st_student set first_name = ?, last_name = ?, dob = ?, gender = ?, mobile_no = ?, email = ?, college_id = ?, college_name = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?"
            );
            pstmt.setString(1, bean.getFirstName());
            pstmt.setString(2, bean.getLastName());
            pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
            pstmt.setString(4, bean.getGender());
            pstmt.setString(5, bean.getMobileNo());
            pstmt.setString(6, bean.getEmail());
            pstmt.setLong(7, bean.getCollegeId());
            pstmt.setString(8, bean.getCollegeName());
            pstmt.setString(9, bean.getCreatedBy());
            pstmt.setString(10, bean.getModifiedBy());
            pstmt.setTimestamp(11, bean.getCreatedDatetime());
            pstmt.setTimestamp(12, bean.getModifiedDatetime());
            pstmt.setLong(13, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {
                throw new ApplicationException("Update rollback exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes a student from database.
     * 
     * @param bean StudentBean to delete
     * @throws ApplicationException if database error occurs
     */
    public void delete(StudentBean bean) throws ApplicationException {
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_student where id = ?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {
                throw new ApplicationException("Delete rollback exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in deleting Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds a student by primary key.
     * 
     * @param pk student ID
     * @return StudentBean if found, else null
     * @throws ApplicationException if database error occurs
     */
    public StudentBean findByPk(long pk) throws ApplicationException {
        String sql = "select * from st_student where id = ?";
        StudentBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                bean = mapResultSetToStudent(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Student by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds a student by email.
     * 
     * @param email student's email
     * @return StudentBean if found, else null
     * @throws ApplicationException if database error occurs
     */
    public StudentBean findByEmailId(String email) throws ApplicationException {
        String sql = "select * from st_student where email = ?";
        StudentBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                bean = mapResultSetToStudent(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Student by Email");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Returns list of all students.
     * 
     * @return list of StudentBean
     * @throws ApplicationException if database error occurs
     */
    public List<StudentBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches students with optional filters and pagination.
     * 
     * @param bean StudentBean containing search criteria
     * @param pageNo page number (starting from 1)
     * @param pageSize number of records per page
     * @return list of StudentBean
     * @throws ApplicationException if database error occurs
     */
    public List<StudentBean> search(StudentBean bean, int pageNo, int pageSize) throws ApplicationException {
        StringBuilder sql = new StringBuilder("select * from st_student where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) sql.append(" and id = " + bean.getId());
            if (bean.getFirstName() != null && !bean.getFirstName().isEmpty())
                sql.append(" and first_name like '" + bean.getFirstName() + "%'");
            if (bean.getLastName() != null && !bean.getLastName().isEmpty())
                sql.append(" and last_name like '" + bean.getLastName() + "%'");
            if (bean.getDob() != null) sql.append(" and dob = '" + new java.sql.Date(bean.getDob().getTime()) + "'");
            if (bean.getGender() != null && !bean.getGender().isEmpty())
                sql.append(" and gender like '" + bean.getGender() + "%'");
            if (bean.getMobileNo() != null && !bean.getMobileNo().isEmpty())
                sql.append(" and mobile_no like '" + bean.getMobileNo() + "%'");
            if (bean.getEmail() != null && !bean.getEmail().isEmpty())
                sql.append(" and email like '" + bean.getEmail() + "%'");
            if (bean.getCollegeName() != null && !bean.getCollegeName().isEmpty())
                sql.append(" and college_name = '" + bean.getCollegeName() + "'");
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<StudentBean> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToStudent(rs));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in searching Student");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }

    /**
     * Helper method to map ResultSet row to StudentBean
     */
    private StudentBean mapResultSetToStudent(ResultSet rs) throws Exception {
        StudentBean bean = new StudentBean();
        bean.setId(rs.getLong("id"));
        bean.setFirstName(rs.getString("first_name"));
        bean.setLastName(rs.getString("last_name"));
        bean.setDob(rs.getDate("dob"));
        bean.setGender(rs.getString("gender"));
        bean.setMobileNo(rs.getString("mobile_no"));
        bean.setEmail(rs.getString("email"));
        bean.setCollegeId(rs.getLong("college_id"));
        bean.setCollegeName(rs.getString("college_name"));
        bean.setCreatedBy(rs.getString("created_by"));
        bean.setModifiedBy(rs.getString("modified_by"));
        bean.setCreatedDatetime(rs.getTimestamp("created_datetime"));
        bean.setModifiedDatetime(rs.getTimestamp("modified_datetime"));
        return bean;
    }
}
