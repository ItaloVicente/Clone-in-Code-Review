
package org.eclipse.jgit.http.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

class GetRefServlet extends RepositoryServlet {
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
		final byte[] raw = read(req);
		if (raw != null) {
			rsp.setContentType("text/plain");
			rsp.setCharacterEncoding(ENCODING);
			send(raw
		} else {
			rsp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private static byte[] read(final HttpServletRequest req) throws IOException {
		final String refName = req.getPathInfo();
		if (!isValidName(refName))
			return null;

		final Ref ref = getRepository(req).getRef(refName);
		if (ref == null)
			return null;

		final StringBuilder out = new StringBuilder();
		if (isSymref(ref)) {
			out.append("ref: ");
			out.append(ref.getName());
		} else {
			out.append(ref.getObjectId().getName());
		}
		out.append('\n');
		return out.toString().getBytes(ENCODING);
	}

	private static boolean isValidName(final String refName) {
		if (refName.equals(Constants.HEAD))
			return true;
				&& !Repository.isValidRefName(refName);
	}

	private static boolean isSymref(final Ref ref) {
		return !ref.getName().equals(ref.getOrigName());
	}
}
