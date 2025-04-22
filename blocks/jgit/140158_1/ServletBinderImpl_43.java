
package org.eclipse.jgit.http.server.glue;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

public interface ServletBinder {
	ServletBinder through(Filter filter);

	void with(HttpServlet servlet);
}
