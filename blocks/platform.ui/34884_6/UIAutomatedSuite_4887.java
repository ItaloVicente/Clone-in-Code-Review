
package org.eclipse.ui.tests.dialogs;

import java.io.StringWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.model.ResourceFactory;

import junit.framework.TestCase;

public class UIAbstractFilteredResourcesSelectionDialog extends TestCase {


	private static final String HISTORY_SETTINGS = "History";

	private static final String DEFAULT_ROOT_NODE_NAME = "historyRootNode"; //$NON-NLS-1$

	private static final String DEFAULT_INFO_NODE_NAME = "infoNode";

	private static final String DIALOG_SETTINGS = "org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog"; //$NON-NLS-1$


	private static final String PROJECT_NAME = "FilteredResourcesTestProject";

	private static final String FOLDER_NAME1 = "testFolder1";

	private static final String FOLDER_NAME2 = "testFolder2";

	private static final String[] HISTORY_FILES = new String[] { "file1H.txt",
			"file2H.avi", "_FiLe4H.java", "afile4H.txt", "bfile5H.txt",
			"cfile6H.txt" };

	private static final String[] NONHISTORY_FILES = new String[] {
			"file1.txt", "file2.avi", "_FiLe4.java", "afile4.txt",
			"bfile5.txt", "cfile6.txt" };


	protected IResource[] historyResources;

	protected IResource[] nonHistoryResources;

	protected IProject project;

	protected String getProjectName() {
		return PROJECT_NAME;
	}

	protected String getFirstFolderName() {
		return FOLDER_NAME1;
	}

	protected String getSecondFolderName() {
		return FOLDER_NAME2;
	}

	protected String[] getHistoryFiles() {
		return HISTORY_FILES;
	}

	protected String[] getNonHistoryFiles() {
		return NONHISTORY_FILES;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		initializeProject();


		IDialogSettings settings = IDEWorkbenchPlugin.getDefault()
				.getDialogSettings().getSection(DIALOG_SETTINGS);

		if (settings == null) {
			settings = IDEWorkbenchPlugin.getDefault().getDialogSettings()
					.addNewSection(DIALOG_SETTINGS);
		}


		XMLMemento memento = XMLMemento.createWriteRoot(HISTORY_SETTINGS);

		IMemento historyMemento = memento.createChild(DEFAULT_ROOT_NODE_NAME);

		for (int i = 0; i < historyResources.length; i++) {
			IResource item = historyResources[i];
			IMemento elementMemento = historyMemento
					.createChild(DEFAULT_INFO_NODE_NAME);
			ResourceFactory resourceFactory = new ResourceFactory(item);
			resourceFactory.saveState(elementMemento);
		}

		StringWriter writer = new StringWriter();

		memento.save(writer);
		settings.put(HISTORY_SETTINGS, writer.getBuffer().toString());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		finalizeProject();
	}

	private void initializeProject() throws Exception {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		project = root.getProject(getProjectName());

		IProject[] projects = root.getProjects();
		for (int i = 0; i < projects.length; i++) {
			projects[i].delete(true, null);
		}

		project.create(null);
		project.open(null);
		IFolder folder1 = project.getFolder(getFirstFolderName());
		IFolder folder2 = project.getFolder(getSecondFolderName());
		folder1.create(false, true, null);
		folder2.create(false, true, null);
		String[] historyFiles = getHistoryFiles();
		if (historyFiles == null || historyFiles.length == 0) {
			historyResources = new IResource[0];
		} else {
			historyResources = new IResource[historyFiles.length];
			for (int i = 0; i < historyFiles.length; i++) {
				IFile file = project.getFile(folder1.getProjectRelativePath()
						.append("/" + historyFiles[i]));
				file.create(null, false, null);
				historyResources[i] = file;
			}
		}
		String[] files = getNonHistoryFiles();
		if (files == null || files.length == 0) {
			nonHistoryResources = new IResource[0];
		} else {
			int length = files.length;
			nonHistoryResources = new IResource[length * 2];
			for (int i = 0; i < length; i++) {
				IFile file = project.getFile(folder1.getProjectRelativePath()
						.append("/" + files[i]));
				file.create(null, false, null);
				nonHistoryResources[i] = file;
			}
			for (int i = 0; i < length; i++) {
				IFile file = project.getFile(folder2.getProjectRelativePath()
						.append("/" + files[i]));
				file.create(null, false, null);
				nonHistoryResources[length + i] = file;
			}
		}
	}

	private void finalizeProject() throws CoreException {
		if (project != null) {
			project.delete(true, null);
		}
	}

}
