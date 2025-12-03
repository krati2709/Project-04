package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * SubjectCtl is a controller class to handle adding, updating, and managing
 * Subjects.
 * <p>
 * It interacts with SubjectModel for database operations and forwards data to
 * SubjectView JSP.
 * </p>
 * <p>
 * URL pattern for this controller is "/ctl/SubjectCtl".
 * </p>
 * <p>
 * It supports preloading of courses for dropdowns, input validation, and CRUD
 * operations.
 * </p>
 * 
 * @author krati
 * @version 1.0
 */
@WebServlet(name = "SubjectCtl", urlPatterns = { "/ctl/SubjectCtl" })
public class SubjectCtl extends BaseCtl {

    /**
     * Preloads list of courses for the Subject form.
     * 
     * @param request HttpServletRequest object
     */
    @Override
    protected void preload(HttpServletRequest request) {
        CourseModel courseModel = new CourseModel();
        try {
            List courseList = courseModel.list();
            request.setAttribute("courseList", courseList);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates the input data from the Subject form.
     * 
     * @param request HttpServletRequest object
     * @return true if all validations pass, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Subject Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("courseId"))) {
            request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates SubjectBean from HTTP request parameters.
     * 
     * @param request HttpServletRequest object
     * @return BaseBean populated with request data
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        SubjectBean bean = new SubjectBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles HTTP GET request to display Subject form.
     * <p>
     * If an id parameter is provided, loads existing Subject data for editing.
     * </p>
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long id = DataUtility.getLong(request.getParameter("id"));

        SubjectModel model = new SubjectModel();

        if (id > 0) {
            try {
                SubjectBean bean = model.findByPk(id);
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
     * Handles HTTP POST request for saving, updating, reset, and cancel operations
     * for Subject.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        SubjectModel model = new SubjectModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {
            SubjectBean bean = (SubjectBean) populateBean(request);
            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("User added successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Login Id already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            SubjectBean bean = (SubjectBean) populateBean(request);
            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("User updated successfully", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
            return;
        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
            return;
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for Subject module.
     * 
     * @return JSP view path for Subject form
     */
    @Override
    protected String getView() {
        return ORSView.SUBJECT_VIEW;
    }

}
