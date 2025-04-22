
package org.eclipse.jgit.internal.storage.dfs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.zip.Deflater;

import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.TestRng;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.junit.Before;
import org.junit.Test;

public class DfsInserterTest {
	InMemoryRepository db;

	@Before
	public void setUp() {
		db = new InMemoryRepository(new DfsRepositoryDescription("test"));
	}

	@Test
	public void testInserterDiscardsPack() throws IOException {
		ObjectInserter ins = db.newObjectInserter();
		ins.insert(Constants.OBJ_BLOB
		ins.insert(Constants.OBJ_BLOB
		assertEquals(0

		ins.release();
		assertEquals(0
	}

	@Test
	public void testReadFromInserterSmallObjects() throws IOException {
		ObjectInserter ins = db.newObjectInserter();
		ObjectId id1 = ins.insert(Constants.OBJ_BLOB
		ObjectId id2 = ins.insert(Constants.OBJ_BLOB
		assertEquals(0

		ObjectReader reader = ins.newReader();
		assertEquals("foo"
		assertEquals("bar"
		assertEquals(0
		ins.flush();
		assertEquals(1
	}

	@Test
	public void testReadFromInserterLargerObjects() throws IOException {
		db.getObjectDatabase().getReaderOptions().setStreamFileThreshold(512);
		DfsBlockCache.reconfigure(new DfsBlockCacheConfig()
			.setBlockSize(512)
			.setBlockLimit(2048));

		byte[] data = new TestRng(JGitTestUtil.getName()).nextBytes(8192);
		DfsInserter ins = (DfsInserter) db.newObjectInserter();
		ins.setCompressionLevel(Deflater.NO_COMPRESSION);
		ObjectId id1 = ins.insert(Constants.OBJ_BLOB
		assertEquals(0

		ObjectReader reader = ins.newReader();
		assertTrue(Arrays.equals(data
		assertEquals(0
		ins.flush();

		List<DfsPackDescription> packs = db.getObjectDatabase().listPacks();
		assertEquals(1
		assertTrue(packs.get(0).getFileSize(PackExt.PACK) > 2048);
	}

	@Test
	public void testReadFromFallback() throws IOException {
		ObjectInserter ins = db.newObjectInserter();
		ObjectId id1 = ins.insert(Constants.OBJ_BLOB
		ins.flush();
		ObjectId id2 = ins.insert(Constants.OBJ_BLOB
		assertEquals(1

		ObjectReader reader = ins.newReader();
		assertEquals("foo"
		assertEquals("bar"
		assertEquals(1
		ins.flush();
		assertEquals(2
	}

	@Test
	public void testReaderResolve() throws IOException {
		ObjectInserter ins = db.newObjectInserter();
		ObjectId id1 = ins.insert(Constants.OBJ_BLOB
		ins.flush();
		ObjectId id2 = ins.insert(Constants.OBJ_BLOB
		String abbr1 = ObjectId.toString(id1).substring(0
		String abbr2 = ObjectId.toString(id2).substring(0
		assertFalse(abbr1.equals(abbr2));

		ObjectReader reader = ins.newReader();
		Collection<ObjectId> objs;
		objs = reader.resolve(AbbreviatedObjectId.fromString(abbr1));
		assertEquals(1
		assertEquals(id1

		objs = reader.resolve(AbbreviatedObjectId.fromString(abbr2));
		assertEquals(1
		assertEquals(id2
	}

	private static String readString(ObjectLoader loader) throws IOException {
		return RawParseUtils.decode(readStream(loader));
	}

	private static byte[] readStream(ObjectLoader loader) throws IOException {
		ByteBuffer bb = IO.readWholeStream(loader.openStream()
		byte[] buf = new byte[bb.remaining()];
		bb.get(buf);
		return buf;
	}
}
