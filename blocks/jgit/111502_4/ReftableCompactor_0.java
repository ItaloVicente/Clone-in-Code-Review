
package org.eclipse.jgit.internal.storage.reftable;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.internal.storage.reftable.ReftableWriter.Stats;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.junit.Test;

public class ReftableCompactorTest {
	private static final String MASTER = "refs/heads/master";
	private static final String NEXT = "refs/heads/next";

	@Test
	public void noTables() throws IOException {
		ReftableCompactor compactor = new ReftableCompactor();
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			compactor.compact(out);
		}
		Stats stats = compactor.getStats();
		assertEquals(0
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void oneTable() throws IOException {
		byte[] inTab;
		try (ByteArrayOutputStream inBuf = new ByteArrayOutputStream()) {
			ReftableWriter writer = new ReftableWriter()
				.setMinUpdateIndex(0)
				.setMaxUpdateIndex(0)
				.begin(inBuf);

			writer.writeRef(ref(MASTER
			writer.finish();
			inTab = inBuf.toByteArray();
		}

		byte[] outTab;
		ReftableCompactor compactor = new ReftableCompactor();
		try (ByteArrayOutputStream outBuf = new ByteArrayOutputStream()) {
			compactor.tryAddFirst(read(inTab));
			compactor.compact(outBuf);
			outTab = outBuf.toByteArray();
		}
		Stats stats = compactor.getStats();
		assertEquals(0
		assertEquals(0
		assertEquals(1

		ReftableReader rr = read(outTab);
		try (RefCursor rc = rr.allRefs()) {
			assertTrue(rc.next());
			assertEquals(MASTER
			assertEquals(id(1)
			assertEquals(0
		}
	}

	@Test
	public void twoTablesOneRef() throws IOException {
		byte[] inTab1;
		try (ByteArrayOutputStream inBuf = new ByteArrayOutputStream()) {
			ReftableWriter writer = new ReftableWriter()
				.setMinUpdateIndex(0)
				.setMaxUpdateIndex(0)
				.begin(inBuf);

			writer.writeRef(ref(MASTER
			writer.finish();
			inTab1 = inBuf.toByteArray();
		}

		byte[] inTab2;
		try (ByteArrayOutputStream inBuf = new ByteArrayOutputStream()) {
			ReftableWriter writer = new ReftableWriter()
				.setMinUpdateIndex(1)
				.setMaxUpdateIndex(1)
				.begin(inBuf);

			writer.writeRef(ref(MASTER
			writer.finish();
			inTab2 = inBuf.toByteArray();
		}

		byte[] outTab;
		ReftableCompactor compactor = new ReftableCompactor();
		try (ByteArrayOutputStream outBuf = new ByteArrayOutputStream()) {
			compactor.addAll(Arrays.asList(read(inTab1)
			compactor.compact(outBuf);
			outTab = outBuf.toByteArray();
		}
		Stats stats = compactor.getStats();
		assertEquals(0
		assertEquals(1
		assertEquals(1

		ReftableReader rr = read(outTab);
		try (RefCursor rc = rr.allRefs()) {
			assertTrue(rc.next());
			assertEquals(MASTER
			assertEquals(id(2)
			assertEquals(1
		}
	}

	@Test
	public void twoTablesTwoRefs() throws IOException {
		byte[] inTab1;
		try (ByteArrayOutputStream inBuf = new ByteArrayOutputStream()) {
			ReftableWriter writer = new ReftableWriter()
				.setMinUpdateIndex(0)
				.setMaxUpdateIndex(0)
				.begin(inBuf);

			writer.writeRef(ref(MASTER
			writer.writeRef(ref(NEXT
			writer.finish();
			inTab1 = inBuf.toByteArray();
		}

		byte[] inTab2;
		try (ByteArrayOutputStream inBuf = new ByteArrayOutputStream()) {
			ReftableWriter writer = new ReftableWriter()
				.setMinUpdateIndex(1)
				.setMaxUpdateIndex(1)
				.begin(inBuf);

			writer.writeRef(ref(MASTER
			writer.finish();
			inTab2 = inBuf.toByteArray();
		}

		byte[] outTab;
		ReftableCompactor compactor = new ReftableCompactor();
		try (ByteArrayOutputStream outBuf = new ByteArrayOutputStream()) {
			compactor.addAll(Arrays.asList(read(inTab1)
			compactor.compact(outBuf);
			outTab = outBuf.toByteArray();
		}
		Stats stats = compactor.getStats();
		assertEquals(0
		assertEquals(1
		assertEquals(2

		ReftableReader rr = read(outTab);
		try (RefCursor rc = rr.allRefs()) {
			assertTrue(rc.next());
			assertEquals(MASTER
			assertEquals(id(3)
			assertEquals(1

			assertTrue(rc.next());
			assertEquals(NEXT
			assertEquals(id(2)
			assertEquals(0
		}
	}

	private static Ref ref(String name
		return new ObjectIdRef.PeeledNonTag(PACKED
	}

	private static ObjectId id(int i) {
		byte[] buf = new byte[OBJECT_ID_LENGTH];
		buf[0] = (byte) (i & 0xff);
		buf[1] = (byte) ((i >>> 8) & 0xff);
		buf[2] = (byte) ((i >>> 16) & 0xff);
		buf[3] = (byte) (i >>> 24);
		return ObjectId.fromRaw(buf);
	}

	private static ReftableReader read(byte[] table) {
		return new ReftableReader(BlockSource.from(table));
	}
}
