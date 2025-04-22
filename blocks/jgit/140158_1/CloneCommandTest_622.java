package org.eclipse.jgit.api;

import static org.eclipse.jgit.lib.Constants.DOT_GIT_MODULES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;
import org.junit.Test;

public class CleanCommandTest extends RepositoryTestCase {
	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);

		writeTrashFile("File1.txt"
		writeTrashFile("File2.txt"
		writeTrashFile("File3.txt"

		writeTrashFile("sub-noclean/File1.txt"
		writeTrashFile("sub-noclean/File2.txt"
		writeTrashFile("sub-clean/File4.txt"
		writeTrashFile("sub-noclean/Ignored.txt"
		writeTrashFile(".gitignore"
		writeTrashFile("ignored-dir/Ignored2.txt"

		git.add().addFilepattern("File1.txt").call();
		git.add().addFilepattern("sub-noclean/File1.txt").call();
		git.add().addFilepattern(".gitignore").call();
		git.commit().setMessage("Initial commit").call();
	}

	@Test
	public void testClean() throws NoWorkTreeException
		StatusCommand command = git.status();
		Status status = command.call();
		Set<String> files = status.getUntracked();
		assertTrue(files.size() > 0);

		Set<String> cleanedFiles = git.clean().call();

		status = git.status().call();
		files = status.getUntracked();

		assertTrue(cleanedFiles.contains("File2.txt"));
		assertTrue(cleanedFiles.contains("File3.txt"));
		assertTrue(!cleanedFiles.contains("sub-noclean/File1.txt"));
		assertTrue(cleanedFiles.contains("sub-noclean/File2.txt"));
		assertTrue(!cleanedFiles.contains("sub-clean/File4.txt"));
	}

	@Test
	public void testCleanDirs() throws NoWorkTreeException
		StatusCommand command = git.status();
		Status status = command.call();
		Set<String> files = status.getUntracked();
		assertTrue(files.size() > 0);

		Set<String> cleanedFiles = git.clean().setCleanDirectories(true).call();

		status = git.status().call();
		files = status.getUntracked();

		assertTrue(files.isEmpty());
		assertTrue(cleanedFiles.contains("File2.txt"));
		assertTrue(cleanedFiles.contains("File3.txt"));
		assertTrue(!cleanedFiles.contains("sub-noclean/File1.txt"));
		assertTrue(cleanedFiles.contains("sub-noclean/File2.txt"));
		assertTrue(cleanedFiles.contains("sub-clean/"));
	}

	@Test
	public void testCleanWithPaths() throws NoWorkTreeException
			GitAPIException {
		StatusCommand command = git.status();
		Status status = command.call();
		Set<String> files = status.getUntracked();
		assertTrue(files.size() > 0);

		Set<String> paths = new TreeSet<>();
		paths.add("File3.txt");
		Set<String> cleanedFiles = git.clean().setPaths(paths).call();

		status = git.status().call();
		files = status.getUntracked();
		assertTrue(files.size() == 3);
		assertTrue(cleanedFiles.contains("File3.txt"));
		assertFalse(cleanedFiles.contains("File2.txt"));
	}

	@Test
	public void testCleanWithDryRun() throws NoWorkTreeException
			GitAPIException {
		StatusCommand command = git.status();
		Status status = command.call();
		Set<String> files = status.getUntracked();
		assertTrue(files.size() > 0);

		Set<String> cleanedFiles = git.clean().setDryRun(true).call();

		status = git.status().call();
		files = status.getUntracked();

		assertEquals(4
		assertTrue(cleanedFiles.contains("File2.txt"));
		assertTrue(cleanedFiles.contains("File3.txt"));
		assertTrue(!cleanedFiles.contains("sub-noclean/File1.txt"));
		assertTrue(cleanedFiles.contains("sub-noclean/File2.txt"));
	}

	@Test
	public void testCleanDirsWithDryRun() throws NoWorkTreeException
			GitAPIException {
		StatusCommand command = git.status();
		Status status = command.call();
		Set<String> files = status.getUntracked();
		assertTrue(files.size() > 0);

		Set<String> cleanedFiles = git.clean().setDryRun(true)
				.setCleanDirectories(true).call();

		status = git.status().call();
		files = status.getUntracked();

		assertTrue(files.size() == 4);
		assertTrue(cleanedFiles.contains("File2.txt"));
		assertTrue(cleanedFiles.contains("File3.txt"));
		assertTrue(!cleanedFiles.contains("sub-noclean/File1.txt"));
		assertTrue(cleanedFiles.contains("sub-noclean/File2.txt"));
		assertTrue(cleanedFiles.contains("sub-clean/"));
	}

	@Test
	public void testCleanWithDryRunAndNoIgnore() throws NoWorkTreeException
			GitAPIException {
		Set<String> cleanedFiles = git.clean().setDryRun(true).setIgnore(false)
				.call();

		Status status = git.status().call();
		Set<String> files = status.getIgnoredNotInIndex();

		assertTrue(files.size() == 2);
		assertTrue(cleanedFiles.contains("sub-noclean/Ignored.txt"));
		assertTrue(!cleanedFiles.contains("ignored-dir/"));
	}

	@Test
	public void testCleanDirsWithDryRunAndNoIgnore()
			throws NoWorkTreeException
		Set<String> cleanedFiles = git.clean().setDryRun(true).setIgnore(false)
				.setCleanDirectories(true).call();

		Status status = git.status().call();
		Set<String> files = status.getIgnoredNotInIndex();

		assertTrue(files.size() == 2);
		assertTrue(cleanedFiles.contains("sub-noclean/Ignored.txt"));
		assertTrue(cleanedFiles.contains("ignored-dir/"));
	}

	@Test
	public void testCleanDirsWithPrefixFolder() throws Exception {
		String path = "sub/foo.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		Status beforeCleanStatus = git.status().call();
		assertTrue(beforeCleanStatus.getAdded().contains(path));

		Set<String> cleanedFiles = git.clean().setCleanDirectories(true).call();

		assertTrue(!cleanedFiles.contains(path + "/"));

		assertTrue(cleanedFiles.contains("File2.txt"));
		assertTrue(cleanedFiles.contains("File3.txt"));
		assertTrue(!cleanedFiles.contains("sub-noclean/File1.txt"));
		assertTrue(cleanedFiles.contains("sub-noclean/File2.txt"));
		assertTrue(cleanedFiles.contains("sub-clean/"));
		assertTrue(cleanedFiles.size() == 4);
	}

	@Test
	public void testCleanDirsWithSubmodule() throws Exception {
		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		String path = "sub";
		command.setPath(path);
		String uri = db.getDirectory().toURI().toString();
		command.setURI(uri);
		try (Repository repo = command.call()) {
		}

		Status beforeCleanStatus = git.status().call();
		assertTrue(beforeCleanStatus.getAdded().contains(DOT_GIT_MODULES));
		assertTrue(beforeCleanStatus.getAdded().contains(path));

		Set<String> cleanedFiles = git.clean().setCleanDirectories(true).call();

		assertTrue(!cleanedFiles.contains(path + "/"));

		assertTrue(cleanedFiles.contains("File2.txt"));
		assertTrue(cleanedFiles.contains("File3.txt"));
		assertTrue(!cleanedFiles.contains("sub-noclean/File1.txt"));
		assertTrue(cleanedFiles.contains("sub-noclean/File2.txt"));
		assertTrue(cleanedFiles.contains("sub-clean/"));
		assertTrue(cleanedFiles.size() == 4);
	}

	@Test
	public void testCleanDirsWithRepository() throws Exception {
		String innerRepoName = "inner-repo";
		File innerDir = new File(trash
		innerDir.mkdir();
		InitCommand initRepoCommand = new InitCommand();
		initRepoCommand.setDirectory(innerDir);
		initRepoCommand.call();

		Status beforeCleanStatus = git.status().call();
		Set<String> untrackedFolders = beforeCleanStatus.getUntrackedFolders();
		Set<String> untrackedFiles = beforeCleanStatus.getUntracked();

		assertTrue(untrackedFiles.contains(innerRepoName));

		assertTrue(!untrackedFolders.contains(innerRepoName));

		Set<String> cleanedFiles = git.clean().setCleanDirectories(true).call();

		assertTrue(!cleanedFiles.contains(innerRepoName + "/"));

		assertTrue(cleanedFiles.contains("File2.txt"));
		assertTrue(cleanedFiles.contains("File3.txt"));
		assertTrue(!cleanedFiles.contains("sub-noclean/File1.txt"));
		assertTrue(cleanedFiles.contains("sub-noclean/File2.txt"));
		assertTrue(cleanedFiles.contains("sub-clean/"));
		assertTrue(cleanedFiles.size() == 4);

		Set<String> forceCleanedFiles = git.clean().setCleanDirectories(true)
				.setForce(true).call();

		assertTrue(forceCleanedFiles.contains(innerRepoName + "/"));
	}

	@Test
	public void testFilesShouldBeCleanedInSubSubFolders()
			throws IOException
		writeTrashFile(".gitignore"
				"/ignored-dir\n/sub-noclean/Ignored.txt\n/this_is_ok\n/this_is/not_ok\n");
		git.add().addFilepattern(".gitignore").call();
		git.commit().setMessage("adding .gitignore").call();
		writeTrashFile("this_is_ok/more/subdirs/file.txt"
		writeTrashFile("this_is/not_ok/more/subdirs/file.txt"
		git.clean().setCleanDirectories(true).setIgnore(false).call();
	}
}
