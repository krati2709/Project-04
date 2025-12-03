package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * SubjectModel provides CRUD operations for Subject entity.
 * Includes methods for add, update, delete, findByPk, findByName, list, and search.
 * Sets CourseName automatically from CourseId when adding or updating a subject.
 * 
 * @author krati
 */
public class SubjectModel {

    /**
     * Returns next primary key of Subject table.
     */
    public Integer nextPk() throws DatabaseException {
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_subject");
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
     * Adds a new subject.
     * Checks for duplicate subject name.
     */
    public long add(SubjectBean bean) throws ApplicationException, DuplicateRecordException {
        Connection conn = null;
        int pk = 0;

        // Set CourseName
        CourseModel courseModel = new CourseModel();
        CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
        bean.setCourseName(courseBean.getName());

        // Check for duplicate subject
        SubjectBean duplicateSubject = findByName(bean.getName());
        if (duplicateSubject != null) {
            throw new DuplicateRecordException("Subject Name already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPk();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_subject values(?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getName());
            pstmt.setLong(3, bean.getCourseId());
            pstmt.setString(4, bean.getCourseName());
            pstmt.setString(5, bean.getDescription());
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreatedDatetime());
            pstmt.setTimestamp(9, bean.getModifiedDatetime());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {
                throw new ApplicationException("Add rollback exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in adding Subject");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates an existing subject.
     * Sets CourseName automatically from CourseId.
     */
    public void update(SubjectBean bean) throws ApplicationException {
        Connection conn = null;

        // Set CourseName
        CourseModel courseModel = new CourseModel();
        CourseBean courseBean = courseModel.findByPk(bean.getCourseId());
        bean.setCourseName(courseBean.getName());

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(
                "update st_subject set name = ?, course_id = ?, course_name = ?, description = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?"
            );
            pstmt.setString(1, bean.getName());
            pstmt.setLong(2, bean.getCourseId());
            pstmt.setString(3, bean.getCourseName());
            pstmt.setString(4, bean.getDescription());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setTimestamp(7, bean.getCreatedDatetime());
            pstmt.setTimestamp(8, bean.getModifiedDatetime());
            pstmt.setLong(9, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {
                throw new ApplicationException("Update rollback exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Subject");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes a subject by ID.
     */
    public void delete(SubjectBean bean) throws ApplicationException {
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("delete from st_subject where id = ?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (Exception e) {
            try { conn.rollback(); } catch (Exception ex) {
                throw new ApplicationException("Delete rollback exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in deleting Subject");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds subject by primary key.
     */
    public SubjectBean findByPk(long pk) throws ApplicationException {
        String sql = "select * from st_subject where id = ?";
        SubjectBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                bean = mapResultSetToSubject(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Subject by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds subject by name.
     */
    public SubjectBean findByName(String name) throws ApplicationException {
        String sql = "select * from st_subject where name = ?";
        SubjectBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                bean = mapResultSetToSubject(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Subject by Name");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Returns list of all subjects.
     */
    public List<SubjectBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Searches subjects with optional filters and pagination.
     */
    public List<SubjectBean> search(SubjectBean bean, int pageNo, int pageSize) throws ApplicationException {
        StringBuilder sql = new StringBuilder("select * from st_subject where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) sql.append(" and id = " + bean.getId());
            if (bean.getName() != null && !bean.getName().isEmpty())
                sql.append(" and name like '" + bean.getName() + "%'");
            if (bean.getCourseId() > 0) sql.append(" and course_id = " + bean.getCourseId());
            if (bean.getCourseName() != null && !bean.getCourseName().isEmpty())
                sql.append(" and course_name like '" + bean.getCourseName() + "%'");
            if (bean.getDescription() != null && !bean.getDescription().isEmpty())
                sql.append(" and description like '" + bean.getDescription() + "%'");
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<SubjectBean> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToSubject(rs));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in searching Subject");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }

    /**
     * Helper method to map ResultSet row to SubjectBean.
     */
    private SubjectBean mapResultSetToSubject(ResultSet rs) throws Exception {
        SubjectBean bean = new SubjectBean();
        bean.setId(rs.getLong("id"));
        bean.setName(rs.getString("name"));
        bean.setCourseId(rs.getLong("course_id"));
        bean.setCourseName(rs.getString("course_name"));
        bean.setDescription(rs.getString("description"));
        bean.setCreatedBy(rs.getString("created_by"));
        bean.setModifiedBy(rs.getString("modified_by"));
        bean.setCreatedDatetime(rs.getTimestamp("created_datetime"));
        bean.setModifiedDatetime(rs.getTimestamp("modified_datetime"));
        return bean;
    }
}
