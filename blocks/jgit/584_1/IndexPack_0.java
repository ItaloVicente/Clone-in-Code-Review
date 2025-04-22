
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
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.TemporaryBuffer;

public class ReceivePackRefFilterTest extends LocalDiskRepositoryTestCase {
	private static final NullProgressMonitor PM = NullProgressMonitor.INSTANCE;

	private static final String R_MASTER = Constants.R_HEADS + Constants.MASTER;

	private static final String R_PRIVATE = Constants.R_HEADS + "private";

	private Repository src;

	private Repository dst;

	private RevCommit A

	private RevBlob a

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		src = createBareRepository();
		dst = createBareRepository();

		TestRepository d = new TestRepository(dst);
		a = d.blob("a");
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

		b = d.blob("b");
		P = d.commit(d.tree(d.file("b"
		d.update(R_PRIVATE
	}

	public void testFilterHidesPrivate() throws Exception {
		Map<String
		TransportLocal t = new TransportLocal(src
			@Override
			ReceivePack createReceivePack(final Repository db) {
				db.close();
				dst.incrementOpen();

				final ReceivePack rp = super.createReceivePack(dst);
				rp.setRefFilter(new HidePrivateFilter());
				return rp;
			}
		};
		try {
			PushConnection c = t.openPush();
			try {
				refs = c.getRefsMap();
			} finally {
				c.close();
			}
		} finally {
			t.close();
		}

		assertNotNull(refs);
		assertNull("no private"
		assertNull("no HEAD"
		assertEquals(1

		Ref master = refs.get(R_MASTER);
		assertNotNull("has master"
		assertEquals(B.copy()
	}

	public void testSuccess() throws Exception {
		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);

		packHeader(pack
		pack.write((Constants.OBJ_BLOB) << 4 | 1);
		deflate(pack

		pack.write((Constants.OBJ_REF_DELTA) << 4 | 4);
		a.copyRawTo(pack);
		deflate(pack

		digest(pack);
		openPack(pack);

		ObjectDirectory od = (ObjectDirectory) src.getObjectDatabase();
		assertTrue("has b"
		assertFalse("b not loose"

		TestRepository s = new TestRepository(src);
		RevCommit N = s.commit().parent(B).add("q"
		s.update(R_MASTER

		TransportLocal t = new TransportLocal(src
			@Override
			ReceivePack createReceivePack(final Repository db) {
				db.close();
				dst.incrementOpen();

				final ReceivePack rp = super.createReceivePack(dst);
				rp.setCheckReceivedObjects(true);
				rp.setCheckReferencedObjectsAreReachable(true);
				rp.setRefFilter(new HidePrivateFilter());
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

	public void testCreateBranchAtHiddenCommitFails() throws Exception {
		final TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64);
		packHeader(pack
		digest(pack);

		final TemporaryBuffer.Heap inBuf = new TemporaryBuffer.Heap(256);
		final PacketLineOut inPckLine = new PacketLineOut(inBuf);
		inPckLine.writeString(ObjectId.zeroId().name() + ' ' + P.name() + ' '
				+ "refs/heads/s" + '\0'
				+ BasePackPushConnection.CAPABILITY_REPORT_STATUS);
		inPckLine.end();
		pack.writeTo(inBuf

		final TemporaryBuffer.Heap outBuf = new TemporaryBuffer.Heap(1024);
		final ReceivePack rp = new ReceivePack(dst);
		rp.setCheckReceivedObjects(true);
		rp.setCheckReferencedObjectsAreReachable(true);
		rp.setRefFilter(new HidePrivateFilter());
		rp.receive(new ByteArrayInputStream(inBuf.toByteArray())

		final PacketLineIn r = asPacketLineIn(outBuf);
		String master = r.readString();
		int nul = master.indexOf('\0');
		assertTrue("has capability list"
		assertEquals(B.name() + ' ' + R_MASTER
		assertSame(PacketLineIn.END

		assertEquals("unpack error Missing commit " + P.name()
		assertEquals("ng refs/heads/s n/a (unpacker error)"
		assertSame(PacketLineIn.END
	}

	public void testUsingHiddenDeltaBaseFails() throws Exception {
		final TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64);
		packHeader(pack
		pack.write((Constants.OBJ_REF_DELTA) << 4 | 4);
		b.copyRawTo(pack);
		deflate(pack
		digest(pack);

		final TemporaryBuffer.Heap inBuf = new TemporaryBuffer.Heap(256);
		final PacketLineOut inPckLine = new PacketLineOut(inBuf);
		inPckLine.writeString(ObjectId.zeroId().name() + ' ' + P.name() + ' '
				+ "refs/heads/s" + '\0'
				+ BasePackPushConnection.CAPABILITY_REPORT_STATUS);
		inPckLine.end();
		pack.writeTo(inBuf

		final TemporaryBuffer.Heap outBuf = new TemporaryBuffer.Heap(1024);
		final ReceivePack rp = new ReceivePack(dst);
		rp.setCheckReceivedObjects(true);
		rp.setCheckReferencedObjectsAreReachable(true);
		rp.setRefFilter(new HidePrivateFilter());
		rp.receive(new ByteArrayInputStream(inBuf.toByteArray())

		final PacketLineIn r = asPacketLineIn(outBuf);
		String master = r.readString();
		int nul = master.indexOf('\0');
		assertTrue("has capability list"
		assertEquals(B.name() + ' ' + R_MASTER
		assertSame(PacketLineIn.END

		assertEquals("unpack error Missing blob " + b.name()
		assertEquals("ng refs/heads/s n/a (unpacker error)"
		assertSame(PacketLineIn.END
	}

	public void testUsingHiddenCommonBlobFails() throws Exception {
		TestRepository s = new TestRepository(src);
		RevCommit N = s.commit().parent(B).add("q"

		final TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64);
		packHeader(pack
		copy(pack
		copy(pack
		digest(pack);

		final TemporaryBuffer.Heap inBuf = new TemporaryBuffer.Heap(256);
		final PacketLineOut inPckLine = new PacketLineOut(inBuf);
		inPckLine.writeString(ObjectId.zeroId().name() + ' ' + N.name() + ' '
				+ "refs/heads/s" + '\0'
				+ BasePackPushConnection.CAPABILITY_REPORT_STATUS);
		inPckLine.end();
		pack.writeTo(inBuf

		final TemporaryBuffer.Heap outBuf = new TemporaryBuffer.Heap(1024);
		final ReceivePack rp = new ReceivePack(dst);
		rp.setCheckReceivedObjects(true);
		rp.setCheckReferencedObjectsAreReachable(true);
		rp.setRefFilter(new HidePrivateFilter());
		rp.receive(new ByteArrayInputStream(inBuf.toByteArray())

		final PacketLineIn r = asPacketLineIn(outBuf);
		String master = r.readString();
		int nul = master.indexOf('\0');
		assertTrue("has capability list"
		assertEquals(B.name() + ' ' + R_MASTER
		assertSame(PacketLineIn.END

		assertEquals("unpack error Missing blob " + b.name()
		assertEquals("ng refs/heads/s n/a (unpacker error)"
		assertSame(PacketLineIn.END
	}

	public void testUsingUnknownBlobFails() throws Exception {
		TestRepository s = new TestRepository(src);
		RevBlob n = s.blob("n");
		RevCommit N = s.commit().parent(B).add("q"

		final TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64);
		packHeader(pack
		copy(pack
		copy(pack
		digest(pack);

		final TemporaryBuffer.Heap inBuf = new TemporaryBuffer.Heap(256);
		final PacketLineOut inPckLine = new PacketLineOut(inBuf);
		inPckLine.writeString(ObjectId.zeroId().name() + ' ' + N.name() + ' '
				+ "refs/heads/s" + '\0'
				+ BasePackPushConnection.CAPABILITY_REPORT_STATUS);
		inPckLine.end();
		pack.writeTo(inBuf

		final TemporaryBuffer.Heap outBuf = new TemporaryBuffer.Heap(1024);
		final ReceivePack rp = new ReceivePack(dst);
		rp.setCheckReceivedObjects(true);
		rp.setCheckReferencedObjectsAreReachable(true);
		rp.setRefFilter(new HidePrivateFilter());
		rp.receive(new ByteArrayInputStream(inBuf.toByteArray())

		final PacketLineIn r = asPacketLineIn(outBuf);
		String master = r.readString();
		int nul = master.indexOf('\0');
		assertTrue("has capability list"
		assertEquals(B.name() + ' ' + R_MASTER
		assertSame(PacketLineIn.END

		assertEquals("unpack error Missing blob " + n.name()
		assertEquals("ng refs/heads/s n/a (unpacker error)"
		assertSame(PacketLineIn.END
	}

	public void testUsingUnknownTreeFails() throws Exception {
		TestRepository s = new TestRepository(src);
		RevCommit N = s.commit().parent(B).add("q"
		RevTree t = s.parseBody(N).getTree();

		final TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64);
		packHeader(pack
		copy(pack
		digest(pack);

		final TemporaryBuffer.Heap inBuf = new TemporaryBuffer.Heap(256);
		final PacketLineOut inPckLine = new PacketLineOut(inBuf);
		inPckLine.writeString(ObjectId.zeroId().name() + ' ' + N.name() + ' '
				+ "refs/heads/s" + '\0'
				+ BasePackPushConnection.CAPABILITY_REPORT_STATUS);
		inPckLine.end();
		pack.writeTo(inBuf

		final TemporaryBuffer.Heap outBuf = new TemporaryBuffer.Heap(1024);
		final ReceivePack rp = new ReceivePack(dst);
		rp.setCheckReceivedObjects(true);
		rp.setCheckReferencedObjectsAreReachable(true);
		rp.setRefFilter(new HidePrivateFilter());
		rp.receive(new ByteArrayInputStream(inBuf.toByteArray())

		final PacketLineIn r = asPacketLineIn(outBuf);
		String master = r.readString();
		int nul = master.indexOf('\0');
		assertTrue("has capability list"
		assertEquals(B.name() + ' ' + R_MASTER
		assertSame(PacketLineIn.END

		assertEquals("unpack error Missing tree " + t.name()
		assertEquals("ng refs/heads/s n/a (unpacker error)"
		assertSame(PacketLineIn.END
	}

	private void packHeader(TemporaryBuffer.Heap tinyPack
			throws IOException {
		final byte[] hdr = new byte[8];
		NB.encodeInt32(hdr
		NB.encodeInt32(hdr

		tinyPack.write(Constants.PACK_SIGNATURE);
		tinyPack.write(hdr
	}

	private void copy(TemporaryBuffer.Heap tinyPack
			throws IOException {
		final byte[] buf = new byte[64];
		final byte[] content = ldr.getCachedBytes();
		int dataLength = content.length;
		int nextLength = dataLength >>> 4;
		int size = 0;
		buf[size++] = (byte) ((nextLength > 0 ? 0x80 : 0x00)
				| (ldr.getType() << 4) | (dataLength & 0x0F));
		dataLength = nextLength;
		while (dataLength > 0) {
			nextLength >>>= 7;
			buf[size++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (dataLength & 0x7F));
			dataLength = nextLength;
		}
		tinyPack.write(buf
		deflate(tinyPack
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
		ip.setFixThin(true);
		ip.index(PM);
		ip.renameAndOpenPack();
	}

	private static PacketLineIn asPacketLineIn(TemporaryBuffer.Heap buf)
			throws IOException {
		return new PacketLineIn(new ByteArrayInputStream(buf.toByteArray()));
	}

	private static final class HidePrivateFilter implements RefFilter {
		public Map<String
			Map<String
			assertNotNull(r.remove(R_PRIVATE));
			return r;
		}
	}

	private static URIish uriOf(Repository r) throws URISyntaxException {
		return new URIish(r.getDirectory().getAbsolutePath());
	}
}
