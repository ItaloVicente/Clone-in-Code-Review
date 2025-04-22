
package org.eclipse.jgit.internal.storage.reftable;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefComparator;
import org.eclipse.jgit.lib.SymbolicRef;
import org.junit.Test;

public class ReftableTest {
	private static final String MASTER = "refs/heads/master";
	private static final String V1_0 = "refs/tags/v1.0";

	@Test
	public void emptyTable() throws IOException {
		byte[] table = write();
		assertEquals('\1'
		assertEquals('R'
		assertEquals('E'
		assertEquals('F'
		assertEquals(0x01
		assertTrue(ReftableConstants.isFileHeaderMagic(table
		assertTrue(ReftableConstants.isFileHeaderMagic(table

		assertFalse(seekToFirstRef(table).next());
		assertFalse(seek(table
		assertFalse(seek(table

		assertFalse(seekToFirstLog(table).next());

		ReftableReader t = ReftableReader.emptyTable();
		t.seekToFirstRef();
		assertFalse(t.next());

		t.seek(HEAD);
		assertFalse(t.next());

		t.seekToFirstLog();
		assertFalse(t.next());
	}

	@Test
	public void oneIdRef() throws IOException {
		Ref exp = ref(MASTER
		byte[] table = write(exp);
		assertEquals(8 + 4 + 2 + MASTER.length() + 1 + 20 + 6 + 36

		ReftableReader r = seekToFirstRef(table);
		assertTrue(r.next());

		Ref act = r.getRef();
		assertNotNull(act);
		assertEquals(PACKED
		assertTrue(act.isPeeled());
		assertFalse(act.isSymbolic());
		assertEquals(exp.getName()
		assertEquals(exp.getObjectId()
		assertNull(act.getPeeledObjectId());
		assertFalse(r.next());

		r = seek(table
		assertTrue(r.next());
		act = r.getRef();
		assertNotNull(act);
		assertEquals(exp.getName()
		assertFalse(r.next());
	}

	@Test
	public void oneTagRef() throws IOException {
		Ref exp = tag(V1_0
		byte[] table = write(exp);
		assertEquals(8 + 4 + 2 + V1_0.length() + 1 + 40 + 6 + 36

		ReftableReader r = seekToFirstRef(table);
		assertTrue(r.next());
		Ref act = r.getRef();
		assertNotNull(act);
		assertEquals(PACKED
		assertTrue(act.isPeeled());
		assertFalse(act.isSymbolic());
		assertEquals(exp.getName()
		assertEquals(exp.getObjectId()
		assertEquals(exp.getPeeledObjectId()
	}

	@Test
	public void oneSymbolicRef() throws IOException {
		Ref exp = sym(HEAD
		byte[] table = write(exp);
		assertEquals(
				8 + 4 + 2 + HEAD.length() + 2 + MASTER.length() + 6 + 36
				table.length);

		ReftableReader r = seekToFirstRef(table);
		assertTrue(r.next());
		Ref act = r.getRef();
		assertNotNull(act);
		assertTrue(act.isSymbolic());
		assertEquals(exp.getName()
		assertNotNull(act.getLeaf());
		assertEquals(MASTER
		assertNull(act.getObjectId());
	}

	@Test
	public void oneDeletedRef() throws IOException {
		String name = "refs/heads/gone";
		Ref exp = newRef(name);
		byte[] table = write(exp);
		assertEquals(8 + 4 + 2 + name.length() + 1 + 6 + 36

		ReftableReader r = seekToFirstRef(table);
		r.setIncludeDeletes(true);
		assertTrue(r.next());
		Ref act = r.getRef();
		assertNotNull(act);
		assertFalse(act.isSymbolic());
		assertEquals(name
		assertEquals(NEW
		assertNull(act.getObjectId());
	}

	@Test
	public void seekNotFound() throws IOException {
		Ref exp = ref(MASTER
		ReftableReader r = read(write(exp));
		r.seek("refs/heads/a");
		assertFalse(r.next());

		r.seek("refs/heads/n");
		assertFalse(r.next());
	}

	public void unpeeledDoesNotWrite() {
		try {
			write(new ObjectIdRef.Unpeeled(PACKED
			fail("expected IOException");
		} catch (IOException e) {
			assertEquals(JGitText.get().peeledRefIsRequired
		}
	}

	@Test
	public void badCrc32() throws IOException {
		byte[] table = write();
		table[table.length - 1] = 0x42;

		try {
			assertFalse(seek(table
			fail("expected IOException");
		} catch (IOException e) {
			assertEquals(JGitText.get().invalidReftableCRC
		}
	}


	private static Ref ref(String name
		return new ObjectIdRef.PeeledNonTag(PACKED
	}

	private static Ref tag(String name
		return new ObjectIdRef.PeeledTag(PACKED
	}

	private static Ref sym(String name
		return new SymbolicRef(name
	}

	private static Ref newRef(String name) {
		return new ObjectIdRef.Unpeeled(NEW
	}

	private static ObjectId id(int i) {
		byte[] buf = new byte[OBJECT_ID_LENGTH];
		buf[0] = (byte) (i & 0xff);
		buf[1] = (byte) ((i >>> 8) & 0xff);
		buf[2] = (byte) ((i >>> 16) & 0xff);
		buf[3] = (byte) (i >>> 24);
		return ObjectId.fromRaw(buf);
	}

	private static ReftableReader seekToFirstRef(byte[] table)
			throws IOException {
		ReftableReader r = read(table);
		r.seekToFirstRef();
		return r;
	}

	private static ReftableReader seek(byte[] table
			throws IOException {
		ReftableReader r = read(table);
		r.seek(name);
		return r;
	}

	private static ReftableReader seekToFirstLog(byte[] table)
			throws IOException {
		ReftableReader r = read(table);
		r.seekToFirstLog();
		return r;
	}

	private static ReftableReader read(byte[] table) {
		return new ReftableReader(BlockSource.from(table));
	}

	private static byte[] write(Ref... refs) throws IOException {
		return write(Arrays.asList(refs));
	}

	private static byte[] write(Collection<Ref> refs) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter().begin(buffer);
		for (Ref r : RefComparator.sort(refs)) {
			writer.writeRef(r);
		}
		writer.finish();
		return buffer.toByteArray();
	}
}
