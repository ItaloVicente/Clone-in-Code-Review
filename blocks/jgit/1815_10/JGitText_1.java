
package org.eclipse.jgit.util.fs;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.NativeLibrary;

public class FileAccessNativeTest extends LocalDiskRepositoryTestCase {
	private static boolean skipTest() {
		return NativeLibrary.isDisabled();
	}

	private Repository db;

	private File root;

	private FileAccessNative access;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		if (!skipTest()) {
			db = createWorkRepository();
			root = db.getWorkTree();
			access = new FileAccessNative();
		}
	}

	public void testStatRegularFile() throws IOException {
		if (skipTest())
			return;

		File path = write("Hello Java");
		FileInfo stat = access.lstat(path);

		assertEquals(10
		assertEquals(path.lastModified() / 1000
		assertTrue(stat.created() >= stat.lastModified());
		assertTrue(FileMode.REGULAR_FILE.equals(stat.mode()));
		final long created = stat.created();

		write(path
		path.setLastModified(System.currentTimeMillis() - 24 * 3600 * 1000L);
		stat = access.lstat(path);

		assertEquals(42
		assertEquals(path.lastModified() / 1000
		assertEquals(created
		assertTrue(FileMode.REGULAR_FILE.equals(stat.mode()));

		if (isPosix()) {
			assertTrue(stat.device() > 0);
			assertTrue(stat.inode() > 0);
			assertTrue(stat.uid() > 0);
			assertTrue(stat.gid() > 0);
		}
	}

	public void testStatDirectory() throws IOException {
		if (skipTest())
			return;

		File path = new File(root
		assertTrue("created " + path

		FileInfo stat = access.lstat(path);
		assertEquals(path.lastModified() / 1000
		assertTrue(FileMode.TREE.equals(stat.mode()));

		if (isPosix()) {
			assertTrue(stat.device() > 0);
			assertTrue(stat.inode() > 0);
			assertTrue(stat.uid() > 0);
			assertTrue(stat.gid() > 0);
		}
	}

	public void testMissingFile() throws IOException {
		if (skipTest())
			return;

		final File file = new File("this.not.here");
		try {
			access.lstat(file);
			fail("expected exception");
		} catch (NoSuchFileException noFile) {
			assertEquals(file.getPath()
		}
	}

	public void testNotDirectory() throws IOException {
		if (skipTest())
			return;

		final File dir = write("a file");
		final File file = new File(dir
		try {
			access.lstat(file);
			fail("expected exception");
		} catch (NotDirectoryException notDir) {
			assertEquals(file.getPath()
		}
	}

	private static boolean isPosix() {
		return System.getProperty("os.name").toLowerCase().indexOf("windows") == -1;
	}
}
