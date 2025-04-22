
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.WindowCacheConfig;
import org.eclipse.jgit.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConcurrentRepackTest extends RepositoryTestCase {
	@Override
	@Before
	public void setUp() throws Exception {
		WindowCacheConfig windowCacheConfig = new WindowCacheConfig();
		windowCacheConfig.setPackedGitOpenFiles(1);
		windowCacheConfig.install();
		super.setUp();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		new WindowCacheConfig().install();
	}

	@Test
	public void testObjectInNewPack() throws IncorrectObjectTypeException
			IOException {
		final Repository eden = createBareRepository();
		final RevObject o1 = writeBlob(eden
		pack(eden
		assertEquals(o1.name()
	}

	@Test
	public void testObjectMovedToNewPack1()
			throws IncorrectObjectTypeException
		final Repository eden = createBareRepository();
		final RevObject o1 = writeBlob(eden
		final File[] out1 = pack(eden
		assertEquals(o1.name()

		final RevObject o2 = writeBlob(eden
		pack(eden

		whackCache();
		delete(out1);

		assertEquals(o2.name()
		assertEquals(o1.name()
	}

	@Test
	public void testObjectMovedWithinPack()
			throws IncorrectObjectTypeException
		final Repository eden = createBareRepository();
		final RevObject o1 = writeBlob(eden
		final File[] out1 = pack(eden
		assertEquals(o1.name()

		whackCache();

		final RevObject o2 = writeBlob(eden
		try (PackWriter pw = new PackWriter(eden)) {
			pw.addObject(o2);
			pw.addObject(o1);
			write(out1
		}

		assertEquals(o1.name()
		assertEquals(o2.name()
	}

	@Test
	public void testObjectMovedToNewPack2()
			throws IncorrectObjectTypeException
		final Repository eden = createBareRepository();
		final RevObject o1 = writeBlob(eden
		final File[] out1 = pack(eden
		assertEquals(o1.name()

		final ObjectLoader load1 = db.open(o1
		assertNotNull(load1);

		final RevObject o2 = writeBlob(eden
		pack(eden

		whackCache();
		delete(out1);

		final ObjectLoader load2 = db.open(o1
		assertNotNull(load2);
		assertNotSame(load1

		final byte[] data2 = load2.getCachedBytes();
		final byte[] data1 = load1.getCachedBytes();
		assertNotNull(data2);
		assertNotNull(data1);
		assertNotSame(data1
		assertArrayEquals(data1
		assertEquals(load2.getType()
	}

	private static void whackCache() {
		final WindowCacheConfig config = new WindowCacheConfig();
		config.setPackedGitOpenFiles(1);
		config.install();
	}

	private RevObject parse(AnyObjectId id)
			throws MissingObjectException
		try (RevWalk rw = new RevWalk(db)) {
			return rw.parseAny(id);
		}
	}

	private File[] pack(Repository src
			throws IOException {
		try (PackWriter pw = new PackWriter(src)) {
			for (RevObject o : list) {
				pw.addObject(o);
			}

			final ObjectId name = pw.computeName();
			final File packFile = fullPackFileName(name
			final File idxFile = fullPackFileName(name
			final File[] files = new File[] { packFile
			write(files
			return files;
		}
	}

	private static void write(File[] files
			throws IOException {
		final long begin = files[0].getParentFile().lastModified();
		NullProgressMonitor m = NullProgressMonitor.INSTANCE;

		try (OutputStream out = new BufferedOutputStream(
				new FileOutputStream(files[0]))) {
			pw.writePack(m
		}

		try (OutputStream out = new BufferedOutputStream(
				new FileOutputStream(files[1]))) {
			pw.writeIndex(out);
		}

		touch(begin
	}

	private static void delete(File[] list) throws IOException {
		final long begin = list[0].getParentFile().lastModified();
		for (File f : list) {
			FileUtils.delete(f);
			assertFalse(f + " was removed"
		}
		touch(begin
	}

	private static void touch(long begin
		while (begin >= dir.lastModified()) {
			try {
				Thread.sleep(25);
			} catch (InterruptedException ie) {
			}
			dir.setLastModified(System.currentTimeMillis());
		}
	}

	private File fullPackFileName(ObjectId name
		final File packdir = db.getObjectDatabase().getPackDirectory();
		return new File(packdir
	}

	private RevObject writeBlob(Repository repo
			throws IOException {
		final byte[] bytes = Constants.encode(data);
		final ObjectId id;
		try (ObjectInserter inserter = repo.newObjectInserter()) {
			id = inserter.insert(Constants.OBJ_BLOB
			inserter.flush();
		}
		try {
			parse(id);
			fail("Object " + id.name() + " should not exist in test repository");
		} catch (MissingObjectException e) {
		}
		try (RevWalk revWalk = new RevWalk(repo)) {
			return revWalk.lookupBlob(id);
		}
	}
}
