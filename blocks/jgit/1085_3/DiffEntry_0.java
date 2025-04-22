
package org.eclipse.jgit.diff;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.io.DisabledOutputStream;

public class DiffFormatterTest extends RepositoryTestCase {
	private static final String DIFF = "diff --git ";

	private static final String REGULAR_FILE = "100644";

	private static final String GITLINK = "160000";

	private static final String PATH_A = "src/a";

	private static final String PATH_B = "src/b";

	private DiffFormatter df;

	private TestRepository testDb;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		testDb = new TestRepository(db);
		df = new DiffFormatter(DisabledOutputStream.INSTANCE);
		df.setRepository(db);
	}

	public void testCreateFileHeader_Modify() throws Exception {
		ObjectId adId = blob("a\nd\n");
		ObjectId abcdId = blob("a\nb\nc\nd\n");

		String diffHeader = makeDiffHeader(PATH_A

		DiffEntry ad = DiffEntry.delete(PATH_A
		DiffEntry abcd = DiffEntry.add(PATH_A

		DiffEntry mod = DiffEntry.pair(ChangeType.MODIFY

		FileHeader fh = df.createFileHeader(mod);

		assertEquals(diffHeader

		assertEquals(1

		HunkHeader hh = fh.getHunks().get(0);
		assertEquals(1

		EditList el = hh.toEditList();
		assertEquals(1

		Edit e = el.get(0);
		assertEquals(1
		assertEquals(1
		assertEquals(1
		assertEquals(3
		assertEquals(Edit.Type.INSERT
	}

	public void testCreateFileHeader_Binary() throws Exception {
		ObjectId adId = blob("a\nd\n");
		ObjectId binId = blob("a\nb\nc\n\0\0\0\0d\n");

		String diffHeader = makeDiffHeader(PATH_A
				+ "Binary files differ\n";

		DiffEntry ad = DiffEntry.delete(PATH_A
		DiffEntry abcd = DiffEntry.add(PATH_B

		DiffEntry mod = DiffEntry.pair(ChangeType.MODIFY

		FileHeader fh = df.createFileHeader(mod);

		assertEquals(diffHeader

		assertEquals(1

		HunkHeader hh = fh.getHunks().get(0);
		assertEquals(0
	}

	public void testCreateFileHeader_GitLink() throws Exception {
		ObjectId aId = blob("a\n");
		ObjectId bId = blob("b\n");

		String diffHeader = makeDiffHeaderModeChange(PATH_A
				GITLINK
				+ "-Subproject commit " + aId.name() + "\n";

		DiffEntry ad = DiffEntry.delete(PATH_A
		ad.oldMode = FileMode.GITLINK;
		DiffEntry abcd = DiffEntry.add(PATH_A

		DiffEntry mod = DiffEntry.pair(ChangeType.MODIFY

		FileHeader fh = df.createFileHeader(mod);

		assertEquals(diffHeader

		assertEquals(1

		HunkHeader hh = fh.getHunks().get(0);
		assertEquals(0
	}

	private String makeDiffHeader(String pathA
			ObjectId bId) {
		String a = aId.abbreviate(db).name();
		String b = bId.abbreviate(db).name();
				"+++ b/" + pathB + "\n";
	}

	private String makeDiffHeaderModeChange(String pathA
			ObjectId aId
		String a = aId.abbreviate(db).name();
		String b = bId.abbreviate(db).name();
				"+++ b/" + pathB + "\n";
	}

	private ObjectId blob(String content) throws Exception {
		return testDb.blob(content).copy();
	}

}
