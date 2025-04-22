
package org.eclipse.jgit.http.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.http.server.GitSmartHttpTools;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.http.HttpTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.transport.PacketLineIn;
import org.eclipse.jgit.transport.PacketLineOut;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.util.NB;
import org.junit.Before;
import org.junit.Test;

public class ProtocolErrorTest extends HttpTestCase {
	private Repository remoteRepository;

	private URIish remoteURI;

	private RevBlob a_blob;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		final TestRepository<Repository> src = createTestRepository();
		final String srcName = src.getRepository().getDirectory().getName();

		ServletContextHandler app = server.addContext("/git");
		GitServlet gs = new GitServlet();
		gs.setRepositoryResolver(new RepositoryResolver<HttpServletRequest>() {
			@Override
			public Repository open(HttpServletRequest req
					throws RepositoryNotFoundException
					ServiceNotEnabledException {
				if (!name.equals(srcName))
					throw new RepositoryNotFoundException(name);

				final Repository db = src.getRepository();
				db.incrementOpen();
				return db;
			}
		});
		app.addServlet(new ServletHolder(gs)

		server.setUp();

		remoteRepository = src.getRepository();
		remoteURI = toURIish(app

		StoredConfig cfg = remoteRepository.getConfig();
		cfg.setBoolean("http"
		cfg.save();

		a_blob = src.blob("a");
	}

	@Test
	public void testPush_UnpackError_TruncatedPack() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(ObjectId.zeroId().name());
		sb.append(' ');
		sb.append(a_blob.name());
		sb.append(' ');
		sb.append("refs/objects/A");
		sb.append('\0');
		sb.append("report-status");

		ByteArrayOutputStream reqbuf = new ByteArrayOutputStream();
		PacketLineOut reqpck = new PacketLineOut(reqbuf);
		reqpck.writeString(sb.toString());
		reqpck.end();

		packHeader(reqbuf

		byte[] reqbin = reqbuf.toByteArray();

		URL u = new URL(remoteURI.toString() + "/git-receive-pack");
		HttpURLConnection c = (HttpURLConnection) u.openConnection();
		try {
			c.setRequestMethod("POST");
			c.setDoOutput(true);
			c.setRequestProperty("Content-Type"
					GitSmartHttpTools.RECEIVE_PACK_REQUEST_TYPE);
			c.setFixedLengthStreamingMode(reqbin.length);
			try (OutputStream out = c.getOutputStream()) {
				out.write(reqbin);
			}

			assertEquals(200
			assertEquals(GitSmartHttpTools.RECEIVE_PACK_RESULT_TYPE
					c.getContentType());

			try (InputStream rawin = c.getInputStream()) {
				PacketLineIn pckin = new PacketLineIn(rawin);
				assertEquals("unpack error "
						+ JGitText.get().packfileIsTruncatedNoParam
						pckin.readString());
				assertEquals("ng refs/objects/A n/a (unpacker error)"
						pckin.readString());
				assertSame(PacketLineIn.END
			}
		} finally {
			c.disconnect();
		}
	}

	private static void packHeader(ByteArrayOutputStream tinyPack
			throws IOException {
		final byte[] hdr = new byte[8];
		NB.encodeInt32(hdr
		NB.encodeInt32(hdr

		tinyPack.write(Constants.PACK_SIGNATURE);
		tinyPack.write(hdr
	}
}
