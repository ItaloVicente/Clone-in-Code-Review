package org.eclipse.egit.core.internal;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.lib.Repository;

public class FileChecker {

	public static class CheckResult {

		private boolean isOk = true;

		private boolean containsNonWorkspaceFiles = false;

		private boolean containsNotSharedResources = false;

		private Map<String, CheckResultEntry> problems = new HashMap<String, FileChecker.CheckResultEntry>();

		public boolean isOk() {
			return isOk;
		}

		public boolean containsNonWorkspaceFiles() {
			return containsNonWorkspaceFiles;
		}

		public boolean containsNotSharedResources() {
			return containsNotSharedResources;
		}

		public Map<String, CheckResultEntry> getProblems() {
			return problems;
		}

		void addEntry(String path, CheckResultEntry entry) {
			isOk = false;
			if (!entry.inWorkspace)
				containsNonWorkspaceFiles = true;
			if (entry.inWorkspace && !entry.shared)
				containsNotSharedResources = true;
			problems.put(path, entry);
		}

		public CheckResultEntry getEntry(String path) {
			return problems.get(path);
		}
	}

	public static class CheckResultEntry {
		final public boolean inWorkspace;

		final public boolean shared;

		public CheckResultEntry(boolean inWorkspace, boolean shared) {
			this.inWorkspace = inWorkspace;
			this.shared = shared;
		}

	}

	public static CheckResult checkFiles(Repository repository,
			Collection<String> files) {
		CheckResult result = new CheckResult();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String workTreePath = repository.getWorkTree().getAbsolutePath();
		for (String filePath : files) {
			IFile[] filesForLocation = root.findFilesForLocationURI(new File(
					workTreePath, filePath).toURI());
			if (filesForLocation.length == 0) {
				result.addEntry(filePath, new CheckResultEntry(false, false));
				continue;
			}
			boolean mappedResourceFound = false;
			for (IFile file : filesForLocation) {
				if (RepositoryMapping.getMapping(file) != null) {
					mappedResourceFound = true;
					break;
				}
			}
			if (!mappedResourceFound)
				result.addEntry(filePath, new CheckResultEntry(true, false));
		}
		return result;
	}

}
