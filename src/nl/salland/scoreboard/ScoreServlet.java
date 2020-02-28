package nl.salland.scoreboard;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScoreServlet extends HttpServlet {

	public static Logger LOG = LoggerFactory.getLogger(ScoreServlet.class);

	private static final long serialVersionUID = 1L;

	public void destroy() {
		LOG.debug("Destroy myself");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.info("ScoreServlet: doGet()");
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		LOG.info("In post");

		ServletContext ctx = request.getServletContext();
		ComPortController comPort = (ComPortController) ctx.getAttribute("ComPortController");

		String message = "";
		
		String line;
		try {
			comPort.sendInitString();
			
			line = ScoreBoardUtils.createLine1(Integer.parseInt((String)request.getParameter("batter1")), Integer.parseInt((String)request.getParameter("total")), Integer.parseInt((String)request.getParameter("batter2")));
			comPort.sendLine(line);

			line = ScoreBoardUtils.createLine2(Integer.parseInt((String)request.getParameter("runs1")), Integer.parseInt((String)request.getParameter("wickets")), Integer.parseInt((String)request.getParameter("runs2")));
			comPort.sendLine(line);

			line = ScoreBoardUtils.createLine3(Integer.parseInt((String)request.getParameter("bowler1")), Integer.parseInt((String)request.getParameter("lastWicket")), Integer.parseInt((String)request.getParameter("lastman")));
			comPort.sendLine(line);

			line = ScoreBoardUtils.createLine4(Integer.parseInt((String)request.getParameter("bowler2")), Integer.parseInt((String)request.getParameter("prevInnings")), Integer.parseInt((String)request.getParameter("RR")));
			comPort.sendLine(line);
		
			line = ScoreBoardUtils.createLine5(Integer.parseInt((String)request.getParameter("oversBowled")), Integer.parseInt((String)request.getParameter("DL")), Integer.parseInt((String)request.getParameter("oversRemaining")));
			comPort.sendLine(line);

			LOG.debug("Score sent @ " + ServletUtils.getCurrentTimeStamp());
			message =  "Score sent @ " + ServletUtils.getCurrentTimeStamp();
			
		} catch (RuntimeException e) {
			message = "ERROR: " + comPort.getErrorMessage()+ " : !!send again!! : " + ServletUtils.getCurrentTimeStamp();
		}
		
		LOG.info("doPost END: " + message);	

		response.setContentType("text/plain");
		response.getWriter().write(message);
	}

}