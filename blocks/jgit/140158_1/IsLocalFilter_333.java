
package org.eclipse.jgit.http.server;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.http.server.ServletUtils.getRepository;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RefAdvertiser;
import org.eclipse.jgit.util.HttpSupport;

class InfoRefsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(final HttpServletRequest req
			final HttpServletResponse rsp) throws IOException {
		rsp.setContentType(HttpSupport.TEXT_PLAIN);
		rsp.setCharacterEncoding(UTF_8.name());

		final Repository db = getRepository(req);
		try (OutputStreamWriter out = new OutputStreamWriter(
				new SmartOutputStream(req
				UTF_8)) {
			final RefAdvertiser adv = new RefAdvertiser() {
				@Override
				protected void writeOne(CharSequence line)
						throws IOException {
					out.append(line.toString().replace(' '
				}

				@Override
				protected void end() {
				}
			};
			adv.init(db);
			adv.setDerefTags(true);
			adv.send(db.getRefDatabase().getRefsByPrefix(Constants.R_REFS));
		}
	}
}
