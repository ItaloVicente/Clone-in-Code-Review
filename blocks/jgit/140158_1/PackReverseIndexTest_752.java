
package org.eclipse.jgit.internal.storage.file;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.storage.file.WindowCacheConfig;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.IO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("boxing")
public class PackInserterTest extends RepositoryTestCase {
	private WindowCacheConfig origWindowCacheConfig;

	private static final Random random = new Random(0);

	@Before
	public void setWindowCacheConfig() {
		origWindowCacheConfig = new WindowCacheConfig();
		origWindowCacheConfig.install();
	}

	@After
	public void resetWindowCacheConfig() {
		origWindowCacheConfig.install();
	}

	@Before
	public void emptyAtSetUp() throws Exception {
		assertEquals(0
		assertNoObjects();
	}

	@Test
	public void noFlush() throws Exception {
		try (PackInserter ins = newInserter()) {
			ins.insert(OBJ_BLOB
		}
		assertNoObjects();
	}

	@Test
	public void flushEmptyPack() throws Exception {
		try (PackInserter ins = newInserter()) {
			ins.flush();
		}
		assertNoObjects();
	}

	@Test
	public void singlePack() throws Exception {
		ObjectId blobId;
		byte[] blob = Constants.encode("foo contents");
		ObjectId treeId;
		ObjectId commitId;
		byte[] commit;
		try (PackInserter ins = newInserter()) {
			blobId = ins.insert(OBJ_BLOB

			DirCache dc = DirCache.newInCore();
			DirCacheBuilder b = dc.builder();
			DirCacheEntry dce = new DirCacheEntry("foo");
			dce.setFileMode(FileMode.REGULAR_FILE);
			dce.setObjectId(blobId);
			b.add(dce);
			b.finish();
			treeId = dc.writeTree(ins);

			CommitBuilder cb = new CommitBuilder();
			cb.setTreeId(treeId);
			cb.setAuthor(author);
			cb.setCommitter(committer);
			cb.setMessage("Commit message");
			commit = cb.toByteArray();
			commitId = ins.insert(cb);
			ins.flush();
		}

		assertPacksOnly();
		List<PackFile> packs = listPacks();
		assertEquals(1
		assertEquals(3

		try (ObjectReader reader = db.newObjectReader()) {
			assertBlob(reader

			CanonicalTreeParser treeParser =
					new CanonicalTreeParser(null
			assertEquals("foo"
			assertEquals(blobId

			ObjectLoader commitLoader = reader.open(commitId);
			assertEquals(OBJ_COMMIT
			assertArrayEquals(commit
		}
	}

	@Test
	public void multiplePacks() throws Exception {
		ObjectId blobId1;
		ObjectId blobId2;
		byte[] blob1 = Constants.encode("blob1");
		byte[] blob2 = Constants.encode("blob2");

		try (PackInserter ins = newInserter()) {
			blobId1 = ins.insert(OBJ_BLOB
			ins.flush();
			blobId2 = ins.insert(OBJ_BLOB
			ins.flush();
		}

		assertPacksOnly();
		List<PackFile> packs = listPacks();
		assertEquals(2
		assertEquals(1
		assertEquals(1

		try (ObjectReader reader = db.newObjectReader()) {
			assertBlob(reader
			assertBlob(reader
		}
	}

	@Test
	public void largeBlob() throws Exception {
		ObjectId blobId;
		byte[] blob = newLargeBlob();
		try (PackInserter ins = newInserter()) {
			assertThat(blob.length
			blobId =
					ins.insert(OBJ_BLOB
			ins.flush();
		}

		assertPacksOnly();
		Collection<PackFile> packs = listPacks();
		assertEquals(1
		PackFile p = packs.iterator().next();
		assertEquals(1

		try (ObjectReader reader = db.newObjectReader()) {
			assertBlob(reader
		}
	}

	@Test
	public void overwriteExistingPack() throws Exception {
		ObjectId blobId;
		byte[] blob = Constants.encode("foo contents");

		try (PackInserter ins = newInserter()) {
			blobId = ins.insert(OBJ_BLOB
			ins.flush();
		}

		assertPacksOnly();
		List<PackFile> packs = listPacks();
		assertEquals(1
		PackFile pack = packs.get(0);
		assertEquals(1

		String inode = getInode(pack.getPackFile());

		try (PackInserter ins = newInserter()) {
			ins.checkExisting(false);
			assertEquals(blobId
			ins.flush();
		}

		assertPacksOnly();
		packs = listPacks();
		assertEquals(1
		pack = packs.get(0);
		assertEquals(1

		if (inode != null) {
			assertNotEquals(inode
		}
	}

	@Test
	public void checkExisting() throws Exception {
		ObjectId blobId;
		byte[] blob = Constants.encode("foo contents");

		try (PackInserter ins = newInserter()) {
			blobId = ins.insert(OBJ_BLOB
			ins.insert(OBJ_BLOB
			ins.flush();
		}

		assertPacksOnly();
		assertEquals(1

		try (PackInserter ins = newInserter()) {
			assertEquals(blobId
			ins.flush();
		}

		assertPacksOnly();
		assertEquals(1

		try (PackInserter ins = newInserter()) {
			ins.checkExisting(false);
			assertEquals(blobId
			ins.flush();
		}

		assertPacksOnly();
		assertEquals(2

		try (ObjectReader reader = db.newObjectReader()) {
			assertBlob(reader
		}
	}

	@Test
	public void insertSmallInputStreamRespectsCheckExisting() throws Exception {
		ObjectId blobId;
		byte[] blob = Constants.encode("foo contents");
		try (PackInserter ins = newInserter()) {
			assertThat(blob.length
			blobId = ins.insert(OBJ_BLOB
			ins.insert(OBJ_BLOB
			ins.flush();
		}

		assertPacksOnly();
		assertEquals(1

		try (PackInserter ins = newInserter()) {
			assertEquals(blobId
					ins.insert(OBJ_BLOB
			ins.flush();
		}

		assertPacksOnly();
		assertEquals(1
	}

	@Test
	public void insertLargeInputStreamBypassesCheckExisting() throws Exception {
		ObjectId blobId;
		byte[] blob = newLargeBlob();

		try (PackInserter ins = newInserter()) {
			assertThat(blob.length
			blobId = ins.insert(OBJ_BLOB
			ins.insert(OBJ_BLOB
			ins.flush();
		}

		assertPacksOnly();
		assertEquals(1

		try (PackInserter ins = newInserter()) {
			assertEquals(blobId
					ins.insert(OBJ_BLOB
			ins.flush();
		}

		assertPacksOnly();
		assertEquals(2
	}

	@Test
	public void readBackSmallFiles() throws Exception {
		ObjectId blobId1;
		ObjectId blobId2;
		ObjectId blobId3;
		byte[] blob1 = Constants.encode("blob1");
		byte[] blob2 = Constants.encode("blob2");
		byte[] blob3 = Constants.encode("blob3");
		try (PackInserter ins = newInserter()) {
			assertThat(blob1.length
			blobId1 = ins.insert(OBJ_BLOB

			try (ObjectReader reader = ins.newReader()) {
				assertBlob(reader
			}

			blobId2 = ins.insert(OBJ_BLOB
			ins.flush();

			blobId3 = ins.insert(OBJ_BLOB
		}

		assertPacksOnly();
		List<PackFile> packs = listPacks();
		assertEquals(1
		assertEquals(2

		try (ObjectReader reader = db.newObjectReader()) {
			assertBlob(reader
			assertBlob(reader

			try {
				reader.open(blobId3);
				fail("Expected MissingObjectException");
			} catch (MissingObjectException expected) {
			}
		}
	}

	@Test
	public void readBackLargeFile() throws Exception {
		ObjectId blobId;
		byte[] blob = newLargeBlob();

		WindowCacheConfig wcc = new WindowCacheConfig();
		wcc.setStreamFileThreshold(1024);
		wcc.install();
		try (ObjectReader reader = db.newObjectReader()) {
			assertThat(blob.length
		}

		try (PackInserter ins = newInserter()) {
			blobId = ins.insert(OBJ_BLOB

			try (ObjectReader reader = ins.newReader()) {
				assertThat(blob.length
				assertBlob(reader
			}
		}

		assertPacksOnly();
		assertEquals(0

		try (ObjectReader reader = db.newObjectReader()) {
			try {
				reader.open(blobId);
				fail("Expected MissingObjectException");
			} catch (MissingObjectException expected) {
			}
		}
	}

	@Test
	public void readBackFallsBackToRepo() throws Exception {
		ObjectId blobId;
		byte[] blob = Constants.encode("foo contents");
		try (PackInserter ins = newInserter()) {
			assertThat(blob.length
			blobId = ins.insert(OBJ_BLOB
			ins.flush();
		}

		try (PackInserter ins = newInserter();
				ObjectReader reader = ins.newReader()) {
			assertBlob(reader
		}
	}

	@Test
	public void readBackSmallObjectBeforeLargeObject() throws Exception {
		WindowCacheConfig wcc = new WindowCacheConfig();
		wcc.setStreamFileThreshold(1024);
		wcc.install();

		ObjectId blobId1;
		ObjectId blobId2;
		ObjectId largeId;
		byte[] blob1 = Constants.encode("blob1");
		byte[] blob2 = Constants.encode("blob2");
		byte[] largeBlob = newLargeBlob();
		try (PackInserter ins = newInserter()) {
			assertThat(blob1.length
			assertThat(largeBlob.length

			blobId1 = ins.insert(OBJ_BLOB
			largeId = ins.insert(OBJ_BLOB

			try (ObjectReader reader = ins.newReader()) {
				assertBlob(reader
			}

			blobId2 = ins.insert(OBJ_BLOB

			try (ObjectReader reader = ins.newReader()) {
				assertBlob(reader
				assertBlob(reader
				assertBlob(reader
			}

			ins.flush();
		}

		try (ObjectReader reader = db.newObjectReader()) {
				assertBlob(reader
				assertBlob(reader
				assertBlob(reader
		}
	}

	private List<PackFile> listPacks() throws Exception {
		List<PackFile> fromOpenDb = listPacks(db);
		List<PackFile> reopened;
		try (FileRepository db2 = new FileRepository(db.getDirectory())) {
			reopened = listPacks(db2);
		}
		assertEquals(fromOpenDb.size()
		for (int i = 0 ; i < fromOpenDb.size(); i++) {
			PackFile a = fromOpenDb.get(i);
			PackFile b = reopened.get(i);
			assertEquals(a.getPackName()
			assertEquals(
					a.getPackFile().getAbsolutePath()
			assertEquals(a.getObjectCount()
			a.getObjectCount();
		}
		return fromOpenDb;
	}

	private static List<PackFile> listPacks(FileRepository db) throws Exception {
		return db.getObjectDatabase().getPacks().stream()
				.sorted(comparing(PackFile::getPackName)).collect(toList());
	}

	private PackInserter newInserter() {
		return db.getObjectDatabase().newPackInserter();
	}

	private static byte[] newLargeBlob() {
		byte[] blob = new byte[10240];
		random.nextBytes(blob);
		return blob;
	}

	private static String getInode(File f) throws Exception {
		BasicFileAttributes attrs = Files.readAttributes(
				f.toPath()
		Object k = attrs.fileKey();
		if (k == null) {
			return null;
		}
		Pattern p = Pattern.compile("^\\(dev=[^
		Matcher m = p.matcher(k.toString());
		return m.matches() ? m.group(1) : null;
	}

	private static void assertBlob(ObjectReader reader
			byte[] expected) throws Exception {
		ObjectLoader loader = reader.open(id);
		assertEquals(OBJ_BLOB
		assertEquals(expected.length
		try (ObjectStream s = loader.openStream()) {
			int n = (int) s.getSize();
			byte[] actual = new byte[n];
			assertEquals(n
			assertArrayEquals(expected
		}
	}

	private void assertPacksOnly() throws Exception {
		new BadFileCollector(f -> !f.endsWith(".pack") && !f.endsWith(".idx"))
				.assertNoBadFiles(db.getObjectDatabase().getDirectory());
	}

	private void assertNoObjects() throws Exception {
		new BadFileCollector(f -> true)
				.assertNoBadFiles(db.getObjectDatabase().getDirectory());
	}

	private static class BadFileCollector extends SimpleFileVisitor<Path> {
		private final Predicate<String> badName;
		private List<String> bad;

		BadFileCollector(Predicate<String> badName) {
			this.badName = badName;
		}

		void assertNoBadFiles(File f) throws IOException {
			bad = new ArrayList<>();
			Files.walkFileTree(f.toPath()
			if (!bad.isEmpty()) {
				fail("unexpected files in object directory: " + bad);
			}
		}

		@Override
		public FileVisitResult visitFile(Path file
			Path fileName = file.getFileName();
			if (fileName != null) {
				String name = fileName.toString();
				if (!attrs.isDirectory() && badName.test(name)) {
					bad.add(name);
				}
			}
			return FileVisitResult.CONTINUE;
		}
	}
}
