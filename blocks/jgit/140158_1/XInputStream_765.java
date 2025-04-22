
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.storage.file.WindowCacheConfig;
import org.junit.Test;

public class WindowCacheReconfigureTest extends RepositoryTestCase {
	@Test
	public void testConfigureCache_PackedGitLimit_0() {
		try {
			final WindowCacheConfig cfg = new WindowCacheConfig();
			cfg.setPackedGitLimit(0);
			cfg.install();
			fail("incorrectly permitted PackedGitLimit = 0");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testConfigureCache_PackedGitWindowSize_0() {
		try {
			final WindowCacheConfig cfg = new WindowCacheConfig();
			cfg.setPackedGitWindowSize(0);
			cfg.install();
			fail("incorrectly permitted PackedGitWindowSize = 0");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid window size"
		}
	}

	@Test
	public void testConfigureCache_PackedGitWindowSize_512() {
		try {
			final WindowCacheConfig cfg = new WindowCacheConfig();
			cfg.setPackedGitWindowSize(512);
			cfg.install();
			fail("incorrectly permitted PackedGitWindowSize = 512");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid window size"
		}
	}

	@Test
	public void testConfigureCache_PackedGitWindowSize_4097() {
		try {
			final WindowCacheConfig cfg = new WindowCacheConfig();
			cfg.setPackedGitWindowSize(4097);
			cfg.install();
			fail("incorrectly permitted PackedGitWindowSize = 4097");
		} catch (IllegalArgumentException e) {
			assertEquals("Window size must be power of 2"
		}
	}

	@Test
	public void testConfigureCache_PackedGitOpenFiles_0() {
		try {
			final WindowCacheConfig cfg = new WindowCacheConfig();
			cfg.setPackedGitOpenFiles(0);
			cfg.install();
			fail("incorrectly permitted PackedGitOpenFiles = 0");
		} catch (IllegalArgumentException e) {
			assertEquals("Open files must be >= 1"
		}
	}

	@Test
	public void testConfigureCache_PackedGitWindowSizeAbovePackedGitLimit() {
		try {
			final WindowCacheConfig cfg = new WindowCacheConfig();
			cfg.setPackedGitLimit(1024);
			cfg.setPackedGitWindowSize(8192);
			cfg.install();
			fail("incorrectly permitted PackedGitWindowSize > PackedGitLimit");
		} catch (IllegalArgumentException e) {
			assertEquals("Window size must be < limit"
		}
	}

	@Test
	public void testConfigureCache_Limits1() {
		final WindowCacheConfig cfg = new WindowCacheConfig();
		cfg.setPackedGitLimit(6 * 4096 / 5);
		cfg.setPackedGitWindowSize(4096);
		cfg.install();
	}
}
