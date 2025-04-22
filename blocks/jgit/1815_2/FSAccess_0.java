package org.eclipse.jgit.util.fs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
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

	public void test_lstat() throws IOException {
		File test = writeTrashFile("test.txt"

		LStat stat = FSAccess.INSTANCE.lstat(test);
		System.out.println(stat.toString() + '\n');
		assertTrue(stat.getMTimeSec() > 0);
		assertTrue(stat.getSize() == 10);
		if (FSAccess.isNativeImplementation()) {
			assertTrue(stat.getCTimeSec() == stat.getMTimeSec());
			assertTrue(stat.getDevice() > 0);
			assertTrue(stat.getGroupId() > 0);
			assertTrue(stat.getUserId() > 0);
			assertTrue(stat.getInode() > 0);
			assertTrue(stat.getMode() > 0);
		}

		BufferedWriter out = new BufferedWriter(new FileWriter(test));
		out.write("Changed the text to see if lstat sees that");
		out.close();
		stat = FSAccess.INSTANCE.lstat(test);
		System.out.println(stat.toString() + '\n');
		assertTrue(stat.getSize() == 42);

	}

	private File writeTrashFile(String name
		final File path = new File(trash
		write(path
		return path;
	}
}
