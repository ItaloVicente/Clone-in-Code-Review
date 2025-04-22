package org.eclipse.jgit.notes;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;

public class NoteMapMergerTest extends RepositoryTestCase {
	private TestRepository<Repository> tr;

	private ObjectReader reader;

	private ObjectInserter inserter;

	private NoteMap noRoot;

	private NoteMap empty;

	private NoteMap map1;

	private NoteMap map2;

	private RevBlob noteAId;

	private RevBlob noteAContent;

	private RevBlob noteBId;

	private RevBlob noteBContent;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tr = new TestRepository<Repository>(db);
		reader = db.newObjectReader();
		inserter = db.newObjectInserter();

		noRoot = NoteMap.newMap(null
		empty = NoteMap.newEmptyMap();

		noteAId = tr.blob("a");
		noteAContent = tr.blob("data01");

		RevCommit sampleTree = tr.commit()
			.add(noteAId.name()
			.create();
		tr.parseBody(sampleTree);

		map1 = NoteMap.read(reader

		map2 = NoteMap.read(reader
		noteBId = tr.blob("b");
		noteBContent = tr.blob("data02");
		map2.set(noteBId
	}

	@Override
	protected void tearDown() throws Exception {
		reader.release();
		inserter.release();
		super.tearDown();
	}

	public void testNoChange() throws IOException {
		NoteMapMerger merger = new NoteMapMerger(db
		NoteMap result;

		assertEquals(0
		assertEquals(0

		result = merger.merge(map1
		assertEquals(1
		assertEquals(result.get(noteAId)
	}

	public void testOursEqualsTheirs() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
		NoteMap result;

		assertEquals(0
		assertEquals(0

		assertEquals(0
		assertEquals(0

		result = merger.merge(noRoot
		assertEquals(1
		assertEquals(result.get(noteAId)

		result = merger.merge(empty
		assertEquals(1
		assertEquals(result.get(noteAId)

		result = merger.merge(map2
		assertEquals(1
		assertEquals(result.get(noteAId)
	}

	public void testBaseEqualsOurs() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
		NoteMap result;

		assertEquals(0
		result = merger.merge(noRoot
		assertEquals(1
		assertEquals(result.get(noteAId)

		assertEquals(0
		result = merger.merge(empty
		assertEquals(1
		assertEquals(result.get(noteAId)

		assertEquals(0
		assertEquals(0
		result = merger.merge(map1
		assertEquals(2
		assertEquals(result.get(noteAId)
		assertEquals(result.get(noteBId)
	}

	public void testBaseEqualsTheirs() throws Exception {
		NoteMapMerger merger = new NoteMapMerger(db
		NoteMap result;

		assertEquals(0
		result = merger.merge(noRoot
		assertEquals(1
		assertEquals(result.get(noteAId)

		assertEquals(0
		result = merger.merge(empty
		assertEquals(1
		assertEquals(result.get(noteAId)

		assertEquals(0
		assertEquals(0
		result = merger.merge(map1
		assertEquals(2
		assertEquals(result.get(noteAId)
		assertEquals(result.get(noteBId)
	}

	private static int countNotes(NoteMap result) {
		int c = 0;
		Iterator<Note> it = result.iterator();
		while (it.hasNext()) {
			it.next();
			c++;
		}
		return c;
	}

}
