package nl.salland.scoreboard;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScoreboardConfigListener implements ServletContextListener {

	public static Logger LOG = LoggerFactory.getLogger(ScoreboardConfigListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		LOG.info("contextDestroyed START");
		ServletContext ctx = servletContextEvent.getServletContext();
		
		ComPortController comPort = (ComPortController) ctx.getAttribute("ComPortController");
		try {
			comPort.close();
			LOG.info("All closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		LOG.info("contextInitialized START");
		// somehow get com port lists
		String[] comportList = ComPortController.getComports();
		
		ServletContext ctx = servletContextEvent.getServletContext();
		
		// try all com ports until one works.
		ComPortController comPort = new ComPortController("COM10"); //, true);
		comPort.init();
		LOG.info("com port initialized");
		
		comPort.sendInitString();
		String line = ScoreBoardUtils.createLine1NoControl(" Welcome");
		comPort.sendLine(line);

		line = ScoreBoardUtils.createLine2NoControl("    to");
		comPort.sendLine(line);

		line = ScoreBoardUtils.createLine3NoControl("SallandCC");
		comPort.sendLine(line);
		
		line = ScoreBoardUtils.createLine4NoControl("");
		comPort.sendLine(line);
		
		line = ScoreBoardUtils.createLine5NoControl(ServletUtils.getCurrentDate());
		comPort.sendLine(line);
		LOG.info("Welcome message sent");
		
		ctx.setAttribute("ComPortController", comPort);
	}

}
