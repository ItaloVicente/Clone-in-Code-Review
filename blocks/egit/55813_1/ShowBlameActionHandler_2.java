package org.eclipse.egit.ui.test.team.actions;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffCache;
import org.eclipse.egit.core.op.CommitOperation;
import org.eclipse.egit.core.op.ConnectProviderOperation;
import org.eclipse.egit.ui.common.LocalRepositoryTestCase;
import org.eclipse.egit.ui.internal.actions.ShowBlameActionHandler;
import org.eclipse.egit.ui.test.TestUtil;
import org.eclipse.jdt.core.IAccessRule;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShowBlameActionHandlerTest extends LocalRepositoryTestCase {

	private static final String JAVA_PROJECT_NAME = "javatestProject";

	private static final String SRC_FOLDER_NAME = "src";

	private static final String BIN_FOLDER_NAME = "bin";

	private static final String PACKAGE_NAME = "p";

	private static final String JAVA_CLASS_NAME = "A";

	private static final String JAVA_FILE_NAME = JAVA_CLASS_NAME + ".java";

	private static final int MAX_DELETE_RETRY = 5;

	private static final int DELETE_RETRY_DELAY = 1000; // ms

	private static boolean initialAutobuild;

	private IJavaProject javaProject = null;

	@BeforeClass
	public static void setupAutobuildOff() throws CoreException {
		initialAutobuild = setAutobuild(false);
	}

	@AfterClass
	public static void teardownAutobuildReset() throws CoreException {
		setAutobuild(initialAutobuild);
	}

	@Before
	public void setup() throws Exception {
		javaProject = createJavaProjectAndCommitToRepository();
	}

	@After
	public void teardown() throws CoreException {
		removeJavaProject();
	}

	@Test
	public void testShowAnnotationsFromProjectExplorer() throws Exception {
		IProject project = javaProject.getProject();
		IFile file = project.getFolder(SRC_FOLDER_NAME).getFolder(PACKAGE_NAME)
				.getFile(JAVA_FILE_NAME);
		assertBlameEnabled(file);
		IJavaElement element = JavaCore.create(file, javaProject);
		assertTrue("Expected an ICompilationUnit",
				element instanceof ICompilationUnit);
		assertBlameEnabled(element);
		IType type = javaProject.findType(PACKAGE_NAME, JAVA_CLASS_NAME);
		assertBlameEnabled(type);
	}

	@SuppressWarnings("boxing")
	private void assertBlameEnabled(Object selected) {
		assertNotNull("Nothing selected", selected);
		IStructuredSelection selection = mock(IStructuredSelection.class);
		when(selection.getFirstElement()).thenReturn(selected);
		when(selection.size()).thenReturn(1);
		ShowBlameActionHandler blame = new ShowBlameActionHandler();
		blame.setSelection(selection);
		assertTrue("Expected blame to be enabled", blame.isEnabled());
	}


	private static boolean setAutobuild(boolean value) throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription desc = workspace.getDescription();
		boolean isAutoBuilding = desc.isAutoBuilding();
		if (isAutoBuilding != value) {
			desc.setAutoBuilding(value);
			workspace.setDescription(desc);
		}
		return isAutoBuilding;
	}

	private IJavaProject createJavaProjectAndCommitToRepository()
			throws Exception {
		Repository myRepository = createLocalTestRepository(REPO1);
		File gitDir = myRepository.getDirectory();
		IJavaProject jProject = createJavaProject(myRepository,
				JAVA_PROJECT_NAME);
		IProject project = jProject.getProject();
		try {
			new ConnectProviderOperation(project, gitDir).execute(null);
		} catch (Exception e) {
			Activator.logError("Failed to connect project to repository", e);
		}
		assertConnected(project);
		IFolder folder = project.getFolder(SRC_FOLDER_NAME)
				.getFolder(PACKAGE_NAME);
		IFile file = folder.getFile(JAVA_FILE_NAME);

		IFile[] commitables = new IFile[] { file };
		ArrayList<IFile> untracked = new ArrayList<IFile>();
		untracked.addAll(Arrays.asList(commitables));
		CommitOperation op = new CommitOperation(commitables, untracked,
				TestUtil.TESTAUTHOR, TestUtil.TESTCOMMITTER, "Initial commit");
		op.execute(null);

		IndexDiffCache cache = Activator.getDefault().getIndexDiffCache();
		cache.getIndexDiffCacheEntry(lookupRepository(gitDir));
		return jProject;
	}

	private IJavaProject createJavaProject(final Repository repository,
			final String projectName) throws Exception {
		final IJavaProject[] jProjectHolder = new IJavaProject[] { null };
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IProject project = root.getProject(projectName);
				if (project.exists()) {
					project.delete(true, null);
					TestUtil.waitForJobs(100, 5000);
				}
				IProjectDescription desc = ResourcesPlugin.getWorkspace()
						.newProjectDescription(projectName);
				desc.setLocation(
						new Path(new File(repository.getWorkTree(), projectName)
								.getPath()));
				project.create(desc, null);
				project.open(null);
				TestUtil.waitForJobs(50, 5000);
				IFolder bin = project.getFolder(BIN_FOLDER_NAME);
				if (!bin.exists()) {
					bin.create(IResource.FORCE | IResource.DERIVED, true, null);
				}
				IPath outputLocation = bin.getFullPath();
				IFolder src = project.getFolder(SRC_FOLDER_NAME);
				if (!src.exists()) {
					src.create(IResource.FORCE, true, null);
				}
				addNatureToProject(project, JavaCore.NATURE_ID);
				IJavaProject jProject = JavaCore.create(project);
				IPackageFragmentRoot srcContainer = jProject
						.getPackageFragmentRoot(src);
				IClasspathEntry srcEntry = JavaCore
						.newSourceEntry(srcContainer.getPath());
				IClasspathEntry jreEntry = JavaCore.newContainerEntry(
						JavaRuntime.getDefaultJREContainerEntry().getPath(),
						new IAccessRule[] {},
						new IClasspathAttribute[] {
								JavaCore.newClasspathAttribute(
										"owner.project.facets", "java") },
						false);
				jProject.setRawClasspath(
						new IClasspathEntry[] { srcEntry, jreEntry },
						outputLocation, true, null);
				IPackageFragment javaPackage = srcContainer
						.createPackageFragment(PACKAGE_NAME, true, null);
				javaPackage
						.createCompilationUnit(JAVA_FILE_NAME,
								"package " + PACKAGE_NAME + ";\nclass "
										+ JAVA_CLASS_NAME + " {\n\n}",
								true, null);
				jProjectHolder[0] = jProject;
			}
		};
		ResourcesPlugin.getWorkspace().run(runnable, null);
		return jProjectHolder[0];
	}

	private void addNatureToProject(IProject proj, String natureId)
			throws CoreException {
		IProjectDescription description = proj.getDescription();
		String[] prevNatures = description.getNatureIds();
		String[] newNatures = new String[prevNatures.length + 1];
		System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
		newNatures[prevNatures.length] = natureId;
		description.setNatureIds(newNatures);
		proj.setDescription(description, null);
	}

	private void removeJavaProject() throws CoreException {
		if (javaProject == null) {
			return;
		}
		final IProject project = javaProject.getProject();
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				for (int i = 0; i < MAX_DELETE_RETRY; i++) {
					try {
						project.delete(
								IResource.FORCE
										| IResource.ALWAYS_DELETE_PROJECT_CONTENT,
								null);
						break;
					} catch (CoreException e) {
						if (i == MAX_DELETE_RETRY - 1) {
							throw e;
						}
						try {
							Activator.logInfo(
									"Sleep before retrying to delete project "
											+ project.getLocationURI());
							Thread.sleep(DELETE_RETRY_DELAY);
						} catch (InterruptedException e1) {
						}
					}
				}

			}
		};
		ResourcesPlugin.getWorkspace().run(runnable, null);
	}
}
