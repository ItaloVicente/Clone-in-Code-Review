package org.eclipse.jgit.attributes;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.junit.Test;

public class AttributeFileTests extends RepositoryTestCase {

	@Test
	public void testTextAutoEolLf() throws Exception {
		writeTrashFile(".gitattributes"
		performTest("Test\r\nFile"
	}

	@Test
	public void testTextAutoEolCrLf() throws Exception {
		writeTrashFile(".gitattributes"
		performTest("Test\r\nFile"
	}

	private void performTest(String initial
			throws Exception {
		File dummy = writeTrashFile("dummy.foo"
		byte[] data = readTestResource("add.png");
		assertTrue("Expected some binary data"
		File binary = writeTrashFile("add.png"
		Files.write(binary.toPath()
		try (Git git = Git.wrap(db)) {
			git.add().addFilepattern(".").call();
			git.commit().setMessage("test commit").call();
			verifyIndexContent("dummy.foo"
					index.getBytes(StandardCharsets.UTF_8));
			verifyIndexContent("add.png"
			assertTrue("Should be able to delete " + dummy
			assertTrue("Should be able to delete " + binary
			git.reset().setMode(ResetType.HARD).call();
			assertTrue(dummy + " should exist"
			assertTrue(binary + " should exist"
			String textFile = RawParseUtils.decode(IO.readFully(dummy
			assertEquals("Unexpected text content"
			byte[] binaryFile = IO.readFully(binary
			assertArrayEquals("Unexpected binary content"
		}
	}

	private byte[] readTestResource(String name) throws Exception {
		try (InputStream in = new BufferedInputStream(
				getClass().getResourceAsStream(name))) {
			byte[] data = new byte[512];
			int read = in.read(data);
			if (read == data.length) {
				return data;
			}
			return Arrays.copyOf(data
		}
	}

	private void verifyIndexContent(String path
			throws Exception {
		DirCache dc = db.readDirCache();
		for (int i = 0; i < dc.getEntryCount(); ++i) {
			DirCacheEntry entry = dc.getEntry(i);
			if (path.equals(entry.getPathString())) {
				byte[] data = db.open(entry.getObjectId()
						.getCachedBytes();
				assertArrayEquals("Unexpected index content for " + path
						expectedContent
				return;
			}
		}
		fail("Path not found in index: " + path);
	}
}
