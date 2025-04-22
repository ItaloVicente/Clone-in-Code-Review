
package org.eclipse.jgit.http.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.RefAdvertiser;

class InfoRefsServlet extends RepositoryServlet {
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
		final byte[] raw = compute(req);
		rsp.setContentType("text/plain");
		rsp.setCharacterEncoding(ENCODING);
		send(raw
	}

	private byte[] compute(final HttpServletRequest req) throws IOException {
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
