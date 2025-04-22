
package org.eclipse.jgit.http.server;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectDirectory;
import org.eclipse.jgit.lib.PackFile;

class InfoPacksServlet extends RepositoryServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(final HttpServletRequest req
			final HttpServletResponse rsp) throws IOException {
		nocache(rsp);
		sendPlainText(packList(req)
	}

	private static String packList(final HttpServletRequest req) {
		final StringBuilder out = new StringBuilder();
		final ObjectDatabase db = getRepository(req).getObjectDatabase();
		if (db instanceof ObjectDirectory) {
			for (PackFile pack : ((ObjectDirectory) db).getPacks()) {
				out.append("P ");
				out.append(pack.getPackFile().getName());
				out.append('\n');
			}
		}
		out.append('\n');
		return out.toString();
	}
}
