package org.eclipse.jgit.niofs.internal.op;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Date;
import java.util.HashMap;

import org.eclipse.jgit.niofs.internal.AbstractTestInfra;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.jboss.byteman.contrib.bmunit.BMScript;
import org.jboss.byteman.contrib.bmunit.BMUnitConfig;
import org.jboss.byteman.contrib.bmunit.BMUnitRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(BMUnitRunner.class)
@BMUnitConfig(loadDirectory = "target/test-classes"
public class ConcurrentJGitUtilTest extends AbstractTestInfra {

	@BeforeClass
	public static void setup() {
		GitImpl.setRetryTimes(5);
	}

	@Test
	@BMScript(value = "byteman/retry/resolve_path.btm")
	public void testRetryResolvePath() throws IOException {
		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git git = new CreateRepository(gitFolder).execute().get();

		new Commit(git
				new HashMap<String
					{
						put("path/to/file1.txt"
					}
				}).execute();
		new Commit(git
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();

		try {
			assertNotNull(git.getPathInfo("master"
			assertNotNull(git.getPathInfo("master"
			assertNotNull(git.getPathInfo("master"
			assertNotNull(git.getPathInfo("master"
		} catch (Exception ex) {
			fail();
		}

		try {
			git.getPathInfo("master"
			fail("forced to fail!");
		} catch (RuntimeException ex) {
		}
	}

	@Test
	@BMScript(value = "byteman/retry/resolve_inputstream.btm")
	public void testRetryResolveInputStream() throws IOException {

		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git git = new CreateRepository(gitFolder).execute().get();

		new Commit(git
				new HashMap<String
					{
						put("path/to/file1.txt"
					}
				}).execute();
		new Commit(git
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();

		try {
			assertNotNull(git.blobAsInputStream("master"
			assertNotNull(git.blobAsInputStream("master"
			assertNotNull(git.blobAsInputStream("master"
			assertNotNull(git.blobAsInputStream("master"
		} catch (Exception ex) {
			fail();
		}

		try {
			assertNotNull(git.blobAsInputStream("master"
			fail("forced to fail!");
		} catch (NoSuchFileException ex) {
		}
	}

	@Test
	@BMScript(value = "byteman/retry/list_path_content.btm")
	public void testRetryListPathContent() throws IOException {

		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git git = new CreateRepository(gitFolder).execute().get();

		new Commit(git
				new HashMap<String
					{
						put("path/to/file1.txt"
					}
				}).execute();
		new Commit(git
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();

		try {
			assertNotNull(git.listPathContent("master"
			assertNotNull(git.listPathContent("master"
			assertNotNull(git.listPathContent("master"
			assertNotNull(git.listPathContent("master"
		} catch (Exception ex) {
			fail();
		}

		try {
			assertNotNull(git.listPathContent("master"
			fail("forced to fail!");
		} catch (RuntimeException ex) {
		}
	}

	@Test
	@BMScript(value = "byteman/retry/check_path.btm")
	public void testRetryCheckPath() throws IOException {

		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git git = new CreateRepository(gitFolder).execute().get();

		new Commit(git
				new HashMap<String
					{
						put("path/to/file1.txt"
					}
				}).execute();
		new Commit(git
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();

		try {
			assertNotNull(git.getPathInfo("master"
			assertNotNull(git.getPathInfo("master"
			assertNotNull(git.getPathInfo("master"
			assertNotNull(git.getPathInfo("master"
		} catch (Exception ex) {
			fail();
		}

		try {
			assertNotNull(git.getPathInfo("master"
			fail("forced to fail!");
		} catch (RuntimeException ex) {
		}
	}

	@Test
	@BMScript(value = "byteman/retry/get_last_commit.btm")
	public void testRetryGetLastCommit() throws IOException {

		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git git = new CreateRepository(gitFolder).execute().get();

		new Commit(git
				new HashMap<String
					{
						put("path/to/file1.txt"
					}
				}).execute();
		new Commit(git
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();

		try {
			assertNotNull(git.getLastCommit("master"));
			assertNotNull(git.getLastCommit("master"));
			assertNotNull(git.getLastCommit("master"));
			assertNotNull(git.getLastCommit("master"));
		} catch (Exception ex) {
			fail();
		}

		try {
			assertNotNull(git.getLastCommit("master"));
			fail("forced to fail!");
		} catch (RuntimeException ex) {
		}
	}

	@Test
	@BMScript(value = "byteman/retry/get_commits.btm")
	public void testRetryGetCommits() throws IOException {

		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder

		final Git git = new CreateRepository(gitFolder).execute().get();

		new Commit(git
				new HashMap<String
					{
						put("path/to/file1.txt"
					}
				}).execute();
		new Commit(git
				new HashMap<String
					{
						put("path/to/file2.txt"
					}
				}).execute();

		final RevCommit commit = git.getLastCommit("master");
		try {
			assertNotNull(git.listCommits(null
			assertNotNull(git.listCommits(null
			assertNotNull(git.listCommits(null
		} catch (Exception ex) {
			fail();
		}

		try {
			assertNotNull(git.listCommits(null
			fail("forced to fail!");
		} catch (RuntimeException ex) {
		}
	}
}
