
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class GcTemporaryFilesTest extends GcTestCase {
	private static final String TEMP_IDX = "gc_1234567890.idx_tmp";

	private static final String TEMP_PACK = "gc_1234567890.pack_tmp";

	private File packDir;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		packDir = repo.getObjectDatabase().getPackDirectory();
	}

	@Test
	public void temporaryPacksAndIdxAreDeleted() throws Exception {
		File tempIndex = new File(packDir
		File tempPack = new File(packDir
		if (!packDir.exists() || !packDir.isDirectory()) {
			assertTrue(packDir.mkdirs());
		}
		assertTrue(tempPack.createNewFile());
		assertTrue(tempIndex.createNewFile());
		assertTrue(tempIndex.exists());
		assertTrue(tempPack.exists());
		gc.gc();
		assertFalse(tempIndex.exists());
		assertFalse(tempPack.exists());
	}
}
