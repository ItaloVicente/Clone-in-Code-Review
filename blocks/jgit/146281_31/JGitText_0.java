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
		cache = new SimpleLruCache<>(100);
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

	@Test
	public void testPutRepeated() {
		cache.put("a"
		assertEquals("A"
		assertEquals("B"
	}

	@Test
	public void testGetMissing() {
		assertEquals(null
	}

	@Test
	public void testPurge() {
		for (int i = 0; i < 110; i++) {
			cache.put("a" + i
		}
		assertEquals(100
		assertNull(cache.get("a0"));
		assertNull(cache.get("a9"));
		assertNotNull(cache.get("a10"));
		assertNotNull(cache.get("a99"));
	}

	@Test
	public void testRemove() {
		cache.put("a"
		cache.put("z"
		assertEquals("A"
		assertEquals(1
		assertNull(cache.get("a"));
		assertEquals("Z"
	}

	@Test
	public void testRemoveMissing() {
		cache.put("a"
		cache.put("z"
		assertNull(cache.remove("b"));
		assertEquals(2
		assertEquals("A"
		assertEquals("Z"
	}

	@Test
	public void testConfigure() {
		for (int i = 0; i < 100; i++) {
			cache.put("a" + i
		}
		assertEquals(100
		cache.configure(10);
		assertEquals(10
		assertNull(cache.get("a0"));
		assertNull(cache.get("a89"));
		assertNotNull(cache.get("a90"));
		assertNotNull(cache.get("a99"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConfigureInvalid() {
		cache.configure(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConfigureInvalid2() {
		cache.configure(-10);
	}
}
