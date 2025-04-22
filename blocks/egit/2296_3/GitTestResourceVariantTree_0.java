package org.eclipse.egit.core.synchronize;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.MASTER;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.op.ConnectProviderOperation;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.egit.core.test.GitTestCase;
import org.eclipse.egit.core.test.TestProject;
import org.eclipse.egit.core.test.TestRepository;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.core.variants.IResourceVariant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GitResourceVariantTreeTest extends GitTestCase {

	private Repository repo;

	private IProject iProject;

	private TestRepository testRepo;

	@Before
	public void createGitRepository() throws Exception {
		iProject = project.project;
		testRepo = new TestRepository(gitDir);
		testRepo.connect(iProject);
		repo = RepositoryMapping.getMapping(iProject).getRepository();
	}

	@After
	public void clearGitResources() throws Exception {
		testRepo.disconnect(iProject);
		testRepo.dispose();
		repo = null;
		super.tearDown();
	}

	@Test
	public void shouldReturnOneRoot() throws Exception {
		new Git(repo).commit().setAuthor("JUnit", "junit@egit.org")
				.setMessage("Initial commit").call();
		GitSynchronizeData data = new GitSynchronizeData(repo, HEAD, HEAD, false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitTestResourceVariantTree(dataSet,
				null);

		assertEquals(1, grvt.roots().length);
		IResource actualProject = grvt.roots()[0];
		assertEquals(this.project.getProject(), actualProject);
	}

	@Test
	public void shouldReturnTwoRoots() throws Exception {
		TestProject secondProject = new TestProject(true, "Project-2");
		IProject secondIProject = secondProject.project;
		new ConnectProviderOperation(secondIProject, gitDir).execute(null);
		new Git(repo).commit().setAuthor("JUnit", "junit@egit.org")
				.setMessage("Initial commit").call();
		GitSynchronizeData data = new GitSynchronizeData(repo, HEAD, HEAD, false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitTestResourceVariantTree(dataSet,
				null);

		IResource[] roots = grvt.roots();
		Arrays.sort(roots, new Comparator<IResource>() {
			public int compare(IResource r1, IResource r2) {
				String path1 = r1.getFullPath().toString();
				String path2 = r2.getFullPath().toString();
				return path1.compareTo(path2);
			}
		});
		assertEquals(2, roots.length);
		IResource actualProject = roots[0];
		assertEquals(this.project.project, actualProject);
		IResource actualProject1 = roots[1];
		assertEquals(secondIProject, actualProject1);
	}

	@Test
	public void shouldReturnNullResourceVariant() throws Exception {
		new Git(repo).commit().setAuthor("JUnit", "junit@egit.org")
				.setMessage("Initial commit").call();
		GitSynchronizeData data = new GitSynchronizeData(repo, HEAD, MASTER,
				false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitRemoteResourceVariantTree(dataSet);

		assertNull(grvt.getResourceVariant(null));
	}

	@Test
	public void shouldReturnNullResourceVariant2() throws Exception {
		IPackageFragment iPackage = project.createPackage("org.egit.test");
		IType mainJava = project.createType(iPackage, "Main.java",
				"class Main {}");
		new Git(repo).commit().setAuthor("JUnit", "junit@egit.org")
				.setMessage("Initial commit").call();
		GitSynchronizeData data = new GitSynchronizeData(repo, HEAD, MASTER,
				false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitRemoteResourceVariantTree(dataSet);

		assertNull(grvt.getResourceVariant(mainJava.getResource()));
	}

	@Test
	public void shoulReturnSameResourceVariant() throws Exception {
		String fileName = "Main.java";
		File file = testRepo.createFile(iProject, fileName);
		testRepo.appendContentAndCommit(iProject, file, "class Main {}",
				"initial commit");
		IFile mainJava = testRepo.getIFile(iProject, file);
		GitSynchronizeData data = new GitSynchronizeData(repo, HEAD, MASTER,
				false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitRemoteResourceVariantTree(dataSet);

		IResourceVariant actual = grvt.getResourceVariant(mainJava);
		assertNotNull(actual);
		assertEquals(fileName, actual.getName());

		InputStream actualIn = actual.getStorage(new NullProgressMonitor())
				.getContents();
		byte[] actualByte = getBytesAndCloseStream(actualIn);
		InputStream expectedIn = mainJava.getContents();
		byte[] expectedByte = getBytesAndCloseStream(expectedIn);
		assertArrayEquals(expectedByte, actualByte);
	}

	@Test
	public void shouldReturnDifferentResourceVariant() throws Exception {
		String fileName = "Main.java";
		File file = testRepo.createFile(iProject, fileName);
		testRepo.appendContentAndCommit(iProject, file, "class Main {}",
				"initial commit");
		IFile mainJava = testRepo.getIFile(iProject, file);

		testRepo.createAndCheckoutBranch(Constants.R_HEADS + Constants.MASTER,
				Constants.R_HEADS + "test");
		testRepo.appendContentAndCommit(iProject, file, "// test",
				"first commit");
		GitSynchronizeData data = new GitSynchronizeData(repo, HEAD, MASTER,
				false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitRemoteResourceVariantTree(dataSet);

		IResourceVariant actual = grvt.getResourceVariant(mainJava);
		assertNotNull(actual);
		assertEquals(fileName, actual.getName());

		InputStream actualIn = actual.getStorage(new NullProgressMonitor())
				.getContents();
		byte[] actualByte = getBytesAndCloseStream(actualIn);
		InputStream expectedIn = mainJava.getContents();
		byte[] expectedByte = getBytesAndCloseStream(expectedIn);

		if (Arrays.equals(expectedByte, actualByte))
			fail();
		else
			assertTrue(true);
	}

	private byte[] getBytesAndCloseStream(InputStream stream) throws Exception {
		try {
			byte[] actualByte = new byte[stream.available()];
			stream.read(actualByte);
			return actualByte;
		} finally {
			stream.close();
		}
	}
}
