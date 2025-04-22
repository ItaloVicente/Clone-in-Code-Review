
package org.eclipse.jgit.storage.dht;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.zip.Deflater;

import static org.eclipse.jgit.lib.Constants.*;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.storage.dht.spi.memory.MemoryDatabase;
import org.eclipse.jgit.storage.file.PackLock;
import org.eclipse.jgit.storage.pack.DeltaEncoder;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.junit.Before;
import org.junit.Test;

public class DhtPackParserTest {
	private MemoryDatabase db;

	@Before
	public void setUpDatabase() {
		db = new MemoryDatabase();
	}

	@Test
	public void testParse() throws IOException {
		DhtRepository repo = db.open("test.git");
		repo.create(true);

		ObjectInserter.Formatter fmt = new ObjectInserter.Formatter();
		byte[] data0 = new byte[512];
		Arrays.fill(data0
		ObjectId id0 = fmt.idFor(OBJ_BLOB

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64 * 1024);
		packHeader(pack
		objectHeader(pack
		deflate(pack

		byte[] data1 = clone(0x01
		byte[] delta1 = delta(data0
		ObjectId id1 = fmt.idFor(OBJ_BLOB
		objectHeader(pack
		id0.copyRawTo(pack);
		deflate(pack

		byte[] data2 = clone(0x02
		byte[] delta2 = delta(data1
		ObjectId id2 = fmt.idFor(OBJ_BLOB
		objectHeader(pack
		id1.copyRawTo(pack);
		deflate(pack

		byte[] data3 = clone(0x03
		byte[] delta3 = delta(data2
		ObjectId id3 = fmt.idFor(OBJ_BLOB
		objectHeader(pack
		id2.copyRawTo(pack);
		deflate(pack
		digest(pack);

		ObjectInserter ins = repo.newObjectInserter();
		try {
			InputStream is = new ByteArrayInputStream(pack.toByteArray());
			DhtPackParser p = (DhtPackParser) ins.newPackParser(is);
			PackLock lock = p.parse(NullProgressMonitor.INSTANCE);
			assertNull(lock);
		} finally {
			ins.release();
		}

		ObjectReader ctx = repo.newObjectReader();
		try {
			assertTrue(ctx.has(id0
			assertTrue(ctx.has(id1
			assertTrue(ctx.has(id2
			assertTrue(ctx.has(id3
		} finally {
			ctx.release();
		}
	}

	private byte[] clone(int first
		byte[] r = new byte[base.length];
		System.arraycopy(base
		r[0] = (byte) first;
		return r;
	}

	private byte[] delta(byte[] base
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		DeltaEncoder de = new DeltaEncoder(tmp
		de.insert(dest
		de.copy(1
		return tmp.toByteArray();
	}

	private void packHeader(TemporaryBuffer.Heap pack
			throws IOException {
		final byte[] hdr = new byte[8];
		NB.encodeInt32(hdr
		NB.encodeInt32(hdr
		pack.write(PACK_SIGNATURE);
		pack.write(hdr
	}

	private void objectHeader(TemporaryBuffer.Heap pack
			throws IOException {
		byte[] buf = new byte[8];
		int nextLength = sz >>> 4;
		buf[0] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (type << 4) | (sz & 0x0F));
		sz = nextLength;
		int n = 1;
		while (sz > 0) {
			nextLength >>>= 7;
			buf[n++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (sz & 0x7F));
			sz = nextLength;
		}
		pack.write(buf
	}

	private void deflate(TemporaryBuffer.Heap pack
			throws IOException {
		final Deflater deflater = new Deflater();
		final byte[] buf = new byte[128];
		deflater.setInput(content
		deflater.finish();
		do {
			final int n = deflater.deflate(buf
			if (n > 0)
				pack.write(buf
		} while (!deflater.finished());
		deflater.end();
	}

	private void digest(TemporaryBuffer.Heap buf) throws IOException {
		MessageDigest md = newMessageDigest();
		md.update(buf.toByteArray());
		buf.write(md.digest());
	}
}
