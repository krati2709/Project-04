package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.LibraryBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class LibraryModel {

    // ---------- nextPk(): Generate next Primary Key ----------
    public Integer nextPk() throws DatabaseException {

        int pk = 0;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM st_library");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pk = rs.getInt(1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception : Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    // ---------- add(): Insert new library record ----------
    public long add(LibraryBean bean) throws ApplicationException, DuplicateRecordException, SQLException {

        Connection conn = null;
        int pk = 0;

        // Duplicate based on book title + author
        LibraryBean exist = findByName(bean.getTitle());
        if (exist != null) {
            throw new DuplicateRecordException("Book already exists in library");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO st_library VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setLong(1, pk);
            pstmt.setString(2, bean.getTitle());
            pstmt.setString(3, bean.getAuthor());
            pstmt.setString(4, bean.getIsbn());
            pstmt.setString(5, bean.getCategory());
            pstmt.setString(6, bean.getPublisher());
            pstmt.setInt(7, bean.getPublishedYear());
            pstmt.setInt(8, bean.getQuantity());
            pstmt.setString(9, bean.getDescription());
            pstmt.setString(10, bean.getCreatedBy());
            pstmt.setString(11, bean.getModifiedBy());
            pstmt.setTimestamp(12, bean.getCreatedDatetime());
            pstmt.setTimestamp(13, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in adding Library");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // ---------- update(): Update existing library record ----------
    public void update(LibraryBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        // Duplicate check only on title
        LibraryBean exist = findByName(bean.getTitle());
        if (exist != null && exist.getId() != bean.getId()) {
            throw new DuplicateRecordException("Library Book already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE st_library SET title=?, author=?, isbn=?, category=?, publisher=?, "
                    + "published_year=?, quantity=?, description=?, created_by=?, modified_by=?, "
                    + "created_datetime=?, modified_datetime=? WHERE id=?");

            pstmt.setString(1, bean.getTitle());
            pstmt.setString(2, bean.getAuthor());
            pstmt.setString(3, bean.getIsbn());
            pstmt.setString(4, bean.getCategory());
            pstmt.setString(5, bean.getPublisher());
            pstmt.setInt(6, bean.getPublishedYear());
            pstmt.setInt(7, bean.getQuantity());
            pstmt.setString(8, bean.getDescription());
            pstmt.setString(9, bean.getCreatedBy());
            pstmt.setString(10, bean.getModifiedBy());
            pstmt.setTimestamp(11, bean.getCreatedDatetime());
            pstmt.setTimestamp(12, bean.getModifiedDatetime());
            pstmt.setLong(13, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback Exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Library");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ---------- delete(): Delete record ----------
    public void delete(LibraryBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM st_library WHERE id=?");
            pstmt.setLong(1, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback Exception: " + ex.getMessage());
            }
            throw new ApplicationException("Exception in deleting library record");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ---------- findByName(): Find by title ----------
    public LibraryBean findByName(String title) throws ApplicationException {

        StringBuffer sql = new StringBuffer("SELECT * FROM st_library WHERE title=?");
        LibraryBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, title);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = populateBean(rs);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in finding Library by Title");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ---------- findByPk(): Find by ID ----------
    public LibraryBean findByPk(long pk) throws ApplicationException {

        LibraryBean bean = null;
        StringBuffer sql = new StringBuffer("SELECT * FROM st_library WHERE id=?");
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = populateBean(rs);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in finding Library by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ---------- list(): Return all books ----------
    public List<LibraryBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    // ---------- search(): Dynamic search + pagination ----------
    public List<LibraryBean> search(LibraryBean bean, int pageNo, int pageSize) throws ApplicationException {

        StringBuffer sql = new StringBuffer("SELECT * FROM st_library WHERE 1=1");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" AND id = " + bean.getId());
            }
            if (bean.getTitle() != null && bean.getTitle().length() > 0) {
                sql.append(" AND title LIKE '" + bean.getTitle() + "%'");
            }
            if (bean.getAuthor() != null && bean.getAuthor().length() > 0) {
                sql.append(" AND author LIKE '" + bean.getAuthor() + "%'");
            }
            if (bean.getCategory() != null && bean.getCategory().length() > 0) {
                sql.append(" AND category LIKE '" + bean.getCategory() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" LIMIT " + pageNo + ", " + pageSize);
        }

        ArrayList<LibraryBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LibraryBean listBean = populateBean(rs);
                list.add(listBean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search Library");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    // ---------- Helper: Populate Bean ----------
    private LibraryBean populateBean(ResultSet rs) throws Exception {

        LibraryBean bean = new LibraryBean();

        bean.setId(rs.getLong(1));
        bean.setTitle(rs.getString(2));
        bean.setAuthor(rs.getString(3));
        bean.setIsbn(rs.getString(4));
        bean.setCategory(rs.getString(5));
        bean.setPublisher(rs.getString(6));
        bean.setPublishedYear(rs.getInt(7));
        bean.setQuantity(rs.getInt(8));
        bean.setDescription(rs.getString(9));
        bean.setCreatedBy(rs.getString(10));
        bean.setModifiedBy(rs.getString(11));
        bean.setCreatedDatetime(rs.getTimestamp(12));
        bean.setModifiedDatetime(rs.getTimestamp(13));

        return bean;
    }
}
