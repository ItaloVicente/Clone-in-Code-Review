
package org.eclipse.jgit.http.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectDirectory;
import org.eclipse.jgit.lib.PackFile;

class InfoPacksServlet extends RepositoryServlet {
	private static final long serialVersionUID = 1L;

	private static final String ENCODING = "UTF-8";

	@Override
	public void doGet(final HttpServletRequest req
			final HttpServletResponse rsp) throws IOException {
		serve(req
	}

	@Override
	protected void doHead(final HttpServletRequest req
			final HttpServletResponse rsp) throws ServletException
		serve(req
	}

	private void serve(final HttpServletRequest req
			final HttpServletResponse rsp
			throws IOException {
		final byte[] raw = packList(req);
		rsp.setContentType("text/plain");
		rsp.setCharacterEncoding(ENCODING);
		send(raw
	}

	private static byte[] packList(final HttpServletRequest req)
			throws IOException {
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
		return out.toString().getBytes(ENCODING);
	}
}
