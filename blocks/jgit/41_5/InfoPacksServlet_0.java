
package org.eclipse.jgit.http.server;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

class GetRefServlet extends RepositoryServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(final HttpServletRequest req
			final HttpServletResponse rsp) throws IOException {
		nocache(rsp);
		final String content = read(req);
		if (content != null)
			sendPlainText(content
		else
			rsp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	private static String read(final HttpServletRequest req) throws IOException {
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
		return out.toString();
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
