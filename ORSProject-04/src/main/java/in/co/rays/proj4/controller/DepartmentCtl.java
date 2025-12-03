package in.co.rays.proj4.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.DepartmentBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.DepartmentModel;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Department Controller — Handles add, update, reset and cancel operations
 * for Department entities.
 *
 * @author Krati
 */
@WebServlet(name = "DepartmentCtl", urlPatterns = { "/ctl/DepartmentCtl" })
public class DepartmentCtl extends BaseCtl {

    /**
     * Validates Department form input.
     *
     * @param request HTTP request object
     * @return true if validation passes, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Department Name"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("desc"))) {
            request.setAttribute("desc", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates DepartmentBean from request parameters.
     *
     * @param request HTTP request
     * @return populated DepartmentBean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        DepartmentBean bean = new DepartmentBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDescription(DataUtility.getString(request.getParameter("desc")));

        populateDTO(bean, request);
        return bean;
    }

    /**
     * Handles HTTP GET request — loads bean if id is present.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));
        DepartmentModel model = new DepartmentModel();

        if (id > 0) {
            try {
                DepartmentBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles HTTP POST request — Save, Update, Reset, Cancel operations.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        DepartmentModel model = new DepartmentModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {
            DepartmentBean bean = (DepartmentBean) populateBean(request);
            try {
                model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("User added successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Department name already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            DepartmentBean bean = (DepartmentBean) populateBean(request);
            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("User updated successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Login Id already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.DEPARTMENT_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.DEPARTMENT_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for Department.
     *
     * @return JSP page constant
     */
    @Override
    protected String getView() {
        return ORSView.DEPARTMENT_VIEW;
    }

}
