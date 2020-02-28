package nl.salland.scoreboard;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextServlet extends HttpServlet {

	public  static Logger LOG = LoggerFactory.getLogger(TextServlet.class);

	private static final long serialVersionUID = 1L;


	public void destroy() {
		LOG.debug("Destroy myself");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.info("Call to get()");
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.info("doPost START");

		ServletContext ctx = request.getServletContext();
		ComPortController comPort = (ComPortController) ctx.getAttribute("ComPortController");
		
		String message = "Text senT OK " + ServletUtils.getCurrentTimeStamp();
		try {
			comPort.sendInitString();
			String line = ScoreBoardUtils.createLine1NoControl((String)request.getParameter("line1"));
			comPort.sendLine(line);
			
			line = ScoreBoardUtils.createLine2NoControl((String)request.getParameter("line2"));
			comPort.sendLine(line);
			
			line = ScoreBoardUtils.createLine3NoControl((String)request.getParameter("line3"));
			comPort.sendLine(line);
			
			line = ScoreBoardUtils.createLine4NoControl((String)request.getParameter("line4"));
			comPort.sendLine(line);
			
			line = ScoreBoardUtils.createLine5NoControl((String)request.getParameter("line5"));
			comPort.sendLine(line);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			message = "ERROR: " +comPort.getErrorMessage()+ " : send again " + ServletUtils.getCurrentTimeStamp();
		}
		LOG.info("doPost END: " + message);
		
		response.setContentType("text/plain");
		response.getWriter().write(message);
	}


}
