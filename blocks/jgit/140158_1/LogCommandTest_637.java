package org.eclipse.jgit.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.junit.MockSystemReader;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Before;
import org.junit.Test;

public class InitCommandTest extends RepositoryTestCase {

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testInitRepository()
			throws IOException
		File directory = createTempDirectory("testInitRepository");
		InitCommand command = new InitCommand();
		command.setDirectory(directory);
		try (Git git = command.call()) {
			assertNotNull(git.getRepository());
		}
	}

	@Test
	public void testInitNonEmptyRepository() throws IOException
			JGitInternalException
		File directory = createTempDirectory("testInitRepository2");
		File someFile = new File(directory
		someFile.createNewFile();
		assertTrue(someFile.exists());
		assertTrue(directory.listFiles().length > 0);
		InitCommand command = new InitCommand();
		command.setDirectory(directory);
		try (Git git = command.call()) {
			assertNotNull(git.getRepository());
		}
	}

	@Test
	public void testInitBareRepository() throws IOException
			JGitInternalException
		File directory = createTempDirectory("testInitBareRepository");
		InitCommand command = new InitCommand();
		command.setDirectory(directory);
		command.setBare(true);
		try (Git git = command.call()) {
			Repository repository = git.getRepository();
			assertNotNull(repository);
			assertTrue(repository.isBare());
		}
	}

	@Test
	public void testInitWithExplicitGitDir() throws IOException
			JGitInternalException
		File wt = createTempDirectory("testInitRepositoryWT");
		File gitDir = createTempDirectory("testInitRepositoryGIT");
		InitCommand command = new InitCommand();
		command.setDirectory(wt);
		command.setGitDir(gitDir);
		try (Git git = command.call()) {
			Repository repository = git.getRepository();
			assertNotNull(repository);
			assertEqualsFile(wt
			assertEqualsFile(gitDir
		}
	}

	@Test
	public void testInitWithOnlyExplicitGitDir() throws IOException
			JGitInternalException
		MockSystemReader reader = (MockSystemReader) SystemReader.getInstance();
		reader.setProperty(Constants.OS_USER_DIR
				.getAbsolutePath());
		File gitDir = createTempDirectory("testInitRepository/.git");
		InitCommand command = new InitCommand();
		command.setGitDir(gitDir);
		try (Git git = command.call()) {
			Repository repository = git.getRepository();
			assertNotNull(repository);
			assertEqualsFile(gitDir
			assertEqualsFile(new File(reader.getProperty("user.dir"))
					repository.getWorkTree());
		}
	}

	@Test(expected = IllegalStateException.class)
	public void testInitBare_DirAndGitDirMustBeEqual() throws IOException
			JGitInternalException
		File gitDir = createTempDirectory("testInitRepository.git");
		InitCommand command = new InitCommand();
		command.setBare(true);
		command.setDirectory(gitDir);
		command.setGitDir(new File(gitDir
		command.call();
	}

	@Test
	public void testInitWithDefaultsNonBare() throws JGitInternalException
			GitAPIException
		MockSystemReader reader = (MockSystemReader) SystemReader.getInstance();
		reader.setProperty(Constants.OS_USER_DIR
				.getAbsolutePath());
		InitCommand command = new InitCommand();
		command.setBare(false);
		try (Git git = command.call()) {
			Repository repository = git.getRepository();
			assertNotNull(repository);
			assertEqualsFile(new File(reader.getProperty("user.dir")
					repository.getDirectory());
			assertEqualsFile(new File(reader.getProperty("user.dir"))
					repository.getWorkTree());
		}
	}

	@Test(expected = NoWorkTreeException.class)
	public void testInitWithDefaultsBare() throws JGitInternalException
			GitAPIException
		MockSystemReader reader = (MockSystemReader) SystemReader.getInstance();
		reader.setProperty(Constants.OS_USER_DIR
				.getAbsolutePath());
		InitCommand command = new InitCommand();
		command.setBare(true);
		try (Git git = command.call()) {
			Repository repository = git.getRepository();
			assertNotNull(repository);
			assertEqualsFile(new File(reader.getProperty("user.dir"))
					repository.getDirectory());
			assertNull(repository.getWorkTree());
		}
	}

	@Test(expected = IllegalStateException.class)
	public void testInitNonBare_GitdirAndDirShouldntBeSame()
			throws JGitInternalException
		File gitDir = createTempDirectory("testInitRepository.git");
		InitCommand command = new InitCommand();
		command.setBare(false);
		command.setGitDir(gitDir);
		command.setDirectory(gitDir);
		command.call().getRepository();
	}
}
