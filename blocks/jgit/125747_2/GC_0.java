
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

public class GcDeleteEmptyRefsFoldersTest extends GcTestCase {
	private static final String REF_FOLDER_01 = "01";
	private static final String REF_FOLDER_02 = "02";

	private Path refsDir;
	private Path heads;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		refsDir = Paths.get(repo.getDirectory().getAbsolutePath())
				.resolve("refs");
		heads = refsDir.resolve("heads");
	}

	@Test
	public void emptyRefFoldersAreDeleted() throws Exception {
		FileTime fileTime = FileTime.from(Instant.now().minusSeconds(31));
		Path refDir01 = Files.createDirectory(heads.resolve(REF_FOLDER_01));
		Path refDir02 = Files.createDirectory(heads.resolve(REF_FOLDER_02));
		Files.setLastModifiedTime(refDir01
		Files.setLastModifiedTime(refDir02
		assertTrue(refDir01.toFile().exists());
		assertTrue(refDir02.toFile().exists());
		gc.gc();

		assertFalse(refDir01.toFile().exists());
		assertFalse(refDir02.toFile().exists());
	}

	@Test
	public void emptyRefFoldersAreKeptIfTheyAreTooRecent()
			throws Exception {
		Path refDir01 = Files.createDirectory(heads.resolve(REF_FOLDER_01));
		Path refDir02 = Files.createDirectory(heads.resolve(REF_FOLDER_02));
		assertTrue(refDir01.toFile().exists());
		assertTrue(refDir02.toFile().exists());
		gc.gc();

		assertTrue(refDir01.toFile().exists());
		assertTrue(refDir02.toFile().exists());
	}

	@Test
	public void nonEmptyRefsFoldersAreKept() throws Exception {
		Path refDir01 = Files.createDirectory(heads.resolve(REF_FOLDER_01));
		Path refDir02 = Files.createDirectory(heads.resolve(REF_FOLDER_02));
		Path ref01 = Files.createFile(refDir01.resolve("ref01"));
		Path ref02 = Files.createFile(refDir01.resolve("ref02"));
		assertTrue(refDir01.toFile().exists());
		assertTrue(refDir02.toFile().exists());
		assertTrue(ref01.toFile().exists());
		assertTrue(ref02.toFile().exists());
		gc.gc();
		assertTrue(refDir01.toFile().exists());
		assertTrue(refDir02.toFile().exists());
		assertTrue(ref01.toFile().exists());
		assertTrue(ref02.toFile().exists());
	}
}
