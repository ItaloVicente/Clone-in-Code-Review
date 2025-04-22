package org.eclipse.jgit.lfs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lib.StoredConfig;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LfsGitTest extends RepositoryTestCase {

	private static final String SMUDGE_NAME = org.eclipse.jgit.lib.Constants.BUILTIN_FILTER_PREFIX
			+ Constants.ATTR_FILTER_DRIVER_PREFIX
			+ org.eclipse.jgit.lib.Constants.ATTR_FILTER_TYPE_SMUDGE;

	private static final String CLEAN_NAME = org.eclipse.jgit.lib.Constants.BUILTIN_FILTER_PREFIX
			+ Constants.ATTR_FILTER_DRIVER_PREFIX
			+ org.eclipse.jgit.lib.Constants.ATTR_FILTER_TYPE_CLEAN;

	@BeforeClass
	public static void installLfs() {
		FilterCommandRegistry.register(SMUDGE_NAME
		FilterCommandRegistry.register(CLEAN_NAME
	}

	@AfterClass
	public static void removeLfs() {
		FilterCommandRegistry.unregister(SMUDGE_NAME);
		FilterCommandRegistry.unregister(CLEAN_NAME);
	}

	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		git.commit().setMessage("Initial commit").call();
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
		config.setString("filter"
		config.save();
	}

	@Test
	public void checkoutNonLfsPointer() throws Exception {
		String content = "size_t\nsome_function(void* ptr);\n";
		File smallFile = writeTrashFile("Test.txt"
		StringBuilder largeContent = new StringBuilder(
				LfsPointer.SIZE_THRESHOLD * 4);
		while (largeContent.length() < LfsPointer.SIZE_THRESHOLD * 4) {
			largeContent.append(content);
		}
		File largeFile = writeTrashFile("large.txt"
		fsTick(largeFile);
		git.add().addFilepattern("Test.txt").addFilepattern("large.txt").call();
		git.commit().setMessage("Text files").call();
		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("attributes").call();
		assertTrue(smallFile.delete());
		assertTrue(largeFile.delete());
		git.reset().setMode(ResetType.HARD).call();
		assertTrue(smallFile.exists());
		assertTrue(largeFile.exists());
		checkFile(smallFile
		checkFile(largeFile
		largeContent.append(content);
		writeTrashFile("large.txt"
		git.add().addFilepattern("large.txt").call();
		git.commit().setMessage("Large modified").call();
				+ "oid sha256:d041ab19bd7edd899b3c0450d0f61819f96672f0b22d26c9753abc62e1261614\n"
				+ "size 858\n";
		assertEquals("[.gitattributes
				+ "[Test.txt
						+ "[large.txt
				indexState(CONTENT));
		File savedFile = new File(db.getDirectory()
		savedFile = new File(savedFile
		savedFile = new File(savedFile
		savedFile = new File(savedFile
		savedFile = new File(savedFile
				"d041ab19bd7edd899b3c0450d0f61819f96672f0b22d26c9753abc62e1261614");
		String saved = new String(Files.readAllBytes(savedFile.toPath())
				StandardCharsets.UTF_8);
		assertEquals(saved

		assertTrue(smallFile.delete());
		assertTrue(largeFile.delete());
		git.reset().setMode(ResetType.HARD).call();
		assertTrue(smallFile.exists());
		assertTrue(largeFile.exists());
		checkFile(smallFile
		checkFile(largeFile
		assertEquals("[.gitattributes
				+ "[Test.txt
						+ "[large.txt
				indexState(CONTENT));
		git.add().addFilepattern("Test.txt").call();
		git.commit().setMessage("Small committed again").call();
				+ "oid sha256:9110463275fb0e2f0e9fdeaf84e598e62915666161145cf08927079119cc7814\n"
				+ "size 33\n";
		assertEquals("[.gitattributes
				+ "[Test.txt
						+ "[large.txt
				indexState(CONTENT));

		assertTrue(git.status().call().isClean());
	}
}
