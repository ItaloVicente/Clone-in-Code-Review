package org.eclipse.jgit.ant.tasks;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.util.FS;
import org.junit.Before;
import org.junit.Test;

public class GitCloneTaskTest extends LocalDiskRepositoryTestCase {

	private GitCloneTask task;
	private Project project;

	@Before
	public void before() {
		project = new Project();
		enableLogging();
		project.addTaskDefinition("git-clone"
		task = (GitCloneTask) project.createTask("git-clone");
	}

	@Test(expected = BuildException.class)
	public void shouldRaiseErrorOnNoUrl() throws Exception {
		task.execute();
	}

	@Test(expected = BuildException.class)
	public void shouldRaiseErrorOnEmptyUrl() throws Exception {
		task.setUri("");
		task.execute();
	}

	@Test(expected = BuildException.class)
	public void shouldRaiseErrorOnBadUrl() throws Exception {
		task.execute();
	}

	@Test(expected = BuildException.class)
	public void shouldRaiseErrorOnBadSourceURL() throws Exception {
		task.execute();
	}

	@Test
	public void shouldCloneAValidGitRepository() throws Exception {
		FileRepository repo = createBareRepository();
		File directory = repo.getDirectory();
		File dest = createTempFile();
		task.setDest(dest);
		task.execute();

		assertTrue(RepositoryCache.FileKey.isGitRepository(new File(dest
	}

	@Test
	public void shouldCreateABareCloneOfAValidGitRepository() throws Exception {
		FileRepository repo = createBareRepository();
		File directory = repo.getDirectory();
		task.setBare(true);
		File dest = createTempFile();
		task.setDest(dest);
		task.execute();

		assertTrue(RepositoryCache.FileKey.isGitRepository(dest
	}

	private void enableLogging() {
		DefaultLogger logger = new DefaultLogger();
		logger.setOutputPrintStream(System.out);
		logger.setErrorPrintStream(System.err);
		logger.setMessageOutputLevel(Project.MSG_INFO);
		project.addBuildListener(logger);
	}

}
