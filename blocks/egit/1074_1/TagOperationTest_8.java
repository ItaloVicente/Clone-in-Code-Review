package org.eclipse.egit.core.test.op;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.op.BranchOperation;
import org.eclipse.egit.core.op.CloneOperation;
import org.eclipse.egit.core.op.CommitOperation;
import org.eclipse.egit.core.op.PushOperation;
import org.eclipse.egit.core.op.PushOperationResult;
import org.eclipse.egit.core.op.PushOperationSpecification;
import org.eclipse.egit.core.op.TrackOperation;
import org.eclipse.egit.core.test.DualRepositoryTestCase;
import org.eclipse.egit.core.test.TestRepository;
import org.eclipse.egit.core.test.TestUtils;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.RemoteRefUpdate.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PushOperationTest extends DualRepositoryTestCase {

	File workdir;

	File workdir2;

	String projectName = "PushTest";

	@Before
	public void setUp() throws Exception {

		workdir = testUtils.getTempDir("Repository1");
		workdir2 = testUtils.getTempDir("Repository2");

		repository1 = new TestRepository(new File(workdir, Constants.DOT_GIT));

		IProject project = testUtils.createProjectInLocalFileSystem(workdir,
				projectName);
		testUtils.addFileToProject(project, "folder1/file1.txt", "Hello world");

		repository1.connect(project);

		project.accept(new IResourceVisitor() {

			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IFile) {
					try {
						repository1
								.track(EFS.getStore(resource.getLocationURI())
										.toLocalFile(0, null));
					} catch (IOException e) {
						throw new CoreException(Activator.error(e.getMessage(),
								e));
					}
				}
				return true;
			}
		});

		repository1.commit("Initial commit");

		project.delete(false, false, null);

		URIish uri = new URIish("file:///"
				+ repository1.getRepository().getDirectory().toString());
		CloneOperation clop = new CloneOperation(uri, true, null, workdir2,
				"refs/heads/master", "origin");
		clop.run(null);

		repository2 = new TestRepository(new Repository(new File(workdir2,
				Constants.DOT_GIT)));
		RefUpdate createBranch = repository2.getRepository().updateRef(
				"refs/heads/test");
		createBranch.setNewObjectId(repository2.getRepository().resolve(
				"refs/heads/master"));
		createBranch.update();
	}

	@After
	public void tearDown() throws Exception {
		repository1.dispose();
		repository2.dispose();
		repository1 = null;
		repository2 = null;
		testUtils.deleteRecursive(workdir);
		testUtils.deleteRecursive(workdir2);
	}

	@Test
	public void testPush() throws Exception {

		PushOperation pop = createPushOperation();
		pop.run(new NullProgressMonitor());
		assertEquals(Status.UP_TO_DATE, getStatus(pop.getOperationResult()));

		IProject proj = importProject(repository1, projectName);
		ArrayList<IFile> files = new ArrayList<IFile>();
		IFile newFile = testUtils.addFileToProject(proj, "folder2/file2.txt",
				"New file");
		files.add(newFile);
		IFile[] fileArr = files.toArray(new IFile[files.size()]);

		TrackOperation trop = new TrackOperation(fileArr);
		trop.execute(null);
		CommitOperation cop = new CommitOperation(fileArr, files, files,
				TestUtils.AUTHOR, TestUtils.COMMITTER, "Added file");
		cop.execute(null);

		proj.delete(false, false, null);

		pop = createPushOperation();
		pop.run(null);
		assertEquals(Status.OK, getStatus(pop.getOperationResult()));

		try {
			pop.run(null);
			fail("Expected Exception not thrown");
		} catch (IllegalStateException e) {
		}

		pop = createPushOperation();
		pop.run(null);
		assertEquals(Status.UP_TO_DATE, getStatus(pop.getOperationResult()));

		String newFilePath = newFile.getFullPath().toOSString();

		File testFile = new File(workdir2, newFilePath);
		assertFalse(testFile.exists());
		testFile = new File(workdir, newFilePath);
		assertTrue(testFile.exists());

		BranchOperation bop = new BranchOperation(repository2.getRepository(),
				"refs/heads/test");
		bop.execute(null);
		testFile = new File(workdir2, newFilePath);
		assertTrue(testFile.exists());
	}

	@Test
	public void testIllegalStateExceptionOnGetResultWithoutRun()
			throws Exception {
		PushOperation pop = createPushOperation();
		try {
			pop.getOperationResult();
			fail("Expected Exception not thrown");
		} catch (IllegalStateException e) {
		}
	}

	@Test
	public void testPushWithReusedSpec() throws Exception {

		PushOperationSpecification spec = new PushOperationSpecification();
		URIish remote = new URIish("file:///"
				+ repository2.getRepository().getDirectory().toString());
		List<RemoteRefUpdate> refUpdates = new ArrayList<RemoteRefUpdate>();
		RemoteRefUpdate update = new RemoteRefUpdate(repository1
				.getRepository(), "HEAD", "refs/heads/test", false, null, null);
		refUpdates.add(update);
		spec.addURIRefUpdates(remote, refUpdates);

		PushOperation pop = new PushOperation(repository1.getRepository(),
				spec, false, null);
		pop.run(null);

		pop = new PushOperation(repository1.getRepository(), spec, false, null);
		try {
			pop.run(null);
			fail("Expected Exception not thrown");
		} catch (IllegalStateException e) {
		}
	}

	private Status getStatus(PushOperationResult operationResult) {
		URIish uri = operationResult.getURIs().iterator().next();
		return operationResult.getPushResult(uri).getRemoteUpdates().iterator()
				.next().getStatus();
	}

	private PushOperation createPushOperation() throws Exception {
		PushOperationSpecification spec = new PushOperationSpecification();
		URIish remote = new URIish("file:///"
				+ repository2.getRepository().getDirectory().toString());
		List<RemoteRefUpdate> refUpdates = new ArrayList<RemoteRefUpdate>();
		RemoteRefUpdate update = new RemoteRefUpdate(repository1
				.getRepository(), "HEAD", "refs/heads/test", false, null, null);
		refUpdates.add(update);
		spec.addURIRefUpdates(remote, refUpdates);
		PushOperation pop = new PushOperation(repository1.getRepository(),
				spec, false, null);
		return pop;
	}

}
