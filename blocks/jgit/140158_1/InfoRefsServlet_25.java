
package org.eclipse.jgit.http.server;

import static org.eclipse.jgit.http.server.ServletUtils.getRepository;
import static org.eclipse.jgit.http.server.ServletUtils.sendPlainText;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.internal.storage.file.ObjectDirectory;
import org.eclipse.jgit.internal.storage.file.PackFile;
import org.eclipse.jgit.lib.ObjectDatabase;

class InfoPacksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(final HttpServletRequest req
			final HttpServletResponse rsp) throws IOException {
		sendPlainText(packList(req)
	}

	private static String packList(HttpServletRequest req) {
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
