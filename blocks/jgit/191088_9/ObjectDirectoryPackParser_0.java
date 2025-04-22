package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.zip.Deflater;

import org.eclipse.jgit.internal.storage.file.ObjectDirectoryPackParser;
import org.eclipse.jgit.internal.storage.file.Pack;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ObjectDirectoryPackParserTest extends RepositoryTestCase {

	@Before
	public void setup() throws IOException {
		FileBasedConfig jGitConfig = mockSystemReader.getJGitConfig();
		jGitConfig.setInt(ConfigConstants.CONFIG_PACK_SECTION
				ConfigConstants.CONFIG_KEY_MIN_BYTES_OBJ_SIZE_INDEX
		jGitConfig.save();
	}

	@Test
	public void testGitPack() throws IOException {
		File packFile = JGitTestUtil.getTestResourceFile("pack-34be9032ac282b11fa9babdc2b2a93ca996c9c2f.pack");
		try (InputStream is = new FileInputStream(packFile)) {
			ObjectDirectoryPackParser p = index(is);
			p.parse(NullProgressMonitor.INSTANCE);

			Pack pack = p.getPack();
			assertTrue(pack.hasObjSizeIndex());

			ObjectId blob1 = ObjectId
					.fromString("6ff87c4664981e4397625791c8ea3bbb5f2279a3");
			ObjectId blob2 = ObjectId
					.fromString("5b6e7c66c276e7610d4a73c70ec1a1f7c1003259");
			assertEquals(18787
			assertEquals(18009

			assertEquals(db.getObjectDatabase().open(blob1).getSize()
					pack.getIndexedObjectSize(blob1));
			assertEquals(db.getObjectDatabase().open(blob2).getSize()
					pack.getIndexedObjectSize(blob2));

		}
	}

	@Test
	public void testAnotherGitPack() throws IOException {
		File packFile = JGitTestUtil.getTestResourceFile("pack-df2982f284bbabb6bdb59ee3fcc6eb0983e20371.pack");
		try (InputStream is = new FileInputStream(packFile)) {
			ObjectDirectoryPackParser p = index(is);
			p.parse(NullProgressMonitor.INSTANCE);
			Pack pack = p.getPack();

			assertEquals(-1
					.fromString("15fae9e651043de0fd1deef588aa3fbf5a7a41c6")));

			assertEquals(10
					.fromString("8230f48330e0055d9e0bc5a2a77718f6dd9324b8")));

			assertEquals(-1
					.fromString("d0114ab8ac326bab30e3a657a0397578c5a1af88")));

			assertEquals(-2
					.fromString("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")));
		}
	}

	@Test
	public void testTinyThinPack() throws Exception {
		String base = "abcdefghijklmn";
		RevBlob a;
		try (TestRepository d = new TestRepository<Repository>(db)) {
			a = d.blob(base);
		}

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);

		packHeader(pack

		pack.write((Constants.OBJ_REF_DELTA) << 4 | 4);
		a.copyRawTo(pack);
		deflate(pack
				(byte) (base.length() + 1)
				0x1

		digest(pack);

		ObjectDirectoryPackParser p = index(new ByteArrayInputStream(pack.toByteArray()));
		p.setAllowThin(true);
		p.parse(NullProgressMonitor.INSTANCE);

		Pack writtenPack = p.getPack();
		assertEquals(base.length()
		assertEquals(base.length() + 1
				writtenPack.getIndexedObjectSize(ObjectId.fromString(
						"f177875498138143c9657cc52b049ad4d20d5223")));
	}

	@Test
	public void testPackWithDuplicateBlob() throws Exception {
		final byte[] data = Constants.encode("0123456789abcdefg");
		RevBlob blob;
		try (TestRepository<Repository> d = new TestRepository<>(db)) {
			blob = d.blob(data);
			assertTrue(db.getObjectDatabase().has(blob));
		}

		TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);
		packHeader(pack
		pack.write((Constants.OBJ_BLOB) << 4 | 0x80 | 1);
		pack.write(1);
		deflate(pack
		digest(pack);

		ObjectDirectoryPackParser p = index(
				new ByteArrayInputStream(pack.toByteArray()));
		p.setAllowThin(false);
		p.parse(NullProgressMonitor.INSTANCE);

		assertEquals(data.length
	}

	private static void packHeader(TemporaryBuffer.Heap tinyPack
			throws IOException {
		final byte[] hdr = new byte[8];
		NB.encodeInt32(hdr
		NB.encodeInt32(hdr

		tinyPack.write(Constants.PACK_SIGNATURE);
		tinyPack.write(hdr
	}

	private static void deflate(TemporaryBuffer.Heap tinyPack
			final byte[] content)
			throws IOException {
		final Deflater deflater = new Deflater();
		final byte[] buf = new byte[128];
		deflater.setInput(content
		deflater.finish();
		do {
			final int n = deflater.deflate(buf
			if (n > 0)
				tinyPack.write(buf
		} while (!deflater.finished());
	}

	private static void digest(TemporaryBuffer.Heap buf) throws IOException {
		MessageDigest md = Constants.newMessageDigest();
		md.update(buf.toByteArray());
		buf.write(md.digest());
	}

	private ObjectInserter inserter;

	@After
	public void release() {
		if (inserter != null) {
			inserter.close();
		}
	}

	private ObjectDirectoryPackParser index(InputStream in) throws IOException {
		if (inserter == null)
			inserter = db.newObjectInserter();
		return (ObjectDirectoryPackParser) inserter.newPackParser(in);
	}
}
