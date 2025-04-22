
package org.eclipse.jgit.http.server;

import static org.eclipse.jgit.http.server.ServletUtils.getRepository;
import static org.eclipse.jgit.http.server.ServletUtils.nocache;
import static org.eclipse.jgit.http.server.ServletUtils.send;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.RefAdvertiser;

class InfoRefsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String ENCODING = "UTF-8";

	public void doGet(final HttpServletRequest req
			final HttpServletResponse rsp) throws IOException {
		final byte[] raw = dumbHttp(req);
		nocache(rsp);
		rsp.setContentType("text/plain");
		rsp.setCharacterEncoding(Constants.CHARACTER_ENCODING);
		send(raw
	}

	private byte[] dumbHttp(final HttpServletRequest req) throws IOException {
		final Repository db = getRepository(req);
		final RevWalk walk = new RevWalk(db);
		final RevFlag ADVERTISED = walk.newFlag("ADVERTISED");
		final StringBuilder out = new StringBuilder();
		final RefAdvertiser adv = new RefAdvertiser() {
			@Override
			protected void writeOne(final CharSequence line) {
				out.append(line.toString().replace(' '
			}

			@Override
			protected void end() {
			}
		};
		adv.init(walk
		adv.setDerefTags(true);
		adv.send(db.getAllRefs().values());
		return out.toString().getBytes(ENCODING);
	}
}
