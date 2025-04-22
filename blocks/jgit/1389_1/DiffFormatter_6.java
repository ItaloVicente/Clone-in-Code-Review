
package org.eclipse.jgit.storage.file;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_STRING_LENGTH;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.transport.PackedObjectInfo;

public class AbbreviationTest extends LocalDiskRepositoryTestCase {
	private FileRepository db;

	private ObjectReader reader;

	private TestRepository<FileRepository> test;

	public void setUp() throws Exception {
		super.setUp();
		db = createBareRepository();
		reader = db.newObjectReader();
		test = new TestRepository<FileRepository>(db);
	}

	public void tearDown() throws Exception {
		if (reader != null)
			reader.release();
	}

	public void testAbbreviateOnEmptyRepository() throws IOException {
		ObjectId id = id("9d5b926ed164e8ee88d3b8b1e525d699adda01ba");

		assertEquals(id.abbreviate(2)
		assertEquals(id.abbreviate(7)
		assertEquals(id.abbreviate(8)
		assertEquals(id.abbreviate(10)
		assertEquals(id.abbreviate(16)

		assertEquals(AbbreviatedObjectId.fromObjectId(id)
				reader.abbreviate(id

		Collection<ObjectId> matches;

		matches = reader.resolve(reader.abbreviate(id
		assertNotNull(matches);
		assertEquals(0

		matches = reader.resolve(AbbreviatedObjectId.fromObjectId(id));
		assertNotNull(matches);
		assertEquals(1
		assertEquals(id
	}

	public void testAbbreviateLooseBlob() throws Exception {
		ObjectId id = test.blob("test");

		assertEquals(id.abbreviate(2)
		assertEquals(id.abbreviate(7)
		assertEquals(id.abbreviate(8)
		assertEquals(id.abbreviate(10)
		assertEquals(id.abbreviate(16)

		Collection<ObjectId> matches = reader.resolve(reader.abbreviate(id
		assertNotNull(matches);
		assertEquals(1
		assertEquals(id

		assertEquals(id
	}

	public void testAbbreviatePackedBlob() throws Exception {
		RevBlob id = test.blob("test");
		test.branch("master").commit().add("test"
		test.packAndPrune();
		assertTrue(reader.has(id));

		assertEquals(id.abbreviate(7)
		assertEquals(id.abbreviate(8)
		assertEquals(id.abbreviate(10)
		assertEquals(id.abbreviate(16)

		Collection<ObjectId> matches = reader.resolve(reader.abbreviate(id
		assertNotNull(matches);
		assertEquals(1
		assertEquals(id

		assertEquals(id
	}

	@SuppressWarnings("unchecked")
	public void testAbbreviateIsActuallyUnique() throws Exception {

		ObjectId id = id("9d5b926ed164e8ee88d3b8b1e525d699adda01ba");
		byte[] idBuf = toByteArray(id);
		List<PackedObjectInfo> objects = new ArrayList<PackedObjectInfo>();
		for (int i = 0; i < 256; i++) {
			idBuf[9] = (byte) i;
			objects.add(new PackedObjectInfo(ObjectId.fromRaw(idBuf)));
		}

		String packName = "pack-" + id.name();
		File packDir = new File(db.getObjectDatabase().getDirectory()
		File idxFile = new File(packDir
		File packFile = new File(packDir
		packDir.mkdir();
		OutputStream dst = new BufferedOutputStream(new FileOutputStream(
				idxFile));
		try {
			PackIndexWriter writer = new PackIndexWriterV2(dst);
			writer.write(objects
		} finally {
			dst.close();
		}
		new FileOutputStream(packFile).close();
		db.openPack(packFile

		assertEquals(id.abbreviate(20)

		Collection<ObjectId> matches = reader.resolve(id.abbreviate(8));
		assertNotNull(matches);
		assertEquals(objects.size()
		for (PackedObjectInfo info : objects)
			assertTrue("contains " + info.name()

		assertNull("cannot resolve"
		assertEquals(id
	}

	private static ObjectId id(String name) {
		return ObjectId.fromString(name);
	}

	private static byte[] toByteArray(ObjectId id) throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream(OBJECT_ID_LENGTH);
		id.copyRawTo(buf);
		return buf.toByteArray();
	}
}
