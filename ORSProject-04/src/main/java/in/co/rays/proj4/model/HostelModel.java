package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.HostelBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class HostelModel {

	// ---------- nextPk(): Get next Primary Key ----------
	public Integer nextPk() throws DatabaseException {

		int pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection(); // Get DB connection
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_hostel"); // SQL to fetch max PK
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1); // Extract highest ID
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn); // Close connection
		}

		return pk + 1; // Return next PK
	}

	public void add(HostelBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_hostel values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getType());
			pstmt.setInt(4, bean.getCapacity());
			pstmt.setString(5, bean.getDescription());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			System.out.println("data here");

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
			System.out.println("after close");

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + e1.getMessage());
			}
			throw new ApplicationException("Exception : add rollback exception " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ---------- update(): Update hostel ----------
	public void update(HostelBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		// Check duplicate name (but ignore current record)
		HostelBean existBean = findByName(bean.getName());
		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Hostel already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update st_hostel set name = ?, type = ?, capacity = ?, description = ?, "
							+ "created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? "
							+ "where id = ?");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getType());
			pstmt.setInt(3, bean.getCapacity());
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

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in updating Hostel");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ---------- delete(): Delete hostel ----------
	public void delete(HostelBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_hostel where id = ?");

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

			throw new ApplicationException("Exception : Exception in deleting Hostel");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ---------- findByName(): Search hostel by name ----------
	public HostelBean findByName(String name) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_hostel where name = ?");
		HostelBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new HostelBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setType(rs.getString(3));
				bean.setCapacity(rs.getInt(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception: Exception in getting Hostel by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ---------- findByPk(): Get hostel by ID ----------
	public HostelBean findByPk(long pk) throws ApplicationException {

		HostelBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_hostel where id = ?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new HostelBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setType(rs.getString(3));
				bean.setCapacity(rs.getInt(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Hostel by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}
	
	
    // ---------- list(): Retrieve all hostels ----------
    public List<HostelBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    // ---------- search(): Dynamic search + pagination ----------
    public List<HostelBean> search(HostelBean bean, int pageNo, int pageSize) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_hostel where 1=1");

        // ----- Dynamic Filters -----
        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id = " + bean.getId());
            }

            if (bean.getName() != null && bean.getName().length() > 0) {
                sql.append(" and name like '" + bean.getName() + "%'");
            }

            if (bean.getType() != null && bean.getType().length() > 0) {
                sql.append(" and type like '" + bean.getType() + "%'");
            }

            if (bean.getDescription() != null && bean.getDescription().length() > 0) {
                sql.append(" and description like '" + bean.getDescription() + "%'");
            }
        }

        // ----- Pagination -----
        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        Connection conn = null;
        ArrayList<HostelBean> list = new ArrayList();

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                HostelBean bean1 = new HostelBean();

                bean1.setId(rs.getLong(1));
                bean1.setName(rs.getString(2));
                bean1.setType(rs.getString(3));
                bean1.setCapacity(rs.getInt(4));
                bean1.setDescription(rs.getString(5));
                bean1.setCreatedBy(rs.getString(6));
                bean1.setModifiedBy(rs.getString(7));
                bean1.setCreatedDatetime(rs.getTimestamp(8));
                bean1.setModifiedDatetime(rs.getTimestamp(9));

                list.add(bean1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in search Hostel");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }


}
