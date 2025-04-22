package org.eclipse.egit.ui.internal.branch;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.internal.util.ProjectUtil;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.clone.ProjectRecord;
import org.eclipse.egit.ui.internal.clone.ProjectUtils;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;

class BranchProjectTracker {

	private static final String PREFIX = "BranchProjectTracker_"; //$NON-NLS-1$

	private static final String KEY_PROJECTS = "projects"; //$NON-NLS-1$

	private static final String KEY_PROJECT = "project"; //$NON-NLS-1$

	private static final String KEY_BRANCH = "branch"; //$NON-NLS-1$

	private final Repository repository;

	public BranchProjectTracker(final Repository repository) {
		this.repository = repository;
	}

	private String getBranch() {
		try {
			return repository.getBranch();
		} catch (IOException e) {
			return null;
		}
	}

	public String getPreference(final String branch) {
		if (branch == null)
			throw new IllegalArgumentException("Branch cannot be null"); //$NON-NLS-1$
		if (branch.length() == 0)
			throw new IllegalArgumentException("Branch cannot be empty"); //$NON-NLS-1$

		return PREFIX + '_' + repository.getDirectory().getAbsolutePath() + '_'
				+ branch;
	}

	public IMemento snapshot() {
		String branch = getBranch();
		if (branch == null)
			return null;

		IProject[] projects;
		try {
			projects = ProjectUtil.getValidOpenProjects(repository);
		} catch (CoreException e) {
			return null;
		}
		XMLMemento memento = XMLMemento.createWriteRoot(KEY_PROJECTS);
		memento.putString(KEY_BRANCH, branch);
		final String workDir = repository.getWorkTree().getAbsolutePath();
		for (IProject project : projects) {
			IPath path = project.getLocation();
			if (path == null)
				continue;
			String fullPath = path.toOSString();
			if (fullPath.startsWith(workDir)) {
				String relative = fullPath.substring(workDir.length());
				IMemento child = memento.createChild(KEY_PROJECT);
				child.putTextData(relative);
			}
		}
		return memento;
	}

	public BranchProjectTracker save(final IMemento memento) {
		if (!(memento instanceof XMLMemento))
			throw new IllegalArgumentException("Invalid memento"); //$NON-NLS-1$

		String branch = memento.getString(KEY_BRANCH);
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String pref = getPreference(branch);
		StringWriter writer = new StringWriter();
		try {
			((XMLMemento) memento).save(writer);
			store.setValue(pref, writer.toString());
		} catch (IOException e) {
			Activator.logError("Error writing branch-project associations", e); //$NON-NLS-1$
		}
		return this;
	}

	public String[] getProjectPaths() {
		String branch = getBranch();
		if (branch == null)
			return new String[0];
		return getProjectPaths(branch);
	}

	public String[] getProjectPaths(final String branch) {
		String pref = getPreference(branch);
		String value = Activator.getDefault().getPreferenceStore()
				.getString(pref);
		if (value.length() == 0)
			return new String[0];
		XMLMemento memento;
		try {
			memento = XMLMemento.createReadRoot(new StringReader(value));
		} catch (WorkbenchException e) {
			Activator.logError("Error reading branch-project associations", e); //$NON-NLS-1$
			return new String[0];
		}
		IMemento[] children = memento.getChildren(KEY_PROJECT);
		String[] projects = new String[children.length];
		for (int i = 0; i < children.length; i++)
			projects[i] = children[i].getTextData();
		return projects;
	}

	public void restore(final IProgressMonitor monitor) {
		String branch = getBranch();
		if (branch != null)
			restore(branch, monitor);
	}

	public void restore(final String branch, final IProgressMonitor monitor) {
		String[] paths = getProjectPaths(branch);
		if (paths.length == 0)
			return;

		Set<ProjectRecord> records = new LinkedHashSet<ProjectRecord>();
		File parent = repository.getWorkTree();
		for (String path : paths) {
			File root = new File(parent, path);
			if (!root.isDirectory())
				continue;
			if (!new File(root, IProjectDescription.DESCRIPTION_FILE_NAME)
					.exists())
				continue;
			records.add(new ProjectRecord(root));
		}
		if (records.isEmpty())
			return;
		IProgressMonitor importMonitor;
		if (monitor != null)
			importMonitor = monitor;
		else
			importMonitor = new NullProgressMonitor();
		try {
			ProjectUtils.createProjects(records, repository, null,
					importMonitor);
		} catch (InvocationTargetException e) {
			Activator
					.logError("Error restoring branch-project associations", e); //$NON-NLS-1$
		} catch (InterruptedException ignored) {
		}
	}
}
