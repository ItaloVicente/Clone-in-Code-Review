package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.eclipse.jgit.niofs.fs.attribute.VersionRecord;
import org.eclipse.jgit.niofs.fs.options.SquashOption;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.GetRef;
import org.eclipse.jgit.revwalk.RevCommit;
import org.jboss.byteman.contrib.bmunit.BMScript;
import org.jboss.byteman.contrib.bmunit.BMUnitConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(org.jboss.byteman.contrib.bmunit.BMUnitRunner.class)
@BMUnitConfig(loadDirectory = "target/test-classes"
public class JGitFileSystemImplProviderBytemanTest extends AbstractTestInfra {

	private static Logger logger = LoggerFactory.getLogger(JGitFileSystemImplProviderBytemanTest.class);

	@Before
	public void createGitFsProvider() throws IOException {
		provider = new JGitFileSystemProvider();
	}

	@Test
	@BMScript(value = "byteman/squash.btm")
	public void testConcurrentSquashWithThreeCommit() throws IOException
		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo

		final CyclicBarrier threadsFinishedBarrier = new CyclicBarrier(3);
		final RevCommit commit = commitThreeTimesAndGetReference(fs

		Thread t1 = new Thread(() -> {
			logger.info("<<<<<<<<<<<<< COMMIT TO SQUASH " + commit.getName() + " --- " + commit.getFullMessage());
			printLog(fs.getGit());
			VersionRecord record = makeVersionRecord("aparedes"
					commit.getName());
			SquashOption squashOption = new SquashOption(record);
			logger.info("COMMITTER-1: Squashing");
			try {
				provider.setAttribute(master
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			printLog(fs.getGit());
			waitFor(threadsFinishedBarrier);
		});

		Thread t2 = new Thread(() -> {
			logger.info("<<<<<<<<<<<<< COMMIT TO SQUASH " + commit.getName() + " --- " + commit.getFullMessage());
			printLog(fs.getGit());
			VersionRecord record = makeVersionRecord("aparedes"
					commit.getName());
			SquashOption squashOption = new SquashOption(record);
			logger.info("COMMITTER-2: Squashing");
			try {
				provider.setAttribute(master
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			printLog(fs.getGit());
			waitFor(threadsFinishedBarrier);
		});

		t1.setName("COMMITTER-1");
		t2.setName("COMMITTER-2");
		t2.start();
		t1.start();

		waitFor(threadsFinishedBarrier);

		assertEquals(2
	}

	@Test
	@BMScript(value = "byteman/squash.btm")
	public void testConcurrentSquashWithSixCommit() throws IOException
		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo

		final CyclicBarrier threadsFinishedBarrier = new CyclicBarrier(3);
		final RevCommit commit = commitSixTimesAndGetReference(fs

		Thread t1 = new Thread(() -> {
			logger.info("<<<<<<<<<<<<< COMMIT TO SQUASH " + commit.getName() + " --- " + commit.getFullMessage());
			printLog(fs.getGit());
			VersionRecord record = makeVersionRecord("aparedes"
					commit.getName());
			SquashOption squashOption = new SquashOption(record);
			logger.info("COMMITTER-1: Squashing");
			try {
				provider.setAttribute(master
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			printLog(fs.getGit());
			waitFor(threadsFinishedBarrier);
		});

		Thread t2 = new Thread(() -> {
			logger.info("<<<<<<<<<<<<< COMMIT TO SQUASH " + commit.getName() + " --- " + commit.getFullMessage());
			printLog(fs.getGit());
			VersionRecord record = makeVersionRecord("aparedes"
					commit.getName());
			SquashOption squashOption = new SquashOption(record);
			logger.info("COMMITTER-2: Squashing");
			try {
				provider.setAttribute(master
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			printLog(fs.getGit());
			waitFor(threadsFinishedBarrier);
		});

		t1.setName("COMMITTER-1");
		t2.setName("COMMITTER-2");
		t2.start();
		t1.start();

		waitFor(threadsFinishedBarrier);

		assertEquals(2
	}

	@Test
	@BMScript(value = "byteman/squash_exception.btm")
	public void testForceExceptionWhenTryingToSquash() throws IOException

		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo

		final RevCommit commit = commitThreeTimesAndGetReference(fs

		logger.info("<<<<<<<<<<<<< COMMIT TO SQUASH " + commit.getName() + " --- " + commit.getFullMessage());
		printLog(fs.getGit());
		VersionRecord record = makeVersionRecord("aparedes"
				commit.getName());
		SquashOption squashOption = new SquashOption(record);
		logger.info("COMMITTER-1: Squashing");

		try {
			provider.setAttribute(master
		} catch (Exception e) {
			fs.lock();
			fs.unlock();
		}

		assertEquals(3
	}

	@Test
	@BMScript(value = "byteman/commit_exception.btm")
	public void testFileSystemLockOnException() throws IOException

		final JGitFileSystemProxy fsProxy = (JGitFileSystemProxy) provider.newFileSystem(newRepo
		JGitFileSystem fs = fsProxy.getRealJGitFileSystem();


		try {
			writeFile(fs
		} catch (RuntimeException e) {
		}

		Object lock = null;
		try {
			Field field = JGitFileSystemImpl.class.getDeclaredField("lock");
			field.setAccessible(true);
			lock = field.get(fs);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		Object isLocked = null;
		try {
			Method method = lock.getClass().getMethod("hasBeenInUse");
			isLocked = method.invoke(lock);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertTrue(((Boolean) isLocked));
	}

	private VersionRecord makeVersionRecord(final String author
			final Date date
		return new VersionRecord() {
			@Override
			public String id() {
				return commit;
			}

			@Override
			public String author() {
				return author;
			}

			@Override
			public String email() {
				return email;
			}

			@Override
			public String comment() {
				return comment;
			}

			@Override
			public Date date() {
				return date;
			}

			@Override
			public String uri() {
				return null;
			}
		};
	}

	private static void printLog(final Git git) {
		try {
			for (final RevCommit revCommit : ((GitImpl) git)._log().call()) {
				logger.info("[LOG]: " + revCommit.getName() + " --- " + revCommit.getFullMessage());
			}
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	protected static void waitFor(CyclicBarrier barrier) {
		String threadName = Thread.currentThread().getName();
		try {
			logger.info(threadName + " request for await");
			barrier.await();
			logger.info(threadName + " await finished");
		} catch (InterruptedException e) {
			fail("Thread '" + threadName + "' was interrupted while waiting for the other threads!");
		} catch (BrokenBarrierException e) {
			fail("Thread '" + threadName + "' barrier was broken while waiting for the other threads!");
		}
	}

	private RevCommit commitThreeTimesAndGetReference(JGitFileSystem fs
		try {
			final Path path = provider
			final Path path2 = provider
			final Path path3 = provider

			final RevCommit commit = writeFile(fs
			writeFile(fs
			writeFile(fs

			return commit;
		} catch (IOException | GitAPIException e) {
			throw new RuntimeException(e);
		}
	}

	private RevCommit commitSixTimesAndGetReference(JGitFileSystem fs
		try {
			final Path path = provider
			final Path path2 = provider
			final Path path3 = provider
			final Path path4 = provider
			final Path path5 = provider
			final Path path6 = provider

			final RevCommit commit = writeFile(fs
			writeFile(fs
			writeFile(fs
			writeFile(fs
			writeFile(fs
			writeFile(fs

			return commit;
		} catch (IOException | GitAPIException e) {
			throw new RuntimeException(e);
		}
	}

	private RevCommit writeFile(final JGitFileSystem fs
			throws IOException
		final OutputStream stream = provider.newOutputStream(path);
		logger.info("Writing file: " + path.getFileName().toString());
		stream.write("my cool content".getBytes());
		stream.close();
		return this.getCommitsFromBranch((GitImpl) fs.getGit()
	}

	private List<RevCommit> getCommitsFromBranch(final GitImpl origin
			throws GitAPIException
		List<RevCommit> commits = new ArrayList<>();
		final ObjectId id = new GetRef(origin.getRepository()
		for (RevCommit commit : origin._log().add(id).call()) {
			commits.add(commit);
		}
		return commits;
	}
}
