
package org.eclipse.jgit.notes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NoteMapMergerTest extends RepositoryTestCase {
	private TestRepository<Repository> tr;

	private ObjectReader reader;

	private ObjectInserter inserter;

	private NoteMap noRoot;

	private NoteMap empty;

	private NoteMap map_a;

	private NoteMap map_a_b;

	private RevBlob noteAId;

	private String noteAContent;

	private RevBlob noteABlob;

	private RevBlob noteBId;

	private String noteBContent;

	private RevBlob noteBBlob;

	private RevCommit sampleTree_a;

	private RevCommit sampleTree_a_b;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		tr = new TestRepository<Repository>(db);
		reader = db.newObjectReader();
		inserter = db.newObjectInserter();

		noRoot = NoteMap.newMap(null
		empty = NoteMap.newEmptyMap();

		noteAId = tr.blob("a");
		noteAContent = "noteAContent";
		noteABlob = tr.blob(noteAContent);
		sampleTree_a = tr.commit()
				.add(noteAId.name()
				.create();
		tr.parseBody(sampleTree_a);
		map_a = NoteMap.read(reader

		noteBId = tr.blob("b");
		noteBContent = "noteBContent";
		noteBBlob = tr.blob(noteBContent);
		sampleTree_a_b = tr.commit()
				.add(noteAId.name()
				.add(noteBId.name()
				.create();
		tr.parseBody(sampleTree_a_b);
		map_a_b = NoteMap.read(reader
	}

	@Override
	@After
	public void tearDown() throws Exception {
		reader.release();
		inserter.release();
		super.tearDown();
	}

	@Test
	public void testNoChange() throws IOException {
		NoteMapMerger merger = new NoteMapMerger(db
		NoteMap result;

		assertEquals(0
		assertEquals(0

		result = merger.merge(map_a
		assertEquals(1
		assertEquals(noteABlob
	}

	@Test
	public void testOursEqualsTheirs() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
		NoteMap result;

		assertEquals(0
		assertEquals(0

		assertEquals(0
		assertEquals(0

		result = merger.merge(noRoot
		assertEquals(1
		assertEquals(noteABlob

		result = merger.merge(empty
		assertEquals(1
		assertEquals(noteABlob

		result = merger.merge(map_a_b
		assertEquals(1
		assertEquals(noteABlob

		result = merger.merge(map_a
		assertEquals(2
		assertEquals(noteABlob
		assertEquals(noteBBlob
	}

	@Test
	public void testBaseEqualsOurs() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
		NoteMap result;

		assertEquals(0
		result = merger.merge(noRoot
		assertEquals(1
		assertEquals(noteABlob

		assertEquals(0
		result = merger.merge(empty
		assertEquals(1
		assertEquals(noteABlob

		assertEquals(0
		assertEquals(0
		result = merger.merge(map_a
		assertEquals(2
		assertEquals(noteABlob
		assertEquals(noteBBlob
	}

	@Test
	public void testBaseEqualsTheirs() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
		NoteMap result;

		assertEquals(0
		result = merger.merge(noRoot
		assertEquals(1
		assertEquals(noteABlob

		assertEquals(0
		result = merger.merge(empty
		assertEquals(1
		assertEquals(noteABlob

		assertEquals(0
		assertEquals(0
		result = merger.merge(map_a
		assertEquals(2
		assertEquals(noteABlob
		assertEquals(noteBBlob
	}

	@Test
	public void testAddDifferentNotes() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
		NoteMap result;

		NoteMap map_a_c = NoteMap.read(reader
		RevBlob noteCId = tr.blob("c");
		RevBlob noteCBlob = tr.blob("noteCContent");
		map_a_c.set(noteCId
		map_a_c.writeTree(inserter);

		result = merger.merge(map_a
		assertEquals(3
		assertEquals(noteABlob
		assertEquals(noteBBlob
		assertEquals(noteCBlob
	}

	@Test
	public void testAddSameNoteDifferentContent() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
				null);
		NoteMap result;

		NoteMap map_a_b1 = NoteMap.read(reader
		String noteBContent1 = noteBContent + "change";
		RevBlob noteBBlob1 = tr.blob(noteBContent1);
		map_a_b1.set(noteBId
		map_a_b1.writeTree(inserter);

		result = merger.merge(map_a
		assertEquals(2
		assertEquals(noteABlob
		assertEquals(tr.blob(noteBContent + noteBContent1)
	}

	@Test
	public void testEditSameNoteDifferentContent() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
				null);
		NoteMap result;

		NoteMap map_a1 = NoteMap.read(reader
		String noteAContent1 = noteAContent + "change1";
		RevBlob noteABlob1 = tr.blob(noteAContent1);
		map_a1.set(noteAId
		map_a1.writeTree(inserter);

		NoteMap map_a2 = NoteMap.read(reader
		String noteAContent2 = noteAContent + "change2";
		RevBlob noteABlob2 = tr.blob(noteAContent2);
		map_a2.set(noteAId
		map_a2.writeTree(inserter);

		result = merger.merge(map_a
		assertEquals(1
		assertEquals(tr.blob(noteAContent1 + noteAContent2)
				result.get(noteAId));
	}

	@Test
	public void testEditDifferentNotes() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
		NoteMap result;

		NoteMap map_a1_b = NoteMap.read(reader
		String noteAContent1 = noteAContent + "change";
		RevBlob noteABlob1 = tr.blob(noteAContent1);
		map_a1_b.set(noteAId
		map_a1_b.writeTree(inserter);

		NoteMap map_a_b1 = NoteMap.read(reader
		String noteBContent1 = noteBContent + "change";
		RevBlob noteBBlob1 = tr.blob(noteBContent1);
		map_a_b1.set(noteBId
		map_a_b1.writeTree(inserter);

		result = merger.merge(map_a_b
		assertEquals(2
		assertEquals(noteABlob1
		assertEquals(noteBBlob1
	}

	@Test
	public void testDeleteDifferentNotes() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db

		NoteMap map_b = NoteMap.read(reader
		map_b.set(noteAId
		map_b.writeTree(inserter);

		assertEquals(0
	}

	@Test
	public void testEditDeleteConflict() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
				null);
		NoteMap result;

		NoteMap map_a_b1 = NoteMap.read(reader
		String noteBContent1 = noteBContent + "change";
		RevBlob noteBBlob1 = tr.blob(noteBContent1);
		map_a_b1.set(noteBId
		map_a_b1.writeTree(inserter);

		result = merger.merge(map_a_b
		assertEquals(2
		assertEquals(noteABlob
		assertEquals(noteBBlob1
	}

	@Test
	public void testLargeTreesWithoutConflict() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
		NoteMap map1 = createLargeNoteMap("note_1_"
		NoteMap map2 = createLargeNoteMap("note_2_"

		NoteMap result = merger.merge(empty
		assertEquals(600
		assertEquals(tr.blob("content_1_59")
		assertEquals(tr.blob("content_2_10")
		assertEquals(tr.blob("content_2_99")
	}

	@Test
	public void testLargeTreesWithConflict() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
				null);
		NoteMap largeTree1 = createLargeNoteMap("note_1_"
		NoteMap largeTree2 = createLargeNoteMap("note_1_"

		NoteMap result = merger.merge(empty
		assertEquals(300
		assertEquals(tr.blob("content_1_59content_2_59")
				result.get(tr.blob("note_1_59")));
		assertEquals(tr.blob("content_1_10content_2_10")
				result.get(tr.blob("note_1_10")));
		assertEquals(tr.blob("content_1_99content_2_99")
				result.get(tr.blob("note_1_99")));
	}

	private NoteMap createLargeNoteMap(String noteNamePrefix
			String noteContentPrefix
			throws Exception {
		NoteMap result = NoteMap.newEmptyMap();
		for (int i = 0; i < notesCount; i++) {
			result.set(tr.blob(noteNamePrefix + (firstIndex + i))
					tr.blob(noteContentPrefix + (firstIndex + i)));
		}
		result.writeTree(inserter);
		return result;
	}

	@Test
	public void testFanoutAndLeafWithoutConflict() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db

		NoteMap largeTree = createLargeNoteMap("note_1_"
		NoteMap result = merger.merge(map_a
		assertEquals(301
	}

	@Test
	public void testFanoutAndLeafWitConflict() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
				null);

		NoteMap largeTree_b1 = createLargeNoteMap("note_1_"
				0);
		String noteBContent1 = noteBContent + "change";
		largeTree_b1.set(noteBId
		largeTree_b1.writeTree(inserter);

		NoteMap result = merger.merge(map_a
		assertEquals(301
		assertEquals(tr.blob(noteBContent + noteBContent1)
	}

	@Test
	public void testCollapseFanoutAfterMerge() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db

		NoteMap largeTree = createLargeNoteMap("note_"
		assertTrue(largeTree.getRoot() instanceof FanoutBucket);
		NoteMap deleteFirstHundredNotes = createLargeNoteMap("note_"
				100);
		NoteMap deleteLastHundredNotes = createLargeNoteMap("note_"
				"content_"
		NoteMap result = merger.merge(largeTree
				deleteLastHundredNotes);
		assertEquals(57
		assertTrue(result.getRoot() instanceof LeafBucket);
	}

	@Test
	public void testNonNotesWithoutNonNoteConflict() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
				MergeStrategy.RESOLVE);
		RevCommit treeWithNonNotes =
			tr.commit()
				.add(noteAId.name()
				.add("a.txt"
				.create();
		tr.parseBody(treeWithNonNotes);
		NoteMap base = NoteMap.read(reader

		treeWithNonNotes =
			tr.commit()
				.add(noteAId.name()
				.add("a.txt"
				.add("b.txt"
				.create();
		tr.parseBody(treeWithNonNotes);
		NoteMap ours = NoteMap.read(reader

		treeWithNonNotes =
			tr.commit()
				.add(noteAId.name()
				.add("a.txt"
				.add("c.txt"
				.create();
		tr.parseBody(treeWithNonNotes);
		NoteMap theirs = NoteMap.read(reader

		NoteMap result = merger.merge(base
		assertEquals(3
	}

	@Test
	public void testNonNotesWithNonNoteConflict() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
				MergeStrategy.RESOLVE);
		RevCommit treeWithNonNotes =
			tr.commit()
				.add(noteAId.name()
				.add("a.txt"
				.create();
		tr.parseBody(treeWithNonNotes);
		NoteMap base = NoteMap.read(reader

		treeWithNonNotes =
			tr.commit()
				.add(noteAId.name()
				.add("a.txt"
				.create();
		tr.parseBody(treeWithNonNotes);
		NoteMap ours = NoteMap.read(reader

		treeWithNonNotes =
			tr.commit()
				.add(noteAId.name()
				.add("a.txt"
				.create();
		tr.parseBody(treeWithNonNotes);
		NoteMap theirs = NoteMap.read(reader

		try {
			merger.merge(base
			fail("NotesMergeConflictException was expected");
		} catch (NotesMergeConflictException e) {
		}
	}

	private static int countNotes(NoteMap map) {
		int c = 0;
		Iterator<Note> it = map.iterator();
		while (it.hasNext()) {
			it.next();
			c++;
		}
		return c;
	}

	private static int countNonNotes(NoteMap map) {
		int c = 0;
		NonNoteEntry nonNotes = map.getRoot().nonNotes;
		while (nonNotes != null) {
			c++;
			nonNotes = nonNotes.next;
		}
		return c;
	}
}
