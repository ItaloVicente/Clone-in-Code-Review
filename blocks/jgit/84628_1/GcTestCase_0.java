package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.junit.Test;

public class AutoGcTest extends GcTestCase {

	@Test
	public void testNotTooManyLooseObjects() {
		assertFalse("should not find too many loose objects"
				gc.tooManyLooseObjects());
	}

	@Test
	public void testTooManyLooseObjects() throws Exception {
		FileBasedConfig c = repo.getConfig();
		c.setInt(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_AUTO
		c.save();
		commitChain(10
		assertTrue("should find too many loose objects"
				gc.tooManyLooseObjects());
	}

	@Test
	public void testNotTooManyPacks() {
		assertFalse("should not find too many packs"
	}

	@Test
	public void testTooManyPacks() throws Exception {
		FileBasedConfig c = repo.getConfig();
		c.setInt(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_AUTOPACKLIMIT
		c.save();
		SampleDataRepositoryTestCase.copyCGitTestPacks(repo);

		assertTrue("should find too many packs"
	}
}
