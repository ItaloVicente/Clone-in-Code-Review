package org.eclipse.egit.core.synchronize;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.op.BranchOperation;
import org.eclipse.egit.core.op.ConnectProviderOperation;
import org.eclipse.egit.core.op.DisconnectProviderOperation;
import org.eclipse.egit.core.op.TrackOperation;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.egit.core.test.GitTestCase;
import org.eclipse.egit.core.test.TestProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.core.variants.IResourceVariant;
import org.eclipse.team.core.variants.ResourceVariantByteStore;
import org.eclipse.team.core.variants.SessionResourceVariantByteStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GitResourceVariantTreeTest extends GitTestCase {

	private Repository repo;

	private ResourceVariantByteStore store;

	@Before
	public void createGitRepository() throws Exception {
		IProject iProject = project.project;
		if (!gitDir.exists())
			new Repository(gitDir).create();

		new ConnectProviderOperation(iProject, gitDir).execute(null);
		repo = RepositoryMapping.getMapping(iProject).getRepository();

		store = new SessionResourceVariantByteStore();
	}

	@After
	public void clearGitResources() throws Exception {
		List<IProject> projects = new ArrayList<IProject>();
		projects.add(project.project);
		new DisconnectProviderOperation(projects).execute(null);

		repo.close();
	}

	@Test
	public void shouldReturnOneRoot() {
		GitSynchronizeData data = new GitSynchronizeData(repo, "", "", false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitTestResourceVariantTree(dataSet,
				store);

		assertEquals(1, grvt.roots().length);
		IResource actualProject = grvt.roots()[0];
		assertEquals(this.project.getProject(), actualProject);
	}

	@Test
	public void shouldReturnTwoRoots() throws Exception {
		TestProject secondProject = new TestProject(false, "Project-2");
		IProject secondIProject = secondProject.project;
		new ConnectProviderOperation(secondIProject, gitDir).execute(null);
		GitSynchronizeData data = new GitSynchronizeData(repo, "", "", false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitTestResourceVariantTree(dataSet,
				store);

		assertEquals(2, grvt.roots().length);
		IResource actualProject = grvt.roots()[1];
		assertEquals(this.project.project, actualProject);
		IResource actualProject1 = grvt.roots()[0];
		assertEquals(secondIProject, actualProject1);
	}

	@Test
	public void shouldReturnOneMember() throws Exception {
		createResourceAndCommit("org.egit.test", "Main.java", "class Main {}",
				"Initial commit");
		IPackageFragment iPackage = project
				.createPackage("org.egit.test.nested");
		project.createType(iPackage, "Main2.java", "class Main2 {}");
		GitSynchronizeData data = new GitSynchronizeData(repo, Constants.HEAD,
				Constants.MASTER, false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitRemoteResourceVariantTree(dataSet,
				store);

		assertEquals(1, grvt.members(project.project).length);
		IResource[] members = grvt.members(project.project);
		assertEquals("src", members[0].getName());
	}

	@Ignore
	@Test
	public void shouldReturnTwoMembers() throws Exception {
		IPackageFragment iPackage = project.createPackage("org.egit.test");
		createResourceAndCommit(iPackage, "Main.java", "class Main {}",
				"Initial commit");
		createResourceAndCommit("org.egit.test.nested", "Main2.java",
				"class Main2 {}", "Second commit");

		GitSynchronizeData data = new GitSynchronizeData(repo, Constants.HEAD,
				Constants.MASTER, false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitRemoteResourceVariantTree(dataSet,
				store);

		assertEquals(2, grvt.members(iPackage.getResource()).length);
		IResource[] members = grvt.members(iPackage.getResource());
		assertEquals("nested", members[0].getName());
		assertEquals("Main.java", members[1].getName());
	}

	@Test
	public void shouldReturnNullResourceVariant() throws Exception {
		GitSynchronizeData data = new GitSynchronizeData(repo, Constants.HEAD,
				Constants.MASTER, false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitRemoteResourceVariantTree(dataSet,
				store);

		assertNull(grvt.getResourceVariant(null));
	}

	@Test
	public void shouldReturnNullResourceVariant2() throws Exception {
		IPackageFragment iPackage = project.createPackage("org.egit.test");
		IType mainJava = project.createType(iPackage, "Main.java",
				"class Main {}");
		GitSynchronizeData data = new GitSynchronizeData(repo, Constants.HEAD,
				Constants.MASTER, false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitRemoteResourceVariantTree(dataSet,
				store);

		assertNull(grvt.getResourceVariant(mainJava.getResource()));
	}

	@Ignore
	@Test
	public void shoulReturnSameResourceVariant() throws Exception {
		IType mainJava = createResourceAndCommit("org.egit.test", "Main.java",
				"class Main {}", "Initial commit");
		GitSynchronizeData data = new GitSynchronizeData(repo, Constants.HEAD,
				Constants.MASTER, false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitRemoteResourceVariantTree(dataSet,
				store);

		IResourceVariant actual = grvt.getResourceVariant(mainJava
				.getResource());
		assertNotNull(actual);
		assertEquals("Main.java", actual.getName());

		InputStream actualIn = actual.getStorage(new NullProgressMonitor())
				.getContents();
		byte[] actualByte = new byte[actualIn.available()];
		actualIn.read(actualByte);
		InputStream expectedIn = ((IFile) mainJava.getResource()).getContents();
		byte[] expectedByte = new byte[expectedIn.available()];
		expectedIn.read(expectedByte);
		assertArrayEquals(expectedByte, actualByte);
	}

	@Ignore
	@Test
	public void shouldReturnDifferentResourceVariant() throws Exception {
		IType mainJava = createResourceAndCommit("org.egit.test", "Main.java",
				"class Main {}", "Initial commit");
		createBranch("test");
		new BranchOperation(repo, "refs/heads/test").execute(null);
		((IFile) mainJava.getResource()).appendContents(
				new ByteArrayInputStream("// test".getBytes()), 0, null);
		addAndCommitResource(mainJava, "Second commit");
		GitSynchronizeData data = new GitSynchronizeData(repo, Constants.HEAD,
				Constants.MASTER, false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitRemoteResourceVariantTree(dataSet,
				store);

		IResourceVariant actual = grvt.getResourceVariant(mainJava
				.getResource());
		assertNotNull(actual);
		assertEquals("Main.java", actual.getName());

		InputStream actualIn = actual.getStorage(new NullProgressMonitor())
				.getContents();
		byte[] actualByte = new byte[actualIn.available()];
		actualIn.read(actualByte);
		InputStream expectedIn = ((IFile) mainJava.getResource()).getContents();
		byte[] expectedByte = new byte[expectedIn.available()];
		expectedIn.read(expectedByte);

		if (Arrays.equals(expectedByte, actualByte)) {
			fail();
		} else {
			assertTrue(true);
		}
	}

	private IType createResourceAndCommit(String packageName, String fileName,
			String fileContent, String commitMsg) throws Exception {
		IPackageFragment iPackage = project.createPackage(packageName);
		return createResourceAndCommit(iPackage, fileName, fileContent,
				commitMsg);
	}

	private IType createResourceAndCommit(IPackageFragment iPackage,
			String fileName, String fileContent, String commitMsg)
			throws Exception {
		IType mainJava = project.createType(iPackage, fileName, fileContent);
		addAndCommitResource(mainJava, commitMsg);

		return mainJava;
	}

	private void addAndCommitResource(IType mainJava, String commitMsg)
			throws Exception {
		List<IResource> resources = new ArrayList<IResource>();
		resources.add(mainJava.getResource());
		IResource[] track = resources.toArray(new IResource[resources.size()]);
		new TrackOperation(track).execute(null); // add resource to git
		new Git(repo).commit().setMessage(commitMsg).call(); // make commit
	}

	private void createBranch(String branchName) throws Exception {
		RefUpdate updateRef;
		updateRef = repo.updateRef(Constants.R_HEADS + branchName);
		Ref startRef = repo.getRef(branchName);
		ObjectId startAt = repo.resolve(Constants.HEAD);
		String startBranch;
		if (startRef != null)
			startBranch = branchName;
		else
			startBranch = startAt.name();
		startBranch = repo.shortenRefName(startBranch);
		updateRef.setNewObjectId(startAt);
		updateRef
				.setRefLogMessage("branch: Created from " + startBranch, false); //$NON-NLS-1$
		updateRef.update();
	}

}
