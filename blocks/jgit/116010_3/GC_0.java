
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class GcEmptyObjectsFoldersTest extends GcTestCase {

	private static final String OBJ_DIR_01 = "01";
	private static final String OBJ_DIR_02 = "02";

	private Path objEmptyDirs;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		objEmptyDirs = Paths.get(repo.getObjectsDirectory().getAbsolutePath());
	}

	@Test
	public void emptyObjFoldersAreDeleted() throws Exception {
		Path objDir01 = Files
				.createDirectory(objEmptyDirs.resolve(OBJ_DIR_01));
		Path objDir02 = Files
				.createDirectory(objEmptyDirs.resolve(OBJ_DIR_02));
		assertTrue(objDir01.toFile().exists());
		assertTrue(objDir02.toFile().exists());
		gc.gc();
		assertFalse(objDir01.toFile().exists());
		assertFalse(objDir02.toFile().exists());
	}


	@Test
	public void nonEmptyObjFoldersAreKept() throws Exception {
		Path objDir01 = Files
				.createDirectory(objEmptyDirs.resolve(OBJ_DIR_01));
		Path objDir02 = Files
				.createDirectory(objEmptyDirs.resolve(OBJ_DIR_02));
		Path obj01 = Files.createFile(objDir01.resolve("obj01"));
		Path obj02 = Files.createFile(objDir02.resolve("obj02"));
		assertTrue(objDir01.toFile().exists());
		assertTrue(objDir02.toFile().exists());
		assertTrue(obj01.toFile().exists());
		assertTrue(obj02.toFile().exists());
		gc.gc();
		assertTrue(objDir01.toFile().exists());
		assertTrue(objDir02.toFile().exists());
		assertTrue(obj01.toFile().exists());
		assertTrue(obj02.toFile().exists());
	}

	@Test
	public void nonRefsFoldersAreKept() throws Exception {
		Path infoFolder = objEmptyDirs.resolve("info");
		Path packFolder = objEmptyDirs.resolve("pack");
		assertTrue(infoFolder.toFile().exists());
		assertTrue(packFolder.toFile().exists());
		gc.gc();
		assertTrue(infoFolder.toFile().exists());
		assertTrue(packFolder.toFile().exists());
	}
}
