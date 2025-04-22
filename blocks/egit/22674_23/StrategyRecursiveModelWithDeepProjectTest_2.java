package org.eclipse.egit.core.internal.merge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.core.test.TestRepository;
import org.eclipse.egit.core.test.models.ModelTestCase;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.IndexDiff.StageState;
import org.eclipse.jgit.lib.Repository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StrategyRecursiveModelTest extends ModelTestCase {
	protected static final String MASTER = Constants.R_HEADS + Constants.MASTER;

	protected static final String BRANCH = Constants.R_HEADS + "branch";

	protected static final String INITIAL_CONTENT_FILE1 = "some content for the first file";

	protected static final String INITIAL_CONTENT_FILE2 = "some content for the second file";

	protected static final String SYSTEM_EOL = System
			.getProperty("line.separator");

	protected static final String BRANCH_CHANGE = "branch changes" + SYSTEM_EOL;

	protected static final String MASTER_CHANGE = "master changes" + SYSTEM_EOL;


	protected Repository repo;

	protected IProject iProject;

	protected TestRepository testRepo;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		iProject = project.project;
		testRepo = new TestRepository(gitDir);
		testRepo.connect(iProject);
		repo = RepositoryMapping.getMapping(iProject).getRepository();

		try (Git git = new Git(repo)) {
			git.commit().setAuthor("JUnit", "junit@jgit.org")
				.setMessage("Initial commit").call();
		}
	}

	@After
	public void clearGitResources() throws Exception {
		testRepo.disconnect(iProject);
		testRepo.dispose();
		repo = null;

		super.tearDown();
	}

	@Test
	public void mergeModelWithDeletedRemoteNoConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_FILE1,
				"first file - initial commit");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2,
				"second file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		IFile iFile2 = testRepo.getIFile(iProject, file2);

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		setContentsAndCommit(testRepo, iFile1, BRANCH_CHANGE
				+ INITIAL_CONTENT_FILE1, "branch commit");
		iFile2.delete(true, new NullProgressMonitor());
		testRepo.addAndCommit(iProject, file2, "branch commit - deleted file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.checkoutBranch(MASTER);

		setContentsAndCommit(testRepo, iFile1, INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE, "master commit");
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		merge(repo, BRANCH);

		final Status status = status(repo);
		assertFalse(status.hasUncommittedChanges());
		assertTrue(status.getConflicting().isEmpty());

		assertContentEquals(iFile1, BRANCH_CHANGE + INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE);
		assertFalse(iFile2.exists());
	}

	@Test
	public void mergeModelWithDeletedLocalNoConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_FILE1,
				"first file - initial commit");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2,
				"second file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		IFile iFile2 = testRepo.getIFile(iProject, file2);

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		setContentsAndCommit(testRepo, iFile1, BRANCH_CHANGE
				+ INITIAL_CONTENT_FILE1, "branch commit");

		testRepo.checkoutBranch(MASTER);

		setContentsAndCommit(testRepo, iFile1, INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE, "master commit");
		iFile2.delete(true, new NullProgressMonitor());
		testRepo.addAndCommit(iProject, file2, "master commit - deleted file2."
				+ SAMPLE_FILE_EXTENSION);
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		merge(repo, BRANCH);

		final Status status = status(repo);
		assertFalse(status.hasUncommittedChanges());
		assertTrue(status.getConflicting().isEmpty());

		assertContentEquals(iFile1, BRANCH_CHANGE + INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE);
		assertFalse(iFile2.exists());
	}

	@Test
	public void mergeModelWithPseudoConflictDeletion() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_FILE1,
				"first file - initial commit");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2,
				"second file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		IFile iFile2 = testRepo.getIFile(iProject, file2);

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		setContentsAndCommit(testRepo, iFile1, BRANCH_CHANGE
				+ INITIAL_CONTENT_FILE1, "branch commit");
		iFile2.delete(true, new NullProgressMonitor());
		testRepo.addAndCommit(iProject, file2, "branch commit - deleted file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.checkoutBranch(MASTER);

		setContentsAndCommit(testRepo, iFile1, INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE, "master commit");
		iFile2.delete(true, new NullProgressMonitor());
		testRepo.addAndCommit(iProject, file2, "master commit - deleted file2."
				+ SAMPLE_FILE_EXTENSION);
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		merge(repo, BRANCH);

		final Status status = status(repo);
		assertFalse(status.hasUncommittedChanges());
		assertTrue(status.getConflicting().isEmpty());

		assertContentEquals(iFile1, BRANCH_CHANGE + INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE);
		assertFalse(iFile2.exists());
	}

	@Test
	public void mergeModelWithDeletedRemoteModelConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_FILE1,
				"first file - initial commit");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2,
				"second file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		String repoRelativePath1 = testRepo.getRepoRelativePath(iFile1
				.getLocation().toPortableString());
		String repoRelativePath2 = testRepo.getRepoRelativePath(iFile2
				.getLocation().toPortableString());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		setContentsAndCommit(testRepo, iFile1, BRANCH_CHANGE
				+ INITIAL_CONTENT_FILE1, "branch commit");
		iFile2.delete(true, new NullProgressMonitor());
		testRepo.addAndCommit(iProject, file2, "branch commit - deleted file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.checkoutBranch(MASTER);

		setContentsAndCommit(testRepo, iFile1, MASTER_CHANGE
				+ INITIAL_CONTENT_FILE1, "master commit");
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		merge(repo, BRANCH);

		final Status status = status(repo);
		assertTrue(status.hasUncommittedChanges());
		assertFalse(status.getConflicting().isEmpty());
		assertTrue(status.getConflicting().contains(repoRelativePath1));
		assertTrue(status.getRemoved().contains(repoRelativePath2));

		assertContentEquals(iFile1, MASTER_CHANGE + INITIAL_CONTENT_FILE1);
		assertFalse(iFile2.exists());

		Map<String, StageState> map = status.getConflictingStageState();
		assertEquals(StageState.BOTH_MODIFIED, map.get(repoRelativePath1));
	}

	@Test
	public void mergeModelWithDeletedLocalModelConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_FILE1,
				"first file - initial commit");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2,
				"second file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		String repoRelativePath1 = testRepo.getRepoRelativePath(iFile1
				.getLocation().toPortableString());
		String repoRelativePath2 = testRepo.getRepoRelativePath(iFile2
				.getLocation().toPortableString());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		setContentsAndCommit(testRepo, iFile1, BRANCH_CHANGE
				+ INITIAL_CONTENT_FILE1, "branch commit");

		testRepo.checkoutBranch(MASTER);

		setContentsAndCommit(testRepo, iFile1, MASTER_CHANGE
				+ INITIAL_CONTENT_FILE1, "master commit");
		iFile2.delete(true, new NullProgressMonitor());
		testRepo.addAndCommit(iProject, file2, "master commit - deleted file2."
				+ SAMPLE_FILE_EXTENSION);
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		merge(repo, BRANCH);

		final Status status = status(repo);
		assertTrue(status.hasUncommittedChanges());
		assertFalse(status.getConflicting().isEmpty());
		assertTrue(status.getConflicting().contains(repoRelativePath1));
		assertFalse(status.getConflicting().contains(repoRelativePath2));

		assertContentEquals(iFile1, MASTER_CHANGE + INITIAL_CONTENT_FILE1);
		assertFalse(iFile2.exists());

		Map<String, StageState> map = status.getConflictingStageState();
		assertEquals(StageState.BOTH_MODIFIED, map.get(repoRelativePath1));
	}

	@Test
	public void mergeModelWithDeletedRemoteFileConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_FILE1,
				"first file - initial commit");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2,
				"second file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		String repoRelativePath1 = testRepo.getRepoRelativePath(iFile1
				.getLocation().toPortableString());
		String repoRelativePath2 = testRepo.getRepoRelativePath(iFile2
				.getLocation().toPortableString());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		setContentsAndCommit(testRepo, iFile1, BRANCH_CHANGE
				+ INITIAL_CONTENT_FILE1, "branch commit");
		iFile2.delete(true, new NullProgressMonitor());
		testRepo.addAndCommit(iProject, file2, "branch commit - deleted file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.checkoutBranch(MASTER);

		setContentsAndCommit(testRepo, iFile1, INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE, "master commit - file1");
		setContentsAndCommit(testRepo, iFile2, INITIAL_CONTENT_FILE2
				+ MASTER_CHANGE, "master commit - file2");
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		merge(repo, BRANCH);

		final Status status = status(repo);
		assertTrue(status.hasUncommittedChanges());
		assertTrue(status.getChanged().contains(repoRelativePath1));
		assertTrue(status.getConflicting().contains(repoRelativePath2));

		assertContentEquals(iFile1, BRANCH_CHANGE + INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE);
		assertContentEquals(iFile2, INITIAL_CONTENT_FILE2 + MASTER_CHANGE);

		Map<String, StageState> map = status.getConflictingStageState();
		assertEquals(StageState.DELETED_BY_THEM, map.get(repoRelativePath2));
	}

	@Test
	public void mergeModelWithDeletedLocalFileConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_FILE1,
				"first file - initial commit");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2,
				"second file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		String repoRelativePath1 = testRepo.getRepoRelativePath(iFile1
				.getLocation().toPortableString());
		String repoRelativePath2 = testRepo.getRepoRelativePath(iFile2
				.getLocation().toPortableString());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		setContentsAndCommit(testRepo, iFile1, BRANCH_CHANGE
				+ INITIAL_CONTENT_FILE1, "branch commit");
		setContentsAndCommit(testRepo, iFile2, BRANCH_CHANGE
				+ INITIAL_CONTENT_FILE2, "branch commit");

		testRepo.checkoutBranch(MASTER);

		setContentsAndCommit(testRepo, iFile1, INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE, "master commit");
		iFile2.delete(true, new NullProgressMonitor());
		testRepo.addAndCommit(iProject, file2, "master commit - deleted file2."
				+ SAMPLE_FILE_EXTENSION);

		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		merge(repo, BRANCH);

		final Status status = status(repo);
		assertTrue(status.hasUncommittedChanges());
		assertFalse(status.getConflicting().contains(repoRelativePath1));
		assertTrue(status.getConflicting().contains(repoRelativePath2));

		assertContentEquals(iFile1, BRANCH_CHANGE + INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE);
		assertContentEquals(iFile2, BRANCH_CHANGE + INITIAL_CONTENT_FILE2);

		Map<String, StageState> map = status.getConflictingStageState();
		assertEquals(StageState.DELETED_BY_US, map.get(repoRelativePath2));
	}

	@Test
	public void mergeModelWithAddedRemoteNoConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);
		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_FILE1,
				"first file - initial commit");
		IFile iFile1 = testRepo.getIFile(iProject, file1);

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		setContentsAndCommit(testRepo, iFile1, BRANCH_CHANGE
				+ INITIAL_CONTENT_FILE1, "branch commit");
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2,
				"second file - initial commit on branch");
		IFile iFile2 = testRepo.getIFile(iProject, file2);

		testRepo.checkoutBranch(MASTER);

		setContentsAndCommit(testRepo, iFile1, INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE, "master commit");
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		merge(repo, BRANCH);

		final Status status = status(repo);
		assertFalse(status.hasUncommittedChanges());
		assertTrue(status.getConflicting().isEmpty());

		assertContentEquals(iFile1, BRANCH_CHANGE + INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE);
		assertContentEquals(iFile2, INITIAL_CONTENT_FILE2);
	}

	@Test
	public void mergeModelWithAddedLocalNoConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_FILE1,
				"first file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		setContentsAndCommit(testRepo, iFile1, BRANCH_CHANGE
				+ INITIAL_CONTENT_FILE1, "branch commit");

		testRepo.checkoutBranch(MASTER);

		setContentsAndCommit(testRepo, iFile1, INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE, "master commit");
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2,
				"second file - initial commit on master");
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		merge(repo, BRANCH);

		final Status status = status(repo);
		assertFalse(status.hasUncommittedChanges());
		assertTrue(status.getConflicting().isEmpty());

		assertContentEquals(iFile1, BRANCH_CHANGE + INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE);
		assertContentEquals(iFile2, INITIAL_CONTENT_FILE2);
	}

	@Test
	public void mergeModelWithPseudoConflictAddition() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_FILE1,
				"first file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		setContentsAndCommit(testRepo, iFile1, BRANCH_CHANGE
				+ INITIAL_CONTENT_FILE1, "branch commit");
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2,
				"second file - initial commit on branch");

		testRepo.checkoutBranch(MASTER);

		setContentsAndCommit(testRepo, iFile1, INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE, "master commit");
		file2 = testRepo.createFile(iProject, "file2." + SAMPLE_FILE_EXTENSION);
		iFile2 = testRepo.getIFile(iProject, file2);
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2,
				"second file - initial commit on master");
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());
		String repoRelativePath2 = testRepo.getRepoRelativePath(iFile2
				.getLocation().toPortableString());

		merge(repo, BRANCH);

		final Status status = status(repo);
		assertTrue(status.hasUncommittedChanges());
		assertTrue(status.getConflicting().contains(repoRelativePath2));

		assertContentEquals(iFile1, BRANCH_CHANGE + INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE);
		assertContentEquals(iFile2, INITIAL_CONTENT_FILE2);

		Map<String, StageState> map = status.getConflictingStageState();
		assertEquals(StageState.BOTH_ADDED, map.get(repoRelativePath2));
	}

	@Test
	public void mergeModelWithAddedRemoteModelConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_FILE1,
				"first file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		String repoRelativePath1 = testRepo.getRepoRelativePath(iFile1
				.getLocation().toPortableString());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		setContentsAndCommit(testRepo, iFile1, BRANCH_CHANGE
				+ INITIAL_CONTENT_FILE1, "branch commit");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2,
				"second file - initial commit - branch");
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		String repoRelativePath2 = testRepo.getRepoRelativePath(iFile2
				.getLocation().toPortableString());

		testRepo.checkoutBranch(MASTER);

		setContentsAndCommit(testRepo, iFile1, MASTER_CHANGE
				+ INITIAL_CONTENT_FILE1, "master commit");
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		merge(repo, BRANCH);

		final Status status = status(repo);
		assertTrue(status.hasUncommittedChanges());
		assertFalse(status.getConflicting().isEmpty());
		assertTrue(status.getConflicting().contains(repoRelativePath1));
		assertTrue(status.getAdded().contains(repoRelativePath2));

		assertContentEquals(iFile1, MASTER_CHANGE + INITIAL_CONTENT_FILE1);
		assertContentEquals(iFile2, INITIAL_CONTENT_FILE2);

		Map<String, StageState> map = status.getConflictingStageState();
		assertEquals(StageState.BOTH_MODIFIED, map.get(repoRelativePath1));
	}

	@Test
	public void mergeModelWithAddedLocalModelConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_FILE1,
				"first file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		String repoRelativePath1 = testRepo.getRepoRelativePath(iFile1
				.getLocation().toPortableString());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		setContentsAndCommit(testRepo, iFile1, BRANCH_CHANGE
				+ INITIAL_CONTENT_FILE1, "branch commit");

		testRepo.checkoutBranch(MASTER);

		setContentsAndCommit(testRepo, iFile1, MASTER_CHANGE
				+ INITIAL_CONTENT_FILE1, "master commit");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2,
				"second file - initial commit");
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		String repoRelativePath2 = testRepo.getRepoRelativePath(iFile2
				.getLocation().toPortableString());
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		merge(repo, BRANCH);

		final Status status = status(repo);
		assertTrue(status.hasUncommittedChanges());
		assertFalse(status.getConflicting().isEmpty());
		assertTrue(status.getConflicting().contains(repoRelativePath1));
		assertTrue(status.getConflicting().contains(repoRelativePath2));

		assertContentEquals(iFile1, MASTER_CHANGE + INITIAL_CONTENT_FILE1);
		assertContentEquals(iFile2, INITIAL_CONTENT_FILE2);

		Map<String, StageState> map = status.getConflictingStageState();
		assertEquals(StageState.BOTH_MODIFIED, map.get(repoRelativePath1));
		assertEquals(StageState.ADDED_BY_US, map.get(repoRelativePath2));
	}

	@Test
	public void mergeModelWithConflictAddition() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1."
				+ SAMPLE_FILE_EXTENSION);
		File file2 = testRepo.createFile(iProject, "file2."
				+ SAMPLE_FILE_EXTENSION);

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_FILE1,
				"first file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		String repoRelativePath1 = testRepo.getRepoRelativePath(iFile1
				.getLocation().toPortableString());

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		setContentsAndCommit(testRepo, iFile1, BRANCH_CHANGE
				+ INITIAL_CONTENT_FILE1, "branch commit");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2
				+ "branch", "second file - initial commit - branch");

		testRepo.checkoutBranch(MASTER);

		setContentsAndCommit(testRepo, iFile1, INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE, "master commit - file1");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_FILE2
				+ "master", "second file - initial commit - master");
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		String repoRelativePath2 = testRepo.getRepoRelativePath(iFile2
				.getLocation().toPortableString());
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		merge(repo, BRANCH);

		final Status status = status(repo);
		assertTrue(status.hasUncommittedChanges());
		assertTrue(status.getChanged().contains(repoRelativePath1));
		assertTrue(status.getConflicting().contains(repoRelativePath2));

		assertContentEquals(iFile1, BRANCH_CHANGE + INITIAL_CONTENT_FILE1
				+ MASTER_CHANGE);
		assertContentEquals(iFile2, INITIAL_CONTENT_FILE2 + "master");

		Map<String, StageState> map = status.getConflictingStageState();
		assertEquals(StageState.BOTH_ADDED, map.get(repoRelativePath2));
	}
}
