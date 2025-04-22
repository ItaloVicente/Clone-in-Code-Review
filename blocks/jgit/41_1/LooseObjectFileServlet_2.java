
package org.eclipse.jgit.http.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.lib.AlternateRepositoryDatabase;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefComparator;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;

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
		final RefAdvertiser adv = compute(req);
		final byte[] raw = adv.toByteArray();
		rsp.setContentType("text/plain");
		rsp.setCharacterEncoding(ENCODING);
		send(raw
	}

	private RefAdvertiser compute(final HttpServletRequest req) {
		final Repository db = getRepository(req);
		final RefAdvertiser adv = new RefAdvertiser(new RevWalk(db));
		adv.send(db.getAllRefs().values());
		return adv;
	}

	private static class RefAdvertiser {
		private final RevWalk walk;

		private final RevFlag ADVERTISED;

		private final StringBuilder outBuffer = new StringBuilder();

		private final char[] tmpId = new char[2 * Constants.OBJECT_ID_LENGTH];

		private final Set<String> capablities = new LinkedHashSet<String>();

		private boolean first = true;

		RefAdvertiser(final RevWalk protoWalk) {
			walk = protoWalk;
			ADVERTISED = protoWalk.newFlag("ADVERTISED");
		}

		void advertiseCapability(String name) {
			capablities.add(name);
		}

		void send(final Collection<Ref> refs) {
			for (final Ref r : RefComparator.sort(refs)) {
				final RevObject obj = parseAnyOrNull(r.getObjectId());
				if (obj != null) {
					advertiseAny(obj
					if (obj instanceof RevTag)
						advertiseTag((RevTag) obj
				}
			}
		}

		void advertiseHave(AnyObjectId id) {
			RevObject obj = parseAnyOrNull(id);
			if (obj != null) {
				advertiseAnyOnce(obj
				if (obj instanceof RevTag)
					advertiseAnyOnce(((RevTag) obj).getObject()
			}
		}

		void includeAdditionalHaves() {
			additionalHaves(walk.getRepository().getObjectDatabase());
		}

		byte[] toByteArray() throws UnsupportedEncodingException {
			return outBuffer.toString().getBytes(ENCODING);
		}

		private void additionalHaves(final ObjectDatabase db) {
			if (db instanceof AlternateRepositoryDatabase)
				additionalHaves(((AlternateRepositoryDatabase) db)
						.getRepository());
			for (ObjectDatabase alt : db.getAlternates())
				additionalHaves(alt);
		}

		private void additionalHaves(final Repository alt) {
			for (final Ref r : alt.getAllRefs().values())
				advertiseHave(r.getObjectId());
		}

		boolean isEmpty() {
			return first;
		}

		private RevObject parseAnyOrNull(final AnyObjectId id) {
			if (id == null)
				return null;
			try {
				return walk.parseAny(id);
			} catch (IOException e) {
				return null;
			}
		}

		private void advertiseAnyOnce(final RevObject obj
			if (!obj.has(ADVERTISED))
				advertiseAny(obj
		}

		private void advertiseAny(final RevObject obj
			obj.add(ADVERTISED);
			advertiseId(obj
		}

		private void advertiseTag(final RevTag tag
			RevObject o = tag;
			do {
				final RevObject target = ((RevTag) o).getObject();
				try {
					walk.parseHeaders(target);
				} catch (IOException err) {
					return;
				}
				target.add(ADVERTISED);
				o = target;
			} while (o instanceof RevTag);
			advertiseAny(tag.getObject()
		}

		void advertiseId(final AnyObjectId id
			id.copyTo(tmpId
			outBuffer.append(' ');
			outBuffer.append(refName);
			if (first) {
				first = false;
				if (!capablities.isEmpty()) {
					outBuffer.append('\0');
					for (final String capName : capablities) {
						outBuffer.append(' ');
						outBuffer.append(capName);
					}
					outBuffer.append(' ');
				}
			}
			outBuffer.append('\n');
		}
	}
}
