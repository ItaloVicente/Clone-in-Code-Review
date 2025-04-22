package org.eclipse.egit.core.internal.merge;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.op.MergeOperation;
import org.junit.Test;

public class DirCacheResourceVariantTreeProviderTest extends VariantsTestCase {
	@Test
	public void testDirCacheAddToIndex() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1");
		IFile iFile1 = testRepo.getIFile(iProject, file1);

		testRepo.appendFileContent(file1, INITIAL_CONTENT_1);

		DirCacheResourceVariantTreeProvider treeProvider = new DirCacheResourceVariantTreeProvider(
				repo);
		assertTrue(treeProvider.getKnownResources().isEmpty());
		assertFalse(treeProvider.getBaseTree().hasResourceVariant(iFile1));
		assertFalse(treeProvider.getSourceTree().hasResourceVariant(iFile1));
		assertFalse(treeProvider.getRemoteTree().hasResourceVariant(iFile1));

		testRepo.addToIndex(iFile1);

		treeProvider = new DirCacheResourceVariantTreeProvider(repo);
		assertTrue(treeProvider.getKnownResources().isEmpty());
		assertFalse(treeProvider.getBaseTree().hasResourceVariant(iFile1));
		assertFalse(treeProvider.getSourceTree().hasResourceVariant(iFile1));
		assertFalse(treeProvider.getRemoteTree().hasResourceVariant(iFile1));
	}

	@Test
	public void testDirCacheTreesNoConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1");
		File file2 = testRepo.createFile(iProject, "file2");

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_1,
				"first file - initial commit");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_2,
				"second file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		IFile iFile2 = testRepo.getIFile(iProject, file2);

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		final String branchChanges = "branch changes\n";
		setContentsAndCommit(testRepo, iFile2, branchChanges
				+ INITIAL_CONTENT_2, "branch commit");

		testRepo.checkoutBranch(MASTER);

		final String masterChanges = "\nsome changes";
		setContentsAndCommit(testRepo, iFile1, INITIAL_CONTENT_1
				+ masterChanges, "master commit");
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		new MergeOperation(repo, BRANCH).execute(null);

		DirCacheResourceVariantTreeProvider treeProvider = new DirCacheResourceVariantTreeProvider(
				repo);
		assertTrue(treeProvider.getKnownResources().isEmpty());

		assertFalse(treeProvider.getBaseTree().hasResourceVariant(iFile1));
		assertFalse(treeProvider.getBaseTree().hasResourceVariant(iFile2));

		assertFalse(treeProvider.getSourceTree().hasResourceVariant(iFile1));
		assertFalse(treeProvider.getSourceTree().hasResourceVariant(iFile2));

		assertFalse(treeProvider.getRemoteTree().hasResourceVariant(iFile1));
		assertFalse(treeProvider.getRemoteTree().hasResourceVariant(iFile2));
	}

	@Test
	public void testDirCacheTreesConflictOnOne() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1");
		File file2 = testRepo.createFile(iProject, "file2");

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_1,
				"first file - initial commit");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_2,
				"second file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);
		IFile iFile2 = testRepo.getIFile(iProject, file2);

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		final String branchChanges = "branch changes\n";
		setContentsAndCommit(testRepo, iFile1, branchChanges
				+ INITIAL_CONTENT_1, "branch commit");
		setContentsAndCommit(testRepo, iFile2, branchChanges
				+ INITIAL_CONTENT_2, "branch commit");

		testRepo.checkoutBranch(MASTER);

		final String masterChanges = "\nsome changes";
		setContentsAndCommit(testRepo, iFile1, INITIAL_CONTENT_1
				+ masterChanges, "master commit");
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		new MergeOperation(repo, BRANCH).execute(null);

		DirCacheResourceVariantTreeProvider treeProvider = new DirCacheResourceVariantTreeProvider(
				repo);
		assertTrue(treeProvider.getKnownResources().contains(iFile1));
		assertFalse(treeProvider.getKnownResources().contains(iFile2));

		assertTrue(treeProvider.getBaseTree().hasResourceVariant(iFile1));
		assertFalse(treeProvider.getBaseTree().hasResourceVariant(iFile2));

		assertTrue(treeProvider.getSourceTree().hasResourceVariant(iFile1));
		assertFalse(treeProvider.getSourceTree().hasResourceVariant(iFile2));

		assertTrue(treeProvider.getRemoteTree().hasResourceVariant(iFile1));
		assertFalse(treeProvider.getRemoteTree().hasResourceVariant(iFile2));
	}

	@Test
	public void testDirCacheTreesConflict() throws Exception {
		File file1 = testRepo.createFile(iProject, "file1");
		File file2 = testRepo.createFile(iProject, "file2");

		testRepo.appendContentAndCommit(iProject, file1, INITIAL_CONTENT_1,
				"first file - initial commit");

		IFile iFile1 = testRepo.getIFile(iProject, file1);

		testRepo.createAndCheckoutBranch(MASTER, BRANCH);

		final String branchChanges = "branch changes\n";
		setContentsAndCommit(testRepo, iFile1, branchChanges
				+ INITIAL_CONTENT_1, "branch commit");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_2
				+ "branch", "second file - initial commit - branch");

		testRepo.checkoutBranch(MASTER);

		final String masterChanges = "some changes\n";
		setContentsAndCommit(testRepo, iFile1, INITIAL_CONTENT_1
				+ masterChanges, "master commit - file1");
		testRepo.appendContentAndCommit(iProject, file2, INITIAL_CONTENT_2
				+ "master", "second file - initial commit - master");
		IFile iFile2 = testRepo.getIFile(iProject, file2);
		iProject.refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());

		new MergeOperation(repo, BRANCH).execute(null);

		DirCacheResourceVariantTreeProvider treeProvider = new DirCacheResourceVariantTreeProvider(
				repo);
		assertTrue(treeProvider.getKnownResources().contains(iFile1));
		assertTrue(treeProvider.getKnownResources().contains(iFile2));

		assertTrue(treeProvider.getBaseTree().hasResourceVariant(iFile1));
		assertFalse(treeProvider.getBaseTree().hasResourceVariant(iFile2));

		assertTrue(treeProvider.getSourceTree().hasResourceVariant(iFile1));
		assertTrue(treeProvider.getSourceTree().hasResourceVariant(iFile2));

		assertTrue(treeProvider.getRemoteTree().hasResourceVariant(iFile1));
		assertTrue(treeProvider.getRemoteTree().hasResourceVariant(iFile2));
	}
}
