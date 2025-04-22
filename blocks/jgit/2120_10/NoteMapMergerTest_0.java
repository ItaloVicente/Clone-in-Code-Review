
package org.eclipse.jgit.notes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevBlob;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DefaultNoteMergerTest extends RepositoryTestCase {

	private TestRepository<Repository> tr;

	private ObjectReader reader;

	private ObjectInserter inserter;

	private DefaultNoteMerger merger;

	private Note baseNote;

	private RevBlob noteOn;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		tr = new TestRepository<Repository>(db);
		reader = db.newObjectReader();
		inserter = db.newObjectInserter();
		merger = new DefaultNoteMerger();
		noteOn = tr.blob("a");
		baseNote = newNote("data");
	}

	@Override
	@After
	public void tearDown() throws Exception {
		reader.release();
		inserter.release();
		super.tearDown();
	}

	@Test
	public void testDeleteDelete() throws Exception {
		assertNull(merger.merge(baseNote
	}

	@Test
	public void testEditDelete() throws Exception {
		Note edit = newNote("edit");
		assertSame(merger.merge(baseNote
		assertSame(merger.merge(baseNote
	}

	@Test
	public void testIdenticalEdit() throws Exception {
		Note edit = newNote("edit");
		assertSame(merger.merge(baseNote
	}

	@Test
	public void testEditEdit() throws Exception {
		Note edit1 = newNote("edit1");
		Note edit2 = newNote("edit2");

		Note result = merger.merge(baseNote
		assertEquals(result
		assertEquals(result.getData()

		result = merger.merge(baseNote
		assertEquals(result
		assertEquals(result.getData()
	}

	@Test
	public void testIdenticalAdd() throws Exception {
		Note add = newNote("add");
		assertSame(merger.merge(null
	}

	@Test
	public void testAddAdd() throws Exception {
		Note add1 = newNote("add1");
		Note add2 = newNote("add2");

		Note result = merger.merge(null
		assertEquals(result
		assertEquals(result.getData()

		result = merger.merge(null
		assertEquals(result
		assertEquals(result.getData()
	}

	private Note newNote(String data) throws Exception {
		return new Note(noteOn
	}
}
