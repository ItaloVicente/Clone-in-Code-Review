
package org.eclipse.jgit.util.fs;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;

public class FileAccessJavaTest extends LocalDiskRepositoryTestCase {
	private Repository db;

	private File root;

	private FileAccessJava access;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		db = createWorkRepository();
		root = db.getWorkTree();
		access = new FileAccessJava(db.getFS());
	}

	public void testStatRegularFile() throws IOException {
		File path = write("Hello Java");
		FileInfo stat = access.lstat(path);

		assertEquals(10
		assertEquals(path.lastModified() / 1000
		assertEquals(FileMode.REGULAR_FILE.getBits()

		write(path
		path.setLastModified(System.currentTimeMillis() - 24 * 3600 * 1000L);
		stat = access.lstat(path);

		assertEquals(42
		assertEquals(path.lastModified() / 1000
		assertEquals(FileMode.REGULAR_FILE.getBits()
	}

	public void testStatDirectory() throws IOException {
		File path = new File(root
		assertTrue("created " + path

		FileInfo stat = access.lstat(path);
		assertEquals(path.lastModified() / 1000
		assertEquals(FileMode.TREE.getBits()
	}

	public void testMissingFile() throws IOException {
		final File file = new File("this.not.here");
		try {
			access.lstat(file);
			fail("expected exception");
		} catch (NoSuchFileException noFile) {
			assertEquals(file.getPath()
		}
	}

	public void testNotDirectory() throws IOException {
		final File dir = write("a file");
		final File file = new File(dir
		try {
			access.lstat(file);
			fail("expected exception");
		} catch (NoSuchFileException notDir) {
			assertEquals(file.getPath()
		}
	}
}
