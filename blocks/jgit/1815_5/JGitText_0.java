package org.eclipse.jgit.util.fs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;

public class FSAccessTest extends LocalDiskRepositoryTestCase {
	private Repository db;

	private File trash;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		db = createWorkRepository();
		trash = db.getWorkTree();
	}

	@SuppressWarnings("boxing")
	public void test_lstat_native() throws IOException {
		if (!Boolean.valueOf(System.getProperty("org.eclipse.jgit.fs.native")))
			return;
		FSAccess fsa = new FSAccessNative();
		do_test_lstat(fsa
	}

	public void test_lstat_java() throws IOException {
		FSAccess fsa = new FSAccessJava();
		do_test_lstat(fsa
	}

	private boolean isPosix() {
		return System.getProperty("os.name").toLowerCase().indexOf("windows") == -1;
	}

	public void do_test_lstat(FSAccess fsa
			throws IOException {
		File test = writeTrashFile("test/test.txt"

		LStat stat = fsa.lstat(db.getFS()

		assertTrue(stat.getMTimeSec() > 0);
		assertTrue(stat.getSize() == 10);
		assertTrue(stat.getMode() == FileMode.REGULAR_FILE);
		if (isNative) {
			assertTrue(stat.getMTimeSec() >= stat.getCTimeSec());
			if (isPosix()) {
				assertTrue(stat.getDevice() > 0);
				assertTrue(stat.getGroupId() > 0);
				assertTrue(stat.getUserId() > 0);
				assertTrue(stat.getInode() > 0);
			}
		}

		BufferedWriter out = new BufferedWriter(new FileWriter(test));
		out.write("Changed the text to see if lstat sees that");
		out.close();
		stat = fsa.lstat(db.getFS()
		assertTrue(stat.getSize() == 42);

		File dir = test.getParentFile();
		stat = fsa.lstat(db.getFS()
		assertTrue(stat.getMode() == FileMode.TREE);

		if (db.getFS().supportsExecute()) {
			db.getFS().setExecute(test
			stat = fsa.lstat(db.getFS()
			assertTrue(stat.getMode() == FileMode.EXECUTABLE_FILE);
		}



		test.delete();
	}

	private File writeTrashFile(String name
		final File path = new File(trash
		write(path
		return path;
	}
}
