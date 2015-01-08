/**
 * 
 */
package com.finin.jboss.samples;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author a208220 - Juan Manuel CABRERA
 *
 */
@WebServlet(SimpleServlet.PATH)
public class SimpleServlet extends HttpServlet {

	private static final long		serialVersionUID	= 123151277137840774L;

	public static final String	PATH							= "/serv";

	public static final String	PREFIX						= "serv-";

	public static final String	MSG								= "msg";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String msg = req.getParameter(MSG);
		resp.getWriter().write(PREFIX + msg);
	}

}
