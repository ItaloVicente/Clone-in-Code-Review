
package org.eclipse.jgit.http.server.glue;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

public interface ServletBinder {
	public ServletBinder through(Filter filter);

	public void with(HttpServlet servlet);
}
