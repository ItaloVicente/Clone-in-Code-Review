
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class GcOrphanFilesTest extends GcTestCase {
	private final static String PACK = "pack";

	private final static String BITMAP_File_1 = PACK + "-1.bitmap";

	private final static String IDX_File_2 = PACK + "-2.idx";

	private final static String IDX_File_malformed = PACK + "-1234idx";

	private final static String PACK_File_2 = PACK + "-2.pack";

	private final static String PACK_File_3 = PACK + "-3.pack";

	private File packDir;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		packDir = new File(repo.getObjectsDirectory()
	}

	@Test
	public void bitmapAndIdxDeletedButPackNot() throws Exception {
		createFileInPackFolder(BITMAP_File_1);
		createFileInPackFolder(IDX_File_2);
		createFileInPackFolder(PACK_File_3);
		gc.gc();
		assertFalse(new File(packDir
		assertFalse(new File(packDir
		assertTrue(new File(packDir

	}

	@Test
	public void bitmapDeletedButIdxAndPackNot() throws Exception {
		createFileInPackFolder(BITMAP_File_1);
		createFileInPackFolder(IDX_File_2);
		createFileInPackFolder(PACK_File_2);
		createFileInPackFolder(PACK_File_3);
		gc.gc();
		assertFalse(new File(packDir
		assertTrue(new File(packDir
		assertTrue(new File(packDir
		assertTrue(new File(packDir

	}

	@Test
	public void malformedIdxNotDeleted() throws Exception {
		createFileInPackFolder(IDX_File_malformed);
		gc.gc();
		assertTrue(new File(packDir
	}

	private void createFileInPackFolder(String fileName) throws IOException {
		if (!packDir.exists() || !packDir.isDirectory()) {
			assertTrue(packDir.mkdirs());
		}
		assertTrue(new File(packDir

	}

}
