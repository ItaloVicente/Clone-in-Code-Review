package org.eclipse.ui.tests.datatransfer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.tests.harness.FileSystemHelper;
import org.eclipse.ui.tests.TestPlugin;
import org.eclipse.ui.tests.harness.util.FileTool;
import org.eclipse.ui.tests.harness.util.FileUtil;

public class ImportTestUtils {

	public static class TestBuilder extends IncrementalProjectBuilder {

		static AtomicInteger cleanBuildCallCount = new AtomicInteger(0);
		static AtomicInteger autoBuildCallCount = new AtomicInteger(0);
		static AtomicInteger fullBuildCallCount = new AtomicInteger(0);
		static List<Integer> otherBuildTriggerTypes = new ArrayList<>();

		public TestBuilder() {
			resetCallCount();
		}

		@Override
		protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) {
			if (kind == IncrementalProjectBuilder.CLEAN_BUILD) {
				clean(monitor);
			} else if (kind == IncrementalProjectBuilder.AUTO_BUILD) {
				autoBuildCallCount.incrementAndGet();
			} else if (kind == IncrementalProjectBuilder.FULL_BUILD) {
				fullBuildCallCount.incrementAndGet();
			} else {
				otherBuildTriggerTypes.add(Integer.valueOf(kind));
			}
			return null;
		}

		@Override
		protected void clean(IProgressMonitor monitor) {
			cleanBuildCallCount.incrementAndGet();
			IProject project = getProject();
			deleteFolderContents(project, "bin");
		}

		static void resetCallCount() {
			cleanBuildCallCount.set(0);
			autoBuildCallCount.set(0);
			fullBuildCallCount.set(0);
			otherBuildTriggerTypes.clear();
		}

		static void assertCleanAutoBuildWasDone() {
			assertEquals("Expected 1 clean build trigger", 1, cleanBuildCallCount.get());
			assertEquals("Expected 1 auto-build trigger", 1, autoBuildCallCount.get());
			assertEquals("Expected no full build triggers", 0, fullBuildCallCount.get());
			assertEquals("Expected only clean and auto-build triggers", Collections.EMPTY_LIST, otherBuildTriggerTypes);
		}

		static void assertCleanFullBuildWasDone() {
			assertEquals("Expected 1 clean build trigger", 1, cleanBuildCallCount.get());
			assertEquals("Expected 1 full-build trigger", 1, fullBuildCallCount.get());
			assertEquals("Expected no auto-build triggers", 0, autoBuildCallCount.get());
			assertEquals("Expected only clean and auto-build triggers", Collections.EMPTY_LIST, otherBuildTriggerTypes);
		}

		static void assertNoBuildWasDone() {
			assertEquals("Expected no clean build triggers", 0, cleanBuildCallCount.get());
			assertEquals("Expected no full-build triggers", 0, fullBuildCallCount.get());
			assertEquals("Expected no auto-build triggers", 0, autoBuildCallCount.get());
			assertEquals("Expected no build triggers", Collections.EMPTY_LIST, otherBuildTriggerTypes);
		}
	}

	static final String WS_DATA_PREFIX = "data/workspaces";

	static String copyDataLocation(String dataLocation) throws IOException {
		TestPlugin plugin = TestPlugin.getDefault();
		if (plugin == null) {
			throw new IllegalStateException("TestPlugin default reference is null");
		}

		URL fullPathString = plugin.getBundle().getResource("/" + WS_DATA_PREFIX + "/" + dataLocation + ".zip");

		URI fileURI = null;
		try {
			fileURI = FileLocator.resolve(fullPathString).toURI();
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException();
		}

		File origin = new File(fileURI);
		if (!origin.exists()) {
			throw new IllegalArgumentException();
		}

		ZipFile zFile = new ZipFile(origin);

		File destination = new File(FileSystemHelper.getRandomLocation(FileSystemHelper.getTempDir()).toOSString());
		FileTool.unzip(zFile, destination);
		return destination.getAbsolutePath();
	}

	static String copyZipLocation(String sourceZipLocation, String targetZipName) throws IOException {
		TestPlugin plugin = TestPlugin.getDefault();
		if (plugin == null) {
			throw new IllegalStateException("TestPlugin default reference is null");
		}

		URL fullPathString = plugin.getBundle().getResource(WS_DATA_PREFIX + "/" + sourceZipLocation + ".zip");

		URI fileURI = null;
		try {
			fileURI = FileLocator.resolve(fullPathString).toURI();
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException();
		}

		File origin = new File(fileURI);
		if (!origin.exists()) {
			throw new IllegalArgumentException();
		}

		File destination = new File(FileSystemHelper.getRandomLocation(FileSystemHelper.getTempDir()).toOSString()
				+ File.separator + targetZipName + ".zip");
		FileTool.copy(origin, destination);
		return destination.getAbsolutePath();
	}

	static void assertBinFolderIsCleaned(String projectName) throws CoreException {
		IWorkspaceRoot root = workspaceRoot();
		IProject project = root.getProject(projectName);
		assertTrue("Expected project to exist in workspace: " + projectName, project.isAccessible());
		assertBinFolderIsCleaned(project);
	}

	static void assertBinFolderIsCleaned(IProject project) throws CoreException {
		IFolder bin = project.getFolder("bin");
		assertTrue("Expected bin/ folder of project " + project.getName() + " to exist", bin.isAccessible());
		class Visitor implements IResourceVisitor {

			List<IResource> contents = new ArrayList<>();

			@Override
			public boolean visit(IResource resource) {
				if (!resource.equals(bin)) {
					contents.add(resource);
				}
				return true;
			}

		}
		Visitor visitor = new Visitor();
		bin.accept(visitor);
		assertEquals("Expected bin folder to be empty", Collections.EMPTY_LIST, visitor.contents);
	}

	static void disableWorkspaceAutoBuild() throws CoreException {
		setWorkspaceAutoBuild(false);
	}

	static void setWorkspaceAutoBuild(boolean autobuildOn) throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IWorkspaceDescription description = workspace.getDescription();
		boolean oldAutoBuildingState = description.isAutoBuilding();
		if (oldAutoBuildingState != autobuildOn) {
			description.setAutoBuilding(autobuildOn);
			workspace.setDescription(description);
		}
	}

	static void waitForBuild() throws InterruptedException {
		Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_REFRESH, null);
		Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_REFRESH, null);
		Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
		Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);
	}

	static void deleteWorkspaceProjects() throws CoreException {
		IWorkspaceRoot root = workspaceRoot();
		IProject[] workspaceProjects = root.getProjects();
		for (IProject workspaceProject : workspaceProjects) {
			FileUtil.deleteProject(workspaceProject);
		}
	}

	static void deleteWorkspaceProjects(IProject... projects) throws CoreException {
		for (IProject project : projects) {
			FileUtil.deleteProject(project);
		}
	}

	private static IWorkspaceRoot workspaceRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	private static void deleteFolderContents(IProject project, String folderName) {
		IFolder folder = project.getFolder(folderName);
		class DeleteContents implements IResourceVisitor {
			@Override
			public boolean visit(IResource resource) throws CoreException {
				if (!folder.equals(resource)) {
					resource.delete(true, null);
					return false;
				}
				return true;
			}
		}
		if (folder.isAccessible()) {
			try {
				folder.accept(new DeleteContents());
			} catch (CoreException e) {
				String message = "Failed to delete contents of folder " + folderName + " of project "
						+ project.getName();
				throw new AssertionError(message, e);
			}
		}
	}
}
