
package org.eclipse.jgit.internal.storage.file;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;

public class FileRepositoryBuilderTest extends LocalDiskRepositoryTestCase {
	@Test
	public void testShouldAutomagicallyDetectGitDirectory() throws Exception {
		Repository r = createWorkRepository();
		File d = new File(r.getDirectory()
		FileUtils.mkdir(d);

		assertEquals(r.getDirectory()
				.findGitDir(d).getGitDir());
	}

	@Test
	public void emptyRepositoryFormatVersion() throws Exception {
		Repository r = createWorkRepository();
		StoredConfig config = r.getConfig();
		config.setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION
		config.save();

		try (FileRepository repo = new FileRepository(r.getDirectory())) {
		}
	}

	@Test
	public void invalidRepositoryFormatVersion() throws Exception {
		Repository r = createWorkRepository();
		StoredConfig config = r.getConfig();
		config.setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION
		config.save();

		try (FileRepository repo = new FileRepository(r.getDirectory())) {
			fail("IllegalArgumentException not thrown");
		} catch (IllegalArgumentException e) {
			assertNotNull(e.getMessage());
		}
	}

	@Test
	public void unknownRepositoryFormatVersion() throws Exception {
		Repository r = createWorkRepository();
		StoredConfig config = r.getConfig();
		config.setLong(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION
		config.save();

		try (FileRepository repo = new FileRepository(r.getDirectory())) {
			fail("IOException not thrown");
		} catch (IOException e) {
			assertNotNull(e.getMessage());
		}
	}

	@Test
	public void absoluteGitDirRef() throws Exception {
		Repository repo1 = createWorkRepository();
		File dir = createTempDirectory("dir");
		File dotGit = new File(dir
		try (BufferedWriter writer = Files.newBufferedWriter(dotGit.toPath()
				UTF_8)) {
			writer.append("gitdir: " + repo1.getDirectory().getAbsolutePath());
		}
		FileRepositoryBuilder builder = new FileRepositoryBuilder();

		builder.setWorkTree(dir);
		builder.setMustExist(true);
		Repository repo2 = builder.build();

		assertEquals(repo1.getDirectory().getAbsolutePath()
				repo2.getDirectory().getAbsolutePath());
		assertEquals(dir
	}

	@Test
	public void relativeGitDirRef() throws Exception {
		Repository repo1 = createWorkRepository();
		File dir = new File(repo1.getWorkTree()
		assertTrue(dir.mkdir());
		File dotGit = new File(dir
		try (BufferedWriter writer = Files.newBufferedWriter(dotGit.toPath()
				UTF_8)) {
			writer.append("gitdir: ../" + Constants.DOT_GIT);
		}
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		builder.setWorkTree(dir);
		builder.setMustExist(true);
		Repository repo2 = builder.build();

		assertEquals(repo1.getDirectory().getCanonicalPath()
				repo2.getDirectory().getCanonicalPath());
		assertEquals(dir
	}

	@Test
	public void scanWithGitDirRef() throws Exception {
		Repository repo1 = createWorkRepository();
		File dir = createTempDirectory("dir");
		File dotGit = new File(dir
		try (BufferedWriter writer = Files.newBufferedWriter(dotGit.toPath()
				UTF_8)) {
			writer.append(
					"gitdir: " + repo1.getDirectory().getAbsolutePath());
		}
		FileRepositoryBuilder builder = new FileRepositoryBuilder();

		builder.setWorkTree(dir);
		builder.findGitDir(dir);
		assertEquals(repo1.getDirectory().getAbsolutePath()
				builder.getGitDir().getAbsolutePath());
		builder.setMustExist(true);
		Repository repo2 = builder.build();

		assertEquals(repo1.getDirectory().getCanonicalPath()
				repo2.getDirectory().getCanonicalPath());
		assertEquals(dir
	}
}
