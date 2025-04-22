package org.eclipse.egit.core.test.op;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.core.op.RebaseOperation;
import org.eclipse.egit.core.test.GitTestCase;
import org.eclipse.egit.core.test.TestRepository;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RebaseResult;
import org.eclipse.jgit.api.RebaseCommand.Operation;
import org.eclipse.jgit.api.RebaseResult.Status;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class RebaseOperationTest extends GitTestCase {

	private static final String TOPIC = Constants.R_HEADS + "topic";

	private static final String MASTER = Constants.R_HEADS + "master";

	TestRepository testRepository;

	Repository repository;

	Git git;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		testRepository = new TestRepository(gitDir);
		repository = testRepository.getRepository();
		testRepository
				.createInitialCommit("testRebaseOperation\n\nfirst commit\n");
		git = new Git(repository);
	}

	@Test
	@Ignore
	public void testUpToDate() throws Exception {
		IFile file = project.createFile("theFile.txt", "Hello, world"
				.getBytes("UTF-8"));
		RevCommit first = testRepository.addAndCommit(project.project,
				new File(file.getLocationURI()), "Adding theFile.txt");

		testRepository.createBranch(MASTER, TOPIC);

		testRepository.checkoutBranch(TOPIC);

		file = project.createFile("theSecondFile.txt", "Hello, world"
				.getBytes("UTF-8"));
		RevCommit topicCommit = testRepository.addAndCommit(project.project,
				new File(file.getLocationURI()), "Adding theSecondFile.txt");

		assertEquals(first, topicCommit.getParent(0));

		RebaseOperation op = new RebaseOperation(
				testRepository.getRepository(), testRepository.getRepository()
						.getRef(MASTER));
		op.execute(null);

		RebaseResult res = op.getResult();
		assertEquals(RebaseResult.Status.UP_TO_DATE, res.getStatus());

		RevCommit newTopic = new RevWalk(repository).parseCommit(repository
				.resolve(TOPIC));
		assertEquals(topicCommit, newTopic);
		assertEquals(first, newTopic.getParent(0));
	}

	@Test
	public void testNoConflict() throws Exception {
		IFile file = project.createFile("theFile.txt", "Hello, world"
				.getBytes("UTF-8"));
		RevCommit first = testRepository.addAndCommit(project.project,
				new File(file.getLocationURI()), "Adding theFile.txt");

		testRepository.createBranch(MASTER, TOPIC);

		file.setContents(new ByteArrayInputStream("master".getBytes("UTF-8")),
				0, null);
		RevCommit second = git.commit().setAll(true).setMessage(
				"Modify theFile.txt").call();
		assertEquals(first, second.getParent(0));

		testRepository.checkoutBranch(TOPIC);

		file = project.createFile("theSecondFile.txt", "Hello, world"
				.getBytes("UTF-8"));
		RevCommit topicCommit = testRepository.addAndCommit(project.project,
				new File(file.getLocationURI()), "Adding theSecondFile.txt");

		assertEquals(first, topicCommit.getParent(0));

		RebaseOperation op = new RebaseOperation(
				testRepository.getRepository(), testRepository.getRepository()
						.getRef(MASTER));
		op.execute(null);

		RebaseResult res = op.getResult();
		assertEquals(RebaseResult.Status.OK, res.getStatus());

		RevCommit newTopic = new RevWalk(repository).parseCommit(repository
				.resolve(TOPIC));
		assertEquals(second, newTopic.getParent(0));
	}

	@Test
	public void testStopAndAbortOnConflict() throws Exception {
		IFile file = project.createFile("theFile.txt", "Hello, world"
				.getBytes("UTF-8"));
		RevCommit first = testRepository.addAndCommit(project.project,
				new File(file.getLocationURI()), "Adding theFile.txt");

		testRepository.createBranch(MASTER, TOPIC);

		file.setContents(new ByteArrayInputStream("master".getBytes("UTF-8")),
				0, null);
		RevCommit second = git.commit().setAll(true).setMessage(
				"Modify theFile.txt").call();
		assertEquals(first, second.getParent(0));

		testRepository.checkoutBranch(TOPIC);

		file.setContents(new ByteArrayInputStream("topic".getBytes("UTF-8")),
				0, null);
		RevCommit topicCommit = testRepository.addAndCommit(project.project,
				new File(file.getLocationURI()), "Changing theFile.txt again");

		assertEquals(first, topicCommit.getParent(0));

		RebaseOperation op = new RebaseOperation(
				testRepository.getRepository(), testRepository.getRepository()
						.getRef(MASTER));
		op.execute(null);

		RebaseResult res = op.getResult();
		assertEquals(RebaseResult.Status.STOPPED, res.getStatus());

		RebaseOperation abort = new RebaseOperation(repository, Operation.ABORT);
		abort.execute(null);
		RebaseResult abortResult = abort.getResult();
		assertEquals(Status.ABORTED, abortResult.getStatus());

		assertEquals(topicCommit, repository.resolve(Constants.HEAD));
	}

	@Test
	public void testExceptionWhenRestartingStoppedRebase() throws Exception {
		IFile file = project.createFile("theFile.txt", "Hello, world"
				.getBytes("UTF-8"));
		RevCommit first = testRepository.addAndCommit(project.project,
				new File(file.getLocationURI()), "Adding theFile.txt");

		testRepository.createBranch(MASTER, TOPIC);

		file.setContents(new ByteArrayInputStream("master".getBytes("UTF-8")),
				0, null);
		RevCommit second = git.commit().setAll(true).setMessage(
				"Modify theFile.txt").call();
		assertEquals(first, second.getParent(0));

		testRepository.checkoutBranch(TOPIC);

		file.setContents(new ByteArrayInputStream("topic".getBytes("UTF-8")),
				0, null);
		RevCommit topicCommit = testRepository.addAndCommit(project.project,
				new File(file.getLocationURI()), "Changing theFile.txt again");

		assertEquals(first, topicCommit.getParent(0));

		RebaseOperation op = new RebaseOperation(repository, repository
				.getRef(MASTER));
		op.execute(null);

		RebaseResult res = op.getResult();
		assertEquals(RebaseResult.Status.STOPPED, res.getStatus());

		try {
			op = new RebaseOperation(repository, repository.getRef(MASTER));
			op.execute(null);
			fail("Expected Exception not thrown");
		} catch (CoreException e) {
			Throwable cause = e.getCause();
			assertTrue(cause instanceof WrongRepositoryStateException);
		}
	}
}
