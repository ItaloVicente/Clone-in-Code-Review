
package org.eclipse.jgit.http.server.glue;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final int status;

	public ErrorServlet(final int status) {
		this.status = status;
	}

	@Override
	protected void doGet(HttpServletRequest req
			throws ServletException
		rsp.sendError(status);
	}
}
