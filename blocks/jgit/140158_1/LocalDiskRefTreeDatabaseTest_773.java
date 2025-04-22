
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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.internal.storage.reftable.ReftableWriter.Stats;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.SymbolicRef;
import org.junit.Test;

public class ReftableTest {
	private static final String MASTER = "refs/heads/master";
	private static final String NEXT = "refs/heads/next";
	private static final String V1_0 = "refs/tags/v1.0";

	private Stats stats;

	@Test
	public void emptyTable() throws IOException {
		byte[] table = write();
		assertEquals('R'
		assertEquals('E'
		assertEquals('F'
		assertEquals('T'
		assertEquals(0x01
		assertTrue(ReftableConstants.isFileHeaderMagic(table
		assertTrue(ReftableConstants.isFileHeaderMagic(table

		Reftable t = read(table);
		try (RefCursor rc = t.allRefs()) {
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.seekRef(HEAD)) {
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.seekRefsWithPrefix(R_HEADS)) {
			assertFalse(rc.next());
		}
		try (LogCursor rc = t.allLogs()) {
			assertFalse(rc.next());
		}
	}

	@Test
	public void emptyVirtualTableFromRefs() throws IOException {
		Reftable t = Reftable.from(Collections.emptyList());
		try (RefCursor rc = t.allRefs()) {
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.seekRef(HEAD)) {
			assertFalse(rc.next());
		}
		try (LogCursor rc = t.allLogs()) {
			assertFalse(rc.next());
		}
	}

	@Test
	public void estimateCurrentBytesOneRef() throws IOException {
		Ref exp = ref(MASTER
		int expBytes = 24 + 4 + 5 + 4 + MASTER.length() + 20 + 68;

		byte[] table;
		ReftableConfig cfg = new ReftableConfig();
		cfg.setIndexObjects(false);
		ReftableWriter writer = new ReftableWriter().setConfig(cfg);
		try (ByteArrayOutputStream buf = new ByteArrayOutputStream()) {
			writer.begin(buf);
			assertEquals(92
			writer.writeRef(exp);
			assertEquals(expBytes
			writer.finish();
			table = buf.toByteArray();
		}
		assertEquals(expBytes
	}

	@SuppressWarnings("boxing")
	@Test
	public void estimateCurrentBytesWithIndex() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 5670; i++) {
			refs.add(ref(String.format("refs/heads/%04d"
		}

		ReftableConfig cfg = new ReftableConfig();
		cfg.setIndexObjects(false);
		cfg.setMaxIndexLevels(1);

		int expBytes = 147860;
		byte[] table;
		ReftableWriter writer = new ReftableWriter().setConfig(cfg);
		try (ByteArrayOutputStream buf = new ByteArrayOutputStream()) {
			writer.begin(buf);
			writer.sortAndWriteRefs(refs);
			assertEquals(expBytes
			writer.finish();
			stats = writer.getStats();
			table = buf.toByteArray();
		}
		assertEquals(1
		assertEquals(expBytes
	}

	@Test
	public void oneIdRef() throws IOException {
		Ref exp = ref(MASTER
		byte[] table = write(exp);
		assertEquals(24 + 4 + 5 + 4 + MASTER.length() + 20 + 68

		ReftableReader t = read(table);
		try (RefCursor rc = t.allRefs()) {
			assertTrue(rc.next());
			Ref act = rc.getRef();
			assertNotNull(act);
			assertEquals(PACKED
			assertTrue(act.isPeeled());
			assertFalse(act.isSymbolic());
			assertEquals(exp.getName()
			assertEquals(exp.getObjectId()
			assertEquals(0
			assertNull(act.getPeeledObjectId());
			assertFalse(rc.wasDeleted());
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.seekRef(MASTER)) {
			assertTrue(rc.next());
			Ref act = rc.getRef();
			assertNotNull(act);
			assertEquals(exp.getName()
			assertEquals(0
			assertFalse(rc.next());
		}
	}

	@Test
	public void oneTagRef() throws IOException {
		Ref exp = tag(V1_0
		byte[] table = write(exp);
		assertEquals(24 + 4 + 5 + 3 + V1_0.length() + 40 + 68

		ReftableReader t = read(table);
		try (RefCursor rc = t.allRefs()) {
			assertTrue(rc.next());
			Ref act = rc.getRef();
			assertNotNull(act);
			assertEquals(PACKED
			assertTrue(act.isPeeled());
			assertFalse(act.isSymbolic());
			assertEquals(exp.getName()
			assertEquals(exp.getObjectId()
			assertEquals(exp.getPeeledObjectId()
			assertEquals(0
		}
	}

	@Test
	public void oneSymbolicRef() throws IOException {
		Ref exp = sym(HEAD
		byte[] table = write(exp);
		assertEquals(
				24 + 4 + 5 + 2 + HEAD.length() + 2 + MASTER.length() + 68
				table.length);

		ReftableReader t = read(table);
		try (RefCursor rc = t.allRefs()) {
			assertTrue(rc.next());
			Ref act = rc.getRef();
			assertNotNull(act);
			assertTrue(act.isSymbolic());
			assertEquals(exp.getName()
			assertNotNull(act.getLeaf());
			assertEquals(MASTER
			assertNull(act.getObjectId());
			assertEquals(0
		}
	}

	@Test
	public void resolveSymbolicRef() throws IOException {
		Reftable t = read(write(
				sym(HEAD
				sym("refs/heads/tmp"
				ref(MASTER

		Ref head = t.exactRef(HEAD);
		assertNull(head.getObjectId());
		assertEquals("refs/heads/tmp"
		assertEquals(0

		head = t.resolve(head);
		assertNotNull(head);
		assertEquals(id(1)
		assertEquals(0

		Ref master = t.exactRef(MASTER);
		assertNotNull(master);
		assertSame(master
		assertEquals(0
	}

	@Test
	public void failDeepChainOfSymbolicRef() throws IOException {
		Reftable t = read(write(
				sym(HEAD
				sym("refs/heads/1"
				sym("refs/heads/2"
				sym("refs/heads/3"
				sym("refs/heads/4"
				sym("refs/heads/5"
				ref(MASTER

		Ref head = t.exactRef(HEAD);
		assertNull(head.getObjectId());
		assertNull(t.resolve(head));
	}

	@Test
	public void oneDeletedRef() throws IOException {
		String name = "refs/heads/gone";
		Ref exp = newRef(name);
		byte[] table = write(exp);
		assertEquals(24 + 4 + 5 + 3 + name.length() + 68

		ReftableReader t = read(table);
		try (RefCursor rc = t.allRefs()) {
			assertFalse(rc.next());
		}

		t.setIncludeDeletes(true);
		try (RefCursor rc = t.allRefs()) {
			assertTrue(rc.next());
			Ref act = rc.getRef();
			assertNotNull(act);
			assertFalse(act.isSymbolic());
			assertEquals(name
			assertEquals(NEW
			assertNull(act.getObjectId());
			assertTrue(rc.wasDeleted());
		}
	}

	@Test
	public void seekNotFound() throws IOException {
		Ref exp = ref(MASTER
		ReftableReader t = read(write(exp));
		try (RefCursor rc = t.seekRef("refs/heads/a")) {
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.seekRef("refs/heads/n")) {
			assertFalse(rc.next());
		}
	}

	@Test
	public void namespaceNotFound() throws IOException {
		Ref exp = ref(MASTER
		ReftableReader t = read(write(exp));
		try (RefCursor rc = t.seekRefsWithPrefix("refs/changes/")) {
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.seekRefsWithPrefix("refs/tags/")) {
			assertFalse(rc.next());
		}
	}

	@Test
	public void namespaceHeads() throws IOException {
		Ref master = ref(MASTER
		Ref next = ref(NEXT
		Ref v1 = tag(V1_0

		ReftableReader t = read(write(master
		try (RefCursor rc = t.seekRefsWithPrefix("refs/tags/")) {
			assertTrue(rc.next());
			assertEquals(V1_0
			assertEquals(0
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.seekRefsWithPrefix("refs/heads/")) {
			assertTrue(rc.next());
			assertEquals(MASTER
			assertEquals(0

			assertTrue(rc.next());
			assertEquals(NEXT
			assertEquals(0

			assertFalse(rc.next());
		}
	}

	@SuppressWarnings("boxing")
	@Test
	public void indexScan() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 5670; i++) {
			refs.add(ref(String.format("refs/heads/%04d"
		}

		byte[] table = write(refs);
		assertTrue(stats.refIndexLevels() > 0);
		assertTrue(stats.refIndexSize() > 0);
		assertScan(refs
	}

	@SuppressWarnings("boxing")
	@Test
	public void indexSeek() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 5670; i++) {
			refs.add(ref(String.format("refs/heads/%04d"
		}

		byte[] table = write(refs);
		assertTrue(stats.refIndexLevels() > 0);
		assertTrue(stats.refIndexSize() > 0);
		assertSeek(refs
	}

	@SuppressWarnings("boxing")
	@Test
	public void noIndexScan() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 567; i++) {
			refs.add(ref(String.format("refs/heads/%03d"
		}

		byte[] table = write(refs);
		assertEquals(0
		assertEquals(0
		assertEquals(table.length
		assertScan(refs
	}

	@SuppressWarnings("boxing")
	@Test
	public void noIndexSeek() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 567; i++) {
			refs.add(ref(String.format("refs/heads/%03d"
		}

		byte[] table = write(refs);
		assertEquals(0
		assertSeek(refs
	}

	@Test
	public void withReflog() throws IOException {
		Ref master = ref(MASTER
		Ref next = ref(NEXT
		PersonIdent who = new PersonIdent("Log"
		String msg = "test";

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter()
				.setMinUpdateIndex(1)
				.setMaxUpdateIndex(1)
				.begin(buffer);

		writer.writeRef(master);
		writer.writeRef(next);

		writer.writeLog(MASTER
		writer.writeLog(NEXT

		writer.finish();
		byte[] table = buffer.toByteArray();
		assertEquals(247

		ReftableReader t = read(table);
		try (RefCursor rc = t.allRefs()) {
			assertTrue(rc.next());
			assertEquals(MASTER
			assertEquals(id(1)
			assertEquals(1

			assertTrue(rc.next());
			assertEquals(NEXT
			assertEquals(id(2)
			assertEquals(1
			assertFalse(rc.next());
		}
		try (LogCursor lc = t.allLogs()) {
			assertTrue(lc.next());
			assertEquals(MASTER
			assertEquals(1
			assertEquals(ObjectId.zeroId()
			assertEquals(id(1)
			assertEquals(who
			assertEquals(msg

			assertTrue(lc.next());
			assertEquals(NEXT
			assertEquals(1
			assertEquals(ObjectId.zeroId()
			assertEquals(id(2)
			assertEquals(who
			assertEquals(msg

			assertFalse(lc.next());
		}
	}

	@Test
	public void onlyReflog() throws IOException {
		PersonIdent who = new PersonIdent("Log"
		String msg = "test";

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter()
				.setMinUpdateIndex(1)
				.setMaxUpdateIndex(1)
				.begin(buffer);
		writer.writeLog(MASTER
		writer.writeLog(NEXT
		writer.finish();
		byte[] table = buffer.toByteArray();
		stats = writer.getStats();
		assertEquals(170
		assertEquals(0
		assertEquals(0
		assertEquals(0

		ReftableReader t = read(table);
		try (RefCursor rc = t.allRefs()) {
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.seekRefsWithPrefix("refs/heads/")) {
			assertFalse(rc.next());
		}
		try (LogCursor lc = t.allLogs()) {
			assertTrue(lc.next());
			assertEquals(MASTER
			assertEquals(1
			assertEquals(ObjectId.zeroId()
			assertEquals(id(1)
			assertEquals(who
			assertEquals(msg

			assertTrue(lc.next());
			assertEquals(NEXT
			assertEquals(1
			assertEquals(ObjectId.zeroId()
			assertEquals(id(2)
			assertEquals(who
			assertEquals(msg

			assertFalse(lc.next());
		}
	}

	@SuppressWarnings("boxing")
	@Test
	public void logScan() throws IOException {
		ReftableConfig cfg = new ReftableConfig();
		cfg.setRefBlockSize(256);
		cfg.setLogBlockSize(2048);

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter(cfg);
		writer.setMinUpdateIndex(1).setMaxUpdateIndex(1).begin(buffer);

		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 5670; i++) {
			Ref ref = ref(String.format("refs/heads/%03d"
			refs.add(ref);
			writer.writeRef(ref);
		}

		PersonIdent who = new PersonIdent("Log"
		for (Ref ref : refs) {
			writer.writeLog(ref.getName()
					ObjectId.zeroId()
					"create " + ref.getName());
		}
		writer.finish();
		stats = writer.getStats();
		assertTrue(stats.logBytes() > 4096);
		byte[] table = buffer.toByteArray();

		ReftableReader t = read(table);
		try (LogCursor lc = t.allLogs()) {
			for (Ref exp : refs) {
				assertTrue("has " + exp.getName()
				assertEquals(exp.getName()
				ReflogEntry entry = lc.getReflogEntry();
				assertNotNull(entry);
				assertEquals(who
				assertEquals(ObjectId.zeroId()
				assertEquals(exp.getObjectId()
				assertEquals("create " + exp.getName()
			}
			assertFalse(lc.next());
		}
	}

	@SuppressWarnings("boxing")
	@Test
	public void byObjectIdOneRefNoIndex() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 200; i++) {
			refs.add(ref(String.format("refs/heads/%02d"
		}
		refs.add(ref("refs/heads/master"

		ReftableReader t = read(write(refs));
		assertEquals(0

		try (RefCursor rc = t.byObjectId(id(42))) {
			assertTrue("has 42"
			assertEquals("refs/heads/42"
			assertEquals(id(42)
			assertEquals(0
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.byObjectId(id(100))) {
			assertTrue("has 100"
			assertEquals("refs/heads/100"
			assertEquals(id(100)

			assertTrue("has master"
			assertEquals("refs/heads/master"
			assertEquals(id(100)
			assertEquals(0

			assertFalse(rc.next());
		}
	}

	@SuppressWarnings("boxing")
	@Test
	public void byObjectIdOneRefWithIndex() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 5200; i++) {
			refs.add(ref(String.format("refs/heads/%02d"
		}
		refs.add(ref("refs/heads/master"

		ReftableReader t = read(write(refs));
		assertTrue(stats.objIndexSize() > 0);

		try (RefCursor rc = t.byObjectId(id(42))) {
			assertTrue("has 42"
			assertEquals("refs/heads/42"
			assertEquals(id(42)
			assertEquals(0
			assertFalse(rc.next());
		}
		try (RefCursor rc = t.byObjectId(id(100))) {
			assertTrue("has 100"
			assertEquals("refs/heads/100"
			assertEquals(id(100)

			assertTrue("has master"
			assertEquals("refs/heads/master"
			assertEquals(id(100)
			assertEquals(0

			assertFalse(rc.next());
		}
	}

	@Test
	public void unpeeledDoesNotWrite() {
		try {
			write(new ObjectIdRef.Unpeeled(PACKED
			fail("expected IOException");
		} catch (IOException e) {
			assertEquals(JGitText.get().peeledRefIsRequired
		}
	}

	@Test
	public void nameTooLongDoesNotWrite() throws IOException {
		try {
			ReftableConfig cfg = new ReftableConfig();
			cfg.setRefBlockSize(64);

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			ReftableWriter writer = new ReftableWriter(cfg).begin(buffer);
			writer.writeRef(ref("refs/heads/i-am-not-a-teapot"
			writer.finish();
			fail("expected BlockSizeTooSmallException");
		} catch (BlockSizeTooSmallException e) {
			assertEquals(85
		}
	}

	@Test
	public void badCrc32() throws IOException {
		byte[] table = write();
		table[table.length - 1] = 0x42;

		try {
			read(table).seekRef(HEAD);
			fail("expected IOException");
		} catch (IOException e) {
			assertEquals(JGitText.get().invalidReftableCRC
		}
	}

	private static void assertScan(List<Ref> refs
			throws IOException {
		try (RefCursor rc = t.allRefs()) {
			for (Ref exp : refs) {
				assertTrue("has " + exp.getName()
				Ref act = rc.getRef();
				assertEquals(exp.getName()
				assertEquals(exp.getObjectId()
				assertEquals(0
			}
			assertFalse(rc.next());
		}
	}

	private static void assertSeek(List<Ref> refs
			throws IOException {
		for (Ref exp : refs) {
			try (RefCursor rc = t.seekRef(exp.getName())) {
				assertTrue("has " + exp.getName()
				Ref act = rc.getRef();
				assertEquals(exp.getName()
				assertEquals(exp.getObjectId()
				assertEquals(0
				assertFalse(rc.next());
			}
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

	private static ReftableReader read(byte[] table) {
		return new ReftableReader(BlockSource.from(table));
	}

	private byte[] write(Ref... refs) throws IOException {
		return write(Arrays.asList(refs));
	}

	private byte[] write(Collection<Ref> refs) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		stats = new ReftableWriter()
				.begin(buffer)
				.sortAndWriteRefs(refs)
				.finish()
				.getStats();
		return buffer.toByteArray();
	}
}
