
package org.eclipse.jgit.notes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.RawParseUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NoteMapTest extends RepositoryTestCase {
	private TestRepository<Repository> tr;

	private ObjectReader reader;

	private ObjectInserter inserter;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		tr = new TestRepository<>(db);
		reader = db.newObjectReader();
		inserter = db.newObjectInserter();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		reader.close();
		inserter.close();
		super.tearDown();
	}

	@Test
	public void testReadFlatTwoNotes() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

				.add(a.name()
				.add(b.name()
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		assertNotNull("have map"

		assertTrue("has note for a"
		assertTrue("has note for b"
		assertEquals(data1
		assertEquals(data2

		assertFalse("no note for data1"
		assertNull("no note for data1"
	}

	@Test
	public void testReadFanout2_38() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

				.add(fanout(2
				.add(fanout(2
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		assertNotNull("have map"

		assertTrue("has note for a"
		assertTrue("has note for b"
		assertEquals(data1
		assertEquals(data2

		assertFalse("no note for data1"
		assertNull("no note for data1"
	}

	@Test
	public void testReadFanout2_2_36() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

				.add(fanout(4
				.add(fanout(4
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		assertNotNull("have map"

		assertTrue("has note for a"
		assertTrue("has note for b"
		assertEquals(data1
		assertEquals(data2

		assertFalse("no note for data1"
		assertNull("no note for data1"
	}

	@Test
	public void testReadFullyFannedOut() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

				.add(fanout(38
				.add(fanout(38
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		assertNotNull("have map"

		assertTrue("has note for a"
		assertTrue("has note for b"
		assertEquals(data1
		assertEquals(data2

		assertFalse("no note for data1"
		assertNull("no note for data1"
	}

	@Test
	public void testGetCachedBytes() throws Exception {
		final String exp = "this is test data";
		RevBlob a = tr.blob("a");
		RevBlob data = tr.blob(exp);

				.add(a.name()
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		byte[] act = map.getCachedBytes(a
		assertNotNull("has data for a"
		assertEquals(exp
	}

	@Test
	public void testWriteUnchangedFlat() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

				.add(a.name()
				.add(b.name()
				.add(".gitignore"
				.add("zoo-animals.txt"
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		assertTrue("has note for a"
		assertTrue("has note for b"

		RevCommit n = commitNoteMap(map);
		assertNotSame("is new commit"
		assertSame("same tree"
	}

	@Test
	public void testWriteUnchangedFanout2_38() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

				.add(fanout(2
				.add(fanout(2
				.add(".gitignore"
				.add("zoo-animals.txt"
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		assertTrue("has note for a"
		assertTrue("has note for b"

		RevCommit n = commitNoteMap(map);
		assertNotSame("is new commit"
		assertSame("same tree"

		map = NoteMap.read(reader
		n = commitNoteMap(map);
		assertNotSame("is new commit"
		assertSame("same tree"
	}

	@Test
	public void testCreateFromEmpty() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

		NoteMap map = NoteMap.newEmptyMap();
		assertFalse("no a"
		assertFalse("no b"

		map.set(a
		map.set(b

		assertEquals(data1
		assertEquals(data2

		map.remove(a);
		map.remove(b);

		assertFalse("no a"
		assertFalse("no b"

		map.set(a
		assertEquals(data1

		map.set(a
		assertFalse("no a"
	}

	@Test
	public void testEditFlat() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

				.add(a.name()
				.add(b.name()
				.add(".gitignore"
				.add("zoo-animals.txt"
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		map.set(a
		map.set(b
		map.set(data1
		map.set(data2

		assertEquals(data2
		assertEquals(b
		assertFalse("no b"
		assertFalse("no data2"

		MutableObjectId id = new MutableObjectId();
		for (int p = 42; p > 0; p--) {
			id.setByte(1
			map.set(id
		}

		for (int p = 42; p > 0; p--) {
			id.setByte(1
			assertTrue("contains " + id
		}

		RevCommit n = commitNoteMap(map);
		map = NoteMap.read(reader
		assertEquals(data2
		assertEquals(b
		assertFalse("no b"
		assertFalse("no data2"
		assertEquals(b
				.forPath(reader
	}

	@Test
	public void testEditFanout2_38() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

				.add(fanout(2
				.add(fanout(2
				.add(".gitignore"
				.add("zoo-animals.txt"
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		map.set(a
		map.set(b
		map.set(data1
		map.set(data2

		assertEquals(data2
		assertEquals(b
		assertFalse("no b"
		assertFalse("no data2"
		RevCommit n = commitNoteMap(map);

		map.set(a
		map.set(data1
		assertFalse("no a"
		assertFalse("no data1"

		map = NoteMap.read(reader
		assertEquals(data2
		assertEquals(b
		assertFalse("no b"
		assertFalse("no data2"
		assertEquals(b
				.forPath(reader
	}

	@Test
	public void testLeafSplitsWhenFull() throws Exception {
		RevBlob data1 = tr.blob("data1");
		MutableObjectId idBuf = new MutableObjectId();

				.add(data1.name()
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		for (int i = 0; i < 254; i++) {
			idBuf.setByte(Constants.OBJECT_ID_LENGTH - 1
			map.set(idBuf
		}

		RevCommit n = commitNoteMap(map);
		try (TreeWalk tw = new TreeWalk(reader)) {
			tw.reset(n.getTree());
			while (tw.next()) {
				assertFalse("no fan-out subtree"
			}
		}

		for (int i = 254; i < 256; i++) {
			idBuf.setByte(Constants.OBJECT_ID_LENGTH - 1
			map.set(idBuf
		}
		idBuf.setByte(Constants.OBJECT_ID_LENGTH - 2
		map.set(idBuf
		n = commitNoteMap(map);

		String path = fanout(38
		try (TreeWalk tw = TreeWalk.forPath(reader
			assertNotNull("has " + path
		}

		path = fanout(2
		try (TreeWalk tw = TreeWalk.forPath(reader
			assertNotNull("has " + path
		}
	}

	@Test
	public void testRemoveDeletesTreeFanout2_38() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob data1 = tr.blob("data1");
		RevTree empty = tr.tree();

				.add(fanout(2
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		map.set(a

		RevCommit n = commitNoteMap(map);
		assertEquals("empty tree"
	}

	@Test
	public void testIteratorEmptyMap() {
		Iterator<Note> it = NoteMap.newEmptyMap().iterator();
		assertFalse(it.hasNext());
	}

	@Test
	public void testIteratorFlatTree() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");
		RevBlob nonNote = tr.blob("non note");

				.add(a.name()
				.add(b.name()
				.add("nonNote"
				.create();
		tr.parseBody(r);

		Iterator it = NoteMap.read(reader
		assertEquals(2
	}

	@Test
	public void testIteratorFanoutTree2_38() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");
		RevBlob nonNote = tr.blob("non note");

				.add(fanout(2
				.add(fanout(2
				.add("nonNote"
				.create();
		tr.parseBody(r);

		Iterator it = NoteMap.read(reader
		assertEquals(2
	}

	@Test
	public void testIteratorFanoutTree2_2_36() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");
		RevBlob nonNote = tr.blob("non note");

				.add(fanout(4
				.add(fanout(4
				.add("nonNote"
				.create();
		tr.parseBody(r);

		Iterator it = NoteMap.read(reader
		assertEquals(2
	}

	@Test
	public void testIteratorFullyFannedOut() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");
		RevBlob nonNote = tr.blob("non note");

				.add(fanout(38
				.add(fanout(38
				.add("nonNote"
				.create();
		tr.parseBody(r);

		Iterator it = NoteMap.read(reader
		assertEquals(2
	}

	@Test
	public void testShorteningNoteRefName() throws Exception {
		String expectedShortName = "review";
		String noteRefName = Constants.R_NOTES + expectedShortName;
		assertEquals(expectedShortName
		String nonNoteRefName = Constants.R_HEADS + expectedShortName;
		assertEquals(nonNoteRefName
	}

	private RevCommit commitNoteMap(NoteMap map) throws IOException {
		tr.tick(600);

		CommitBuilder builder = new CommitBuilder();
		builder.setTreeId(map.writeTree(inserter));
		tr.setAuthorAndCommitter(builder);
		return tr.getRevWalk().parseCommit(inserter.insert(builder));
	}

	private static String fanout(int prefix
		StringBuilder r = new StringBuilder();
		int i = 0;
		for (; i < prefix && i < name.length(); i += 2) {
			if (i != 0)
				r.append('/');
			r.append(name.charAt(i + 0));
			r.append(name.charAt(i + 1));
		}
		if (i < name.length()) {
			if (i != 0)
				r.append('/');
			r.append(name.substring(i));
		}
		return r.toString();
	}

	private static int count(Iterator it) {
		int c = 0;
		while (it.hasNext()) {
			c++;
			it.next();
		}
		return c;
	}
}
