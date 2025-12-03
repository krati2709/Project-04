package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.MarksheetModel;
import in.co.rays.proj4.model.StudentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Controller to handle Marksheet operations.
 * <p>
 * It handles Add, Update, Reset, and Cancel operations for marksheets.
 * Also validates marks, roll number, and student selection.
 * </p>
 * 
 * @author krati
 */
@WebServlet(name = "MarksheetCtl", urlPatterns = { "/ctl/MarksheetCtl" })
public class MarksheetCtl extends BaseCtl {

    /**
     * Loads the list of students for dropdown.
     */
    @Override
    protected void preload(HttpServletRequest request) {
        StudentModel studentModel = new StudentModel();
        try {
            List studentList = studentModel.list();
            request.setAttribute("studentList", studentList);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates Marksheet form fields.
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("studentId"))) {
            request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("rollNo"))) {
            request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
            pass = false;
        } else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
            request.setAttribute("rollNo", "Roll No is invalid");
            pass = false;
        }

        String[] subjects = { "physics", "chemistry", "maths" };
        for (String subject : subjects) {
            if (DataValidator.isNull(request.getParameter(subject))) {
                request.setAttribute(subject, PropertyReader.getValue("error.require", "Marks"));
                pass = false;
            } else if (!DataValidator.isInteger(request.getParameter(subject))) {
                request.setAttribute(subject, PropertyReader.getValue("error.integer", "Marks"));
                pass = false;
            } else if (DataUtility.getInt(request.getParameter(subject)) < 0
                    || DataUtility.getInt(request.getParameter(subject)) > 100) {
                request.setAttribute(subject, "Marks should be in 0 to 100");
                pass = false;
            }
        }

        return pass;
    }

    /**
     * Populates MarksheetBean from request parameters.
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        MarksheetBean bean = new MarksheetBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
        bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
        bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
        bean.setStudentId(DataUtility.getLong(request.getParameter("studentId")));

        populateDTO(bean, request);
        return bean;
    }

    /**
     * Handles GET request to load a marksheet for editing.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));
        MarksheetModel model = new MarksheetModel();

        if (id > 0) {
            try {
                MarksheetBean bean = model.findByPk(id);
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
     * Handles POST request for Add, Update, Reset, and Cancel operations.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        MarksheetModel model = new MarksheetModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {
            MarksheetBean bean = (MarksheetBean) populateBean(request);
            try {
                if (id > 0) {
                    model.update(bean);
                    ServletUtility.setSuccessMessage("Marksheet updated successfully", request);
                } else {
                    model.add(bean);
                    ServletUtility.setSuccessMessage("Marksheet added successfully", request);
                }
                ServletUtility.setBean(bean, request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Roll No already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for Marksheet.
     */
    @Override
    protected String getView() {
        return ORSView.MARKSHEET_VIEW;
    }

}
