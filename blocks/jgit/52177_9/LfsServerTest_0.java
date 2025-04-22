package org.eclipse.jgit.lfs.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.test.LongObjectIdTestUtils;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;

public class DownloadTest extends LfsServerTest {

	@Test
	public void testDownload() throws Exception {
		String TEXT = "test";
		AnyLongObjectId id = putContent(TEXT);
		Path f = Paths.get(getTempDirectory().toString()
		long len = getContent(id
		assertEquals(TEXT.length()
		FileUtils.delete(f.toFile()
	}

	@Test
	public void testDownloadInvalidPathInfo()
			throws ClientProtocolException
		String TEXT = "test";
		AnyLongObjectId id = putContent(TEXT);
		Path f = Paths.get(getTempDirectory().toString()
		try {
			getContent(id.name().substring(0
			fail("expected RuntimeException");
		} catch (RuntimeException e) {
			assertEquals("Status: 400 Bad Request"
					e.getMessage());
		}
	}

	@Test
	public void testDownloadInvalidId()
			throws ClientProtocolException
		String TEXT = "test";
		AnyLongObjectId id = putContent(TEXT);
		Path f = Paths.get(getTempDirectory().toString()
		try {
			getContent(id.name().replace('f'
			fail("expected RuntimeException");
		} catch (RuntimeException e) {
			assertEquals("Status: 400 Bad Request"
					e.getMessage());
		}
	}

	@Test
	public void testDownloadNotFound()
			throws ClientProtocolException
		String TEXT = "test";
		AnyLongObjectId id = LongObjectIdTestUtils.hash(TEXT);
		Path f = Paths.get(getTempDirectory().toString()
		try {
			getContent(id
			fail("expected RuntimeException");
		} catch (RuntimeException e) {
			assertEquals("Status: 404 Not Found"
					e.getMessage());
		}
	}

	@SuppressWarnings("boxing")
	@Test
	public void testLargeFileDownload() throws Exception {
		Path f = Paths.get(getTempDirectory().toString()
		long expectedLen = createPseudoRandomContentFile(f
		AnyLongObjectId id = putContent(f);
		Path f2 = Paths.get(getTempDirectory().toString()
		long start = System.nanoTime();
		long len = getContent(id
		System.out.println(
				MessageFormat.format("dowloaded 10 MiB random data in {0}ms"
						(System.nanoTime() - start) / 1e6));
		assertEquals(expectedLen
		FileUtils.delete(f.toFile()

	}
}
