package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleLruCacheTest {

	private Path trash;

	private SimpleLruCache<String


	@Before
	public void setup() throws IOException {
		trash = Files.createTempDirectory("tmp_");
		cache = new SimpleLruCache<>(100
	}

	@Before
	@After
	public void tearDown() throws Exception {
		FileUtils.delete(trash.toFile()
				FileUtils.RECURSIVE | FileUtils.SKIP_MISSING);
	}

	@Test
	public void testPutGet() {
		cache.put("a"
		cache.put("z"
		assertEquals("A"
		assertEquals("Z"
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPurgeFactorTooLarge() {
		cache.configure(5
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPurgeFactorTooLarge2() {
		cache.configure(5
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPurgeFactorTooSmall() {
		cache.configure(5
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPurgeFactorTooSmall2() {
		cache.configure(5
	}

	@Test
	public void testGetMissing() {
		assertEquals(null
	}

	@Test
	public void testPurge() {
		for (int i = 0; i < 101; i++) {
			cache.put("a" + i
		}
		assertEquals(80
		assertNull(cache.get("a0"));
		assertNull(cache.get("a20"));
		assertNotNull(cache.get("a21"));
		assertNotNull(cache.get("a99"));
	}

	@Test
	public void testConfigure() {
		for (int i = 0; i < 100; i++) {
			cache.put("a" + i
		}
		assertEquals(100
		cache.configure(10
		assertEquals(7
		assertNull(cache.get("a0"));
		assertNull(cache.get("a92"));
		assertNotNull(cache.get("a93"));
		assertNotNull(cache.get("a99"));
	}
}
