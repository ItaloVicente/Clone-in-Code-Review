
package org.eclipse.jgit.transport;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectDirectory;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.TemporaryBuffer;

public class ReceivePackRefFilterTest extends LocalDiskRepositoryTestCase {
	private static final NullProgressMonitor PM = NullProgressMonitor.INSTANCE;

	private static final String R_MASTER = Constants.R_HEADS + Constants.MASTER;

	private static final String R_PRIVATE = Constants.R_HEADS + "private";

	private Repository src;

	private Repository dst;

	private RevCommit A

	private RevBlob littlea;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		src = createBareRepository();
		dst = createBareRepository();

		TestRepository d = new TestRepository(dst);
		littlea = d.blob("a");
		A = d.commit(d.tree(d.file("a"
		B = d.commit().parent(A).create();
		d.update(R_MASTER

		Transport t = Transport.open(src
		try {
			t.fetch(PM
			assertEquals(B.copy()
		} finally {
			t.close();
		}

		RevCommit P = d.commit(d.tree(d.file("b"
		d.update(R_PRIVATE
	}

	public void testSuccess() throws Exception {
		final byte[] hdr = new byte[4];
		TemporaryBuffer.Heap tinyPack = new TemporaryBuffer.Heap(4096);
		tinyPack.write(Constants.PACK_SIGNATURE);
		NB.encodeInt32(hdr
		tinyPack.write(hdr
		tinyPack.write(hdr

		tinyPack.write((Constants.OBJ_BLOB) << 4 | 1);
		deflate(tinyPack

		tinyPack.write((Constants.OBJ_REF_DELTA) << 4 | 4);
		littlea.copyRawTo(tinyPack);
		deflate(tinyPack

		digest(tinyPack);
		openPack(tinyPack);

		TestRepository s = new TestRepository(src);
		RevBlob littleb = s.blob("b");
		RevCommit N = s.commit().parent(B).add("q"
		s.update(R_MASTER

		ObjectDirectory od = (ObjectDirectory) src.getObjectDatabase();
		assertFalse("b not loose"
		assertTrue("has b"

		TransportLocal t = new TransportLocal(src
			@Override
			ReceivePack createReceivePack(final Repository db) {
				final ReceivePack rp = super.createReceivePack(dst);
				rp.setCheckReceivedObjects(true);
				rp.setEnsureProvidedObjectsVisible(true);
				rp.setRefFilter(new RefFilter() {
					public Map<String
						Map<String
						assertNotNull(r.remove(R_PRIVATE));
						return r;
					}
				});
				return rp;
			}
		};
				src
				R_MASTER
				R_MASTER
				false
				null
		);
		PushResult r;
		try {
			t.setPushThin(true);
			r = t.push(PM
		} finally {
			t.close();
		}

		assertNotNull("have result"
		assertNull("private not advertised"
		assertSame("master updated"
		assertEquals(N.copy()
	}

	private void deflate(TemporaryBuffer.Heap tinyPack
			throws IOException {
		final Deflater deflater = new Deflater();
		final byte[] buf = new byte[128];
		deflater.setInput(content
		deflater.finish();
		do {
			final int n = deflater.deflate(buf
			if (n > 0)
				tinyPack.write(buf
		} while (!deflater.finished());
	}

	private void digest(TemporaryBuffer.Heap buf) throws IOException {
		MessageDigest md = Constants.newMessageDigest();
		md.update(buf.toByteArray());
		buf.write(md.digest());
	}

	private void openPack(TemporaryBuffer.Heap buf) throws IOException {
		final byte[] raw = buf.toByteArray();
		IndexPack ip = IndexPack.create(src
		ip.index(PM);
		ip.renameAndOpenPack();
	}

	private static URIish uriOf(Repository r) throws URISyntaxException {
		return new URIish(r.getDirectory().getAbsolutePath());
	}
}
