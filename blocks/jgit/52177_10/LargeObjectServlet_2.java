package org.eclipse.jgit.lfs.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.lib.LongObjectId;
import org.eclipse.jgit.lfs.test.LongObjectIdTestUtils;
import org.junit.Test;

public class UploadTest extends LfsServerTest {

	@Test
	public void testUpload() throws Exception {
		String TEXT = "test";
		AnyLongObjectId id = putContent(TEXT);
		assertTrue("expect object " + id.name() + " to exist"
				repository.exists(id));
		assertEquals("expected object length " + TEXT.length()
				repository.getLength(id));
	}

	@Test
	public void testCorruptUpload() throws Exception {
		String TEXT = "test";
		AnyLongObjectId id = LongObjectIdTestUtils.hash("wrongHash");
		try {
			putContent(id
			fail("expected RuntimeException(\"Status 400\")");
		} catch (RuntimeException e) {
			assertEquals("Status: 400. Bad Request"
		}
		assertFalse("expect object " + id.name() + " not to exist"
				repository.exists(id));
	}

	@SuppressWarnings("boxing")
	@Test
	public void testLargeFileUpload() throws Exception {
		Path f = Paths.get(getTempDirectory().toString()
		createPseudoRandomContentFile(f
		long start = System.nanoTime();
		LongObjectId id = putContent(f);
		System.out.println(
				MessageFormat.format("uploaded 10 MiB random data in {0}ms"
						(System.nanoTime() - start) / 1e6));
		assertTrue("expect object " + id.name() + " to exist"
				repository.exists(id));
		assertEquals("expected object length " + Files.size(f)
				repository.getLength(id));
	}
}
