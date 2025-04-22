
package org.eclipse.jgit.internal.storage.file;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_STRING_LENGTH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AbbreviationTest extends LocalDiskRepositoryTestCase {
	private FileRepository db;

	private ObjectReader reader;

	private TestRepository<Repository> test;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		db = createBareRepository();
		reader = db.newObjectReader();
		test = new TestRepository<>(db);
	}

	@Override
	@After
	public void tearDown() throws Exception {
		if (reader != null) {
			reader.close();
		}
	}

	@Test
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

	@Test
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

	@Test
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

	@Test
	public void testAbbreviateIsActuallyUnique() throws Exception {

		ObjectId id = id("9d5b926ed164e8ee88d3b8b1e525d699adda01ba");
		byte[] idBuf = toByteArray(id);
		List<PackedObjectInfo> objects = new ArrayList<>();
		for (int i = 0; i < 256; i++) {
			idBuf[9] = (byte) i;
			objects.add(new PackedObjectInfo(ObjectId.fromRaw(idBuf)));
		}

		String packName = "pack-" + id.name();
		File packDir = db.getObjectDatabase().getPackDirectory();
		File idxFile = new File(packDir
		File packFile = new File(packDir
		FileUtils.mkdir(packDir
		try (OutputStream dst = new BufferedOutputStream(
				new FileOutputStream(idxFile))) {
			PackIndexWriter writer = new PackIndexWriterV2(dst);
			writer.write(objects
		}

		try (FileOutputStream unused = new FileOutputStream(packFile)) {
		}

		assertEquals(id.abbreviate(20)

		AbbreviatedObjectId abbrev8 = id.abbreviate(8);
		Collection<ObjectId> matches = reader.resolve(abbrev8);
		assertNotNull(matches);
		assertEquals(objects.size()
		for (PackedObjectInfo info : objects)
			assertTrue("contains " + info.name()

		try {
			db.resolve(abbrev8.name());
			fail("did not throw AmbiguousObjectException");
		} catch (AmbiguousObjectException err) {
			assertEquals(abbrev8
			matches = err.getCandidates();
			assertNotNull(matches);
			assertEquals(objects.size()
			for (PackedObjectInfo info : objects)
				assertTrue("contains " + info.name()
		}

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
