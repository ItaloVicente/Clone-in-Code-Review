package org.eclipse.egit.ui.internal.actions;

import static org.eclipse.jgit.junit.JGitTestUtil.write;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.egit.ui.common.LocalRepositoryTestCase;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LinkedResourcesTest extends LocalRepositoryTestCase {

	private static File standaloneDirectory;

	private static final String LINKED_FILE = "LinkedFile";

	private static final String STANDALONE_FOLDER = "StandaloneFolder";

	private File gitDir;

	private IProject project;

	@Before
	public void setUp() throws Exception {
		gitDir = createProjectAndCommitToRepository();
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1);
		File userHome = FS.DETECTED.userHome();
		standaloneDirectory = new File(userHome, STANDALONE_FOLDER);
		if (standaloneDirectory.exists())
			FileUtils.delete(standaloneDirectory, FileUtils.RECURSIVE
					| FileUtils.RETRY);
		if (!standaloneDirectory.exists())
			FileUtils.mkdir(standaloneDirectory, true);
	}

	@After
	public void tearDown() throws Exception {
		deleteAllProjects();
		shutDownRepositories();
		FileUtils.delete(gitDir.getParentFile(), FileUtils.RECURSIVE
				| FileUtils.RETRY);
		FileUtils.delete(standaloneDirectory, FileUtils.RECURSIVE
				| FileUtils.RETRY);
	}

	private List<RepositoryActionHandler> getRepositoryActionHandlerList()
			throws CoreException {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint("org.eclipse.ui.commands");
		IConfigurationElement[] elements = point.getConfigurationElements();
		ArrayList<RepositoryActionHandler> result = new ArrayList<RepositoryActionHandler>();
		for (IConfigurationElement e: elements) {
			String categoryId = e.getAttribute("categoryId");
			if ("org.eclipse.egit.ui.commandCategory".equals(categoryId)) {
				if (e.getAttribute("defaultHandler") != null) {
					Object o = e.createExecutableExtension("defaultHandler");
					if (o instanceof RepositoryActionHandler)
						result.add((RepositoryActionHandler) o);
				}
			}
		}
		return result;
	}

	@Test
	public void testSomeActionsWithoutLinkedResources() throws Exception {
		List<RepositoryActionHandler> handlers = getRepositoryActionHandlerList();
		int count = 0;
		IFile selection = project.getFile(FILE1);
		for (RepositoryActionHandler handler: handlers) {
			handler.setSelection(new StructuredSelection(selection));
			if (handler.isEnabled())
				count++;
		}
		assertTrue(
				"Some EGit action should be enabled, please review this test.",
				count > 0);
	}

	@Test
	public void testNoActionOnLinkedResources() throws Exception {
		List<RepositoryActionHandler> handlers = getRepositoryActionHandlerList();
		File standaloneFile = new File(standaloneDirectory, LINKED_FILE);
		write(standaloneFile, "Something");
		IFile linkedFile = project.getFile(LINKED_FILE);
		linkedFile.createLink(standaloneFile.toURI(),
				IResource.ALLOW_MISSING_LOCAL, null);
		Object[] mixedSelection = { linkedFile,
				project.getFile(FILE1), project.getFile(FILE2) };
		for (RepositoryActionHandler handler : handlers) {
			handler.setSelection(new StructuredSelection(linkedFile));
			assertFalse(
					handler.getClass().getSimpleName()
							+ " is enabled on a linked resource pointing outside any project and repository.",
					handler.isEnabled());
			handler.setSelection(new StructuredSelection(mixedSelection));
			assertFalse(
					handler.getClass().getSimpleName()
							+ " is enabled when selection contains a linked resource pointing outside any project and repository.",
					handler.isEnabled());
		}
	}

	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "LinkedResourcesTest"; //$NON-NLS-1$
	}
}
