
package org.eclipse.jgit.notes;

import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.RawParseUtils;

public class NoteMapReadTest extends RepositoryTestCase {
	private TestRepository<Repository> tr;

	private ObjectReader reader;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		tr = new TestRepository<Repository>(db);
		reader = db.newObjectReader();
	}

	@Override
	protected void tearDown() throws Exception {
		reader.release();
		super.tearDown();
	}

	public void testReadFlatTwoNotes() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

				.add(a.name()
				.add(b.name()
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.load(reader
		assertNotNull("have map"
		assertEquals("knows commit"
		assertNotSame("but no RevCommit"

		assertTrue("has note for a"
		assertTrue("has note for b"
		assertEquals(data1
		assertEquals(data2

		assertFalse("no note for data1"
		assertNull("no note for data1"
	}

	public void testReadDeeplyNestedNotes() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob b = tr.blob("b");
		RevBlob data1 = tr.blob("data1");
		RevBlob data2 = tr.blob("data2");

				.add(splitPath(a.name())
				.add(splitPath(b.name())
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.load(reader
		assertNotNull("have map"
		assertEquals("knows commit"
		assertNotSame("but no RevCommit"

		assertTrue("has note for a"
		assertTrue("has note for b"
		assertEquals(data1
		assertEquals(data2

		assertFalse("no note for data1"
		assertNull("no note for data1"
	}

	public void testGetCachedBytes() throws Exception {
		final String exp = "this is test data";
		RevBlob a = tr.blob("a");
		RevBlob data = tr.blob(exp);

				.add(splitPath(a.name())
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.load(reader
		byte[] act = map.getCachedBytes(a
		assertNotNull("has data for a"
		assertEquals(exp
	}

	private static String splitPath(String name) {
		StringBuilder r = new StringBuilder();
		for (int i = 0; i < name.length(); i += 2) {
			if (i != 0)
				r.append('/');
			r.append(name.charAt(i + 0));
			r.append(name.charAt(i + 1));
		}
		return r.toString();
	}
}
