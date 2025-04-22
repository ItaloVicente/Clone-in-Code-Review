
package org.eclipse.jgit.http.test.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.URIish;

public abstract class HttpTestCase extends LocalDiskRepositoryTestCase {
	protected static final String master = Constants.R_HEADS + Constants.MASTER;

	protected AppServer server;

	protected void setUp() throws Exception {
		super.setUp();
		server = new AppServer();
	}

	protected void tearDown() throws Exception {
		server.tearDown();
		super.tearDown();
	}

	protected TestRepository createTestRepository() throws Exception {
		return new TestRepository(createBareRepository());
	}

	protected URIish toURIish(String path) throws URISyntaxException {
		URI u = server.getURI().resolve(path);
		return new URIish(u.toString());
	}

	protected URIish toURIish(ServletContextHandler app
			throws URISyntaxException {
		String p = app.getContextPath();
		if (!p.endsWith("/") && !name.startsWith("/"))
			p += "/";
		p += name;
		return toURIish(p);
	}

	protected List<AccessEvent> getRequests() {
		return server.getRequests();
	}

	protected List<AccessEvent> getRequests(URIish base
		return server.getRequests(base
	}

	protected List<AccessEvent> getRequests(String path) {
		return server.getRequests(path);
	}

	protected static void fsck(Repository db
			throws Exception {
		new TestRepository(db).fsck(tips);
	}

	protected static Set<RefSpec> mirror(String... refs) {
		HashSet<RefSpec> r = new HashSet<RefSpec>();
		for (String name : refs) {
			RefSpec rs = new RefSpec(name);
			rs = rs.setDestination(name);
			rs = rs.setForceUpdate(true);
			r.add(rs);
		}
		return r;
	}

	protected static Collection<RemoteRefUpdate> push(TestRepository from
			RevCommit q) throws IOException {
		final Repository db = from.getRepository();
		final String srcExpr = q.name();
		final String dstName = master;
		final boolean forceUpdate = true;
		final String localName = null;
		final ObjectId oldId = null;

		RemoteRefUpdate u = new RemoteRefUpdate(db
				forceUpdate
		return Collections.singleton(u);
	}

	public static String loose(URIish base
		final String objectName = id.name();
		final String d = objectName.substring(0
		final String f = objectName.substring(2);
		return join(base
	}

	public static String join(URIish base
		if (path.startsWith("/"))
			fail("Cannot join absolute path " + path + " to URIish " + base);

		String dir = base.getPath();
		if (!dir.endsWith("/"))
			dir += "/";
		return dir + path;
	}
}
