package view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;

import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

import model.services.TasksAMImpl;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;

import net.sf.jasperreports.engine.xml.JRXmlLoader;

import oracle.jbo.client.Configuration;

public class ReportServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        TasksAMImpl appM =
            (TasksAMImpl) Configuration.createRootApplicationModule("model.services.TasksAMImpl", "TasksAMLocal");
        ServletContext ctx = getServletContext();
        
        try(PreparedStatement ps = appM.getDBTransaction().createPreparedStatement("commit", 1);
            Connection conn = ps.getConnection();
            InputStream input = ctx.getResourceAsStream("/Reports/TasksReport.jrxml")){
            
            response.setContentType("application/pdf");
            response.setHeader("Cache-Control", "max-age=0");
            
            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);
            
            HashMap param = new HashMap();
            String id = request.getParameter("id");
            param.put("userId", id);
            JasperPrint print;
            if(id == null){
                print = JasperFillManager.fillReport(report, null, conn);
            }
            else {
                print = JasperFillManager.fillReport(report, param, conn);
            }
            
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, stream);
            
            try(ServletOutputStream out = response.getOutputStream()){
                out.write(stream.toByteArray());
                out.flush();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
        
        return;

    }
}
