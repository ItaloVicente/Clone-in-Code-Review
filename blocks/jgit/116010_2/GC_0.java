
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class GcEmptyRefsFoldersTest extends GcTestCase {

	private static final String REF_01_FOLDER = "01";
	private static final String REF_02_FOLDER = "02";

	private Path refsEmptyDirs;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		refsEmptyDirs = Paths.get(repo.getObjectsDirectory().getAbsolutePath());
	}

	@Test
	public void emptyRefsFoldersAreDeleted() throws Exception {
		Path folderRef01 = Files
				.createDirectory(refsEmptyDirs.resolve(REF_01_FOLDER));
		Path folderRef02 = Files
				.createDirectory(refsEmptyDirs.resolve(REF_02_FOLDER));
		assertTrue(folderRef01.toFile().exists());
		assertTrue(folderRef02.toFile().exists());
		gc.gc();
		assertFalse(folderRef01.toFile().exists());
		assertFalse(folderRef02.toFile().exists());
	}


	@Test
	public void nonEmptyRefsFoldersAreKept() throws Exception {
		Path folderRef01 = Files
				.createDirectory(refsEmptyDirs.resolve(REF_01_FOLDER));
		Path folderRef02 = Files
				.createDirectory(refsEmptyDirs.resolve(REF_02_FOLDER));
		Path ref01 = Files.createFile(folderRef01.resolve("ref01"));
		Path ref02 = Files.createFile(folderRef02.resolve("ref02"));
		assertTrue(folderRef01.toFile().exists());
		assertTrue(folderRef02.toFile().exists());
		assertTrue(ref01.toFile().exists());
		assertTrue(ref02.toFile().exists());
		gc.gc();
		assertTrue(folderRef01.toFile().exists());
		assertTrue(folderRef02.toFile().exists());
		assertTrue(ref01.toFile().exists());
		assertTrue(ref02.toFile().exists());
	}

	@Test
	public void nonRefsFoldersAreKept() throws Exception {
		Path infoFolder = refsEmptyDirs.resolve("info");
		Path packFolder = refsEmptyDirs.resolve("pack");
		assertTrue(infoFolder.toFile().exists());
		assertTrue(packFolder.toFile().exists());
		gc.gc();
		assertTrue(infoFolder.toFile().exists());
		assertTrue(packFolder.toFile().exists());
	}
}
