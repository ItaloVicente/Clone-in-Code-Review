
package org.eclipse.jgit.internal.storage.reftable;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.MASTER;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefComparator;
import org.eclipse.jgit.lib.SymbolicRef;
import org.junit.Test;

public class MergedReftableTest {
	@Test
	public void noTables() throws IOException {
		MergedReftable mr = merge(new byte[0][]);
		try (RefCursor rc = mr.allRefs()) {
			assertFalse(rc.next());
		}
		try (RefCursor rc = mr.seekRef(HEAD)) {
			assertFalse(rc.next());
		}
		try (RefCursor rc = mr.seekRefsWithPrefix(R_HEADS)) {
			assertFalse(rc.next());
		}
	}

	@Test
	public void oneEmptyTable() throws IOException {
		MergedReftable mr = merge(write());
		try (RefCursor rc = mr.allRefs()) {
			assertFalse(rc.next());
		}
		try (RefCursor rc = mr.seekRef(HEAD)) {
			assertFalse(rc.next());
		}
		try (RefCursor rc = mr.seekRefsWithPrefix(R_HEADS)) {
			assertFalse(rc.next());
		}
	}

	@Test
	public void twoEmptyTables() throws IOException {
		MergedReftable mr = merge(write()
		try (RefCursor rc = mr.allRefs()) {
			assertFalse(rc.next());
		}
		try (RefCursor rc = mr.seekRef(HEAD)) {
			assertFalse(rc.next());
		}
		try (RefCursor rc = mr.seekRefsWithPrefix(R_HEADS)) {
			assertFalse(rc.next());
		}
	}

	@SuppressWarnings("boxing")
	@Test
	public void oneTableScan() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 567; i++) {
			refs.add(ref(String.format("refs/heads/%03d"
		}

		MergedReftable mr = merge(write(refs));
		try (RefCursor rc = mr.allRefs()) {
			for (Ref exp : refs) {
				assertTrue("has " + exp.getName()
				Ref act = rc.getRef();
				assertEquals(exp.getName()
				assertEquals(exp.getObjectId()
				assertEquals(1
			}
			assertFalse(rc.next());
		}
	}

	@Test
	public void deleteIsHidden() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/apple"
				ref("refs/heads/master"
		List<Ref> delta2 = Arrays.asList(delete("refs/heads/apple"));

		MergedReftable mr = merge(write(delta1)
		try (RefCursor rc = mr.allRefs()) {
			assertTrue(rc.next());
			assertEquals("refs/heads/master"
			assertEquals(id(2)
			assertEquals(1
			assertFalse(rc.next());
		}
	}

	@Test
	public void twoTableSeek() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/apple"
				ref("refs/heads/master"
		List<Ref> delta2 = Arrays.asList(ref("refs/heads/banana"

		MergedReftable mr = merge(write(delta1)
		try (RefCursor rc = mr.seekRef("refs/heads/master")) {
			assertTrue(rc.next());
			assertEquals("refs/heads/master"
			assertEquals(id(2)
			assertFalse(rc.next());
			assertEquals(1
		}
	}

	@Test
	public void twoTableById() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/apple"
				ref("refs/heads/master"
		List<Ref> delta2 = Arrays.asList(ref("refs/heads/banana"

		MergedReftable mr = merge(write(delta1)
		try (RefCursor rc = mr.byObjectId(id(2))) {
			assertTrue(rc.next());
			assertEquals("refs/heads/master"
			assertEquals(id(2)
			assertEquals(1
			assertFalse(rc.next());
		}
	}

	@SuppressWarnings("boxing")
	@Test
	public void fourTableScan() throws IOException {
		List<Ref> base = new ArrayList<>();
		for (int i = 1; i <= 567; i++) {
			base.add(ref(String.format("refs/heads/%03d"
		}

		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/next"
				ref(String.format("refs/heads/%03d"
		List<Ref> delta2 = Arrays.asList(
				delete("refs/heads/next")
				ref(String.format("refs/heads/%03d"
		List<Ref> delta3 = Arrays.asList(
				ref("refs/heads/master"
				ref(String.format("refs/heads/%03d"
				ref(String.format("refs/heads/%03d"

		List<Ref> expected = merge(base
		MergedReftable mr = merge(
				write(base)
				write(delta1)
				write(delta2)
				write(delta3));
		try (RefCursor rc = mr.allRefs()) {
			for (Ref exp : expected) {
				assertTrue("has " + exp.getName()
				Ref act = rc.getRef();
				assertEquals(exp.getName()
				assertEquals(exp.getObjectId()
				assertEquals(1
			}
			assertFalse(rc.next());
		}
	}

	@Test
	public void scanDuplicates() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/apple"
				ref("refs/heads/banana"
		List<Ref> delta2 = Arrays.asList(
				ref("refs/heads/apple"
				ref("refs/heads/apple"

		MergedReftable mr = merge(write(delta1
		try (RefCursor rc = mr.allRefs()) {
			assertTrue(rc.next());
			assertEquals("refs/heads/apple"
			assertEquals(id(3)
			assertEquals(2000
			assertTrue(rc.next());
			assertEquals("refs/heads/banana"
			assertEquals(id(2)
			assertEquals(1000
			assertFalse(rc.next());
		}
	}

	@Test
	public void scanIncludeDeletes() throws IOException {
		List<Ref> delta1 = Arrays.asList(ref("refs/heads/next"
		List<Ref> delta2 = Arrays.asList(delete("refs/heads/next"));
		List<Ref> delta3 = Arrays.asList(ref("refs/heads/master"

		MergedReftable mr = merge(write(delta1)
		mr.setIncludeDeletes(true);
		try (RefCursor rc = mr.allRefs()) {
			assertTrue(rc.next());
			Ref r = rc.getRef();
			assertEquals("refs/heads/master"
			assertEquals(id(8)
			assertEquals(1

			assertTrue(rc.next());
			r = rc.getRef();
			assertEquals("refs/heads/next"
			assertEquals(NEW
			assertNull(r.getObjectId());
			assertEquals(1

			assertFalse(rc.next());
		}
	}

	@SuppressWarnings("boxing")
	@Test
	public void oneTableSeek() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 567; i++) {
			refs.add(ref(String.format("refs/heads/%03d"
		}

		MergedReftable mr = merge(write(refs));
		for (Ref exp : refs) {
			try (RefCursor rc = mr.seekRef(exp.getName())) {
				assertTrue("has " + exp.getName()
				Ref act = rc.getRef();
				assertEquals(exp.getName()
				assertEquals(exp.getObjectId()
				assertEquals(1
				assertFalse(rc.next());
			}
		}
	}

	@Test
	public void missedUpdate() throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		ReftableWriter writer = new ReftableWriter()
				.setMinUpdateIndex(1)
				.setMaxUpdateIndex(3)
				.begin(buf);
		writer.writeRef(ref("refs/heads/a"
		writer.writeRef(ref("refs/heads/c"
		writer.finish();
		byte[] base = buf.toByteArray();

		byte[] delta = write(Arrays.asList(
				ref("refs/heads/b"
				ref("refs/heads/c"
				2);
		MergedReftable mr = merge(base
		try (RefCursor rc = mr.allRefs()) {
			assertTrue(rc.next());
			assertEquals("refs/heads/a"
			assertEquals(id(1)
			assertEquals(1

			assertTrue(rc.next());
			assertEquals("refs/heads/b"
			assertEquals(id(2)
			assertEquals(2

			assertTrue(rc.next());
			assertEquals("refs/heads/c"
			assertEquals(id(3)
			assertEquals(3
		}
	}

	@Test
	public void compaction() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/next"
				ref("refs/heads/master"
		List<Ref> delta2 = Arrays.asList(delete("refs/heads/next"));
		List<Ref> delta3 = Arrays.asList(ref("refs/heads/master"

		ReftableCompactor compactor = new ReftableCompactor();
		compactor.addAll(Arrays.asList(
				read(write(delta1))
				read(write(delta2))
				read(write(delta3))));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		compactor.compact(out);
		byte[] table = out.toByteArray();

		ReftableReader reader = read(table);
		try (RefCursor rc = reader.allRefs()) {
			assertTrue(rc.next());
			Ref r = rc.getRef();
			assertEquals("refs/heads/master"
			assertEquals(id(8)
			assertFalse(rc.next());
		}
	}

	@Test
	public void versioningSymbolicReftargetMoves() throws IOException {
		Ref master = ref(MASTER

		List<Ref> delta1 = Arrays.asList(master
		List<Ref> delta2 = Arrays.asList(ref(MASTER

		MergedReftable mr = merge(write(delta1
		Ref head = mr.exactRef(HEAD);
		assertEquals(head.getUpdateIndex()

		Ref masterRef = mr.exactRef(MASTER);
		assertEquals(masterRef.getUpdateIndex()
	}

	@Test
	public void versioningSymbolicRefMoves() throws IOException {
		Ref branchX = ref("refs/heads/branchX"

		List<Ref> delta1 = Arrays.asList(ref(MASTER
				sym(HEAD
		List<Ref> delta2 = Arrays.asList(sym(HEAD
		List<Ref> delta3 = Arrays.asList(sym(HEAD

		MergedReftable mr = merge(write(delta1
				write(delta3
		Ref head = mr.exactRef(HEAD);
		assertEquals(head.getUpdateIndex()

		Ref masterRef = mr.exactRef(MASTER);
		assertEquals(masterRef.getUpdateIndex()

		Ref branchRef = mr.exactRef(MASTER);
		assertEquals(branchRef.getUpdateIndex()
	}

	@Test
	public void versioningResolveRef() throws IOException {
		List<Ref> delta1 = Arrays.asList(sym(HEAD
				sym("refs/heads/tmp"
		List<Ref> delta2 = Arrays.asList(ref(MASTER
		List<Ref> delta3 = Arrays.asList(ref(MASTER

		MergedReftable mr = merge(write(delta1
				write(delta3
		Ref head = mr.exactRef(HEAD);
		Ref resolvedHead = mr.resolve(head);
		assertEquals(resolvedHead.getObjectId()
		assertEquals("HEAD has not moved"

		Ref master = mr.exactRef(MASTER);
		Ref resolvedMaster = mr.resolve(master);
		assertEquals(resolvedMaster.getObjectId()
		assertEquals("master also has update index"
				resolvedMaster.getUpdateIndex()
	}

	private static MergedReftable merge(byte[]... table) {
		List<Reftable> stack = new ArrayList<>(table.length);
		for (byte[] b : table) {
			stack.add(read(b));
		}
		return new MergedReftable(stack);
	}

	private static ReftableReader read(byte[] table) {
		return new ReftableReader(BlockSource.from(table));
	}

	private static Ref ref(String name
		return new ObjectIdRef.PeeledNonTag(PACKED
	}

	private static Ref sym(String name
		return new SymbolicRef(name
	}

	private static Ref newRef(String name) {
		return new ObjectIdRef.Unpeeled(NEW
	}

	private static Ref delete(String name) {
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

	private byte[] write(Ref... refs) throws IOException {
		return write(Arrays.asList(refs));
	}

	private byte[] write(Collection<Ref> refs) throws IOException {
		return write(refs
	}

	private byte[] write(Collection<Ref> refs
			throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		new ReftableWriter()
				.setMinUpdateIndex(updateIndex)
				.setMaxUpdateIndex(updateIndex)
				.begin(buffer)
				.sortAndWriteRefs(refs)
				.finish();
		return buffer.toByteArray();
	}

	@SafeVarargs
	private static List<Ref> merge(List<Ref>... tables) {
		Map<String
		for (List<Ref> t : tables) {
			for (Ref r : t) {
				if (r.getStorage() == NEW && r.getObjectId() == null) {
					expect.remove(r.getName());
				} else {
					expect.put(r.getName()
				}
			}
		}

		List<Ref> expected = new ArrayList<>(expect.values());
		Collections.sort(expected
		return expected;
	}
}
