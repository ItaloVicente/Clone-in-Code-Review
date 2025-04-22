package org.eclipse.jgit.lfs.server.fs;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.test.LongObjectIdTestUtils;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DownloadTest extends LfsServerTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

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
		String id = putContent(TEXT).name().substring(0
		Path f = Paths.get(getTempDirectory().toString()
		String error = String.format(
				"Invalid pathInfo: '/%s' does not match '/{SHA-256}'"
		exception.expect(RuntimeException.class);
		exception.expectMessage(
				formatErrorMessage(SC_UNPROCESSABLE_ENTITY
		getContent(id
	}

	@Test
	public void testDownloadInvalidId()
			throws ClientProtocolException
		String TEXT = "test";
		String id = putContent(TEXT).name().replace('f'
		Path f = Paths.get(getTempDirectory().toString()
		String error = String.format("Invalid id: %s"
		exception.expect(RuntimeException.class);
		exception.expectMessage(
				formatErrorMessage(SC_UNPROCESSABLE_ENTITY
		getContent(id
	}

	@Test
	public void testDownloadNotFound()
			throws ClientProtocolException
		String TEXT = "test";
		AnyLongObjectId id = LongObjectIdTestUtils.hash(TEXT);
		Path f = Paths.get(getTempDirectory().toString()
		String error = String.format("Object '%s' not found"
		exception.expect(RuntimeException.class);
		exception.expectMessage(formatErrorMessage(SC_NOT_FOUND
		getContent(id
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
				MessageFormat.format("downloaded 10 MiB random data in {0}ms"
						(System.nanoTime() - start) / 1e6));
		assertEquals(expectedLen
		FileUtils.delete(f.toFile()

	}

	@SuppressWarnings("boxing")
	private String formatErrorMessage(int status
		return String.format("Status: %d {\"message\":\"%s\"}"
				message);
	}
}
