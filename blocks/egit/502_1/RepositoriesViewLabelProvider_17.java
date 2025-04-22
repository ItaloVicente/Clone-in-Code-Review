package org.eclipse.egit.ui.internal.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.clone.GitProjectsImportPage;
import org.eclipse.egit.ui.internal.repository.RepositoryTreeNode.RepositoryTreeNodeType;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.statushandlers.StatusManager;

public class RepositoriesViewContentProvider implements ITreeContentProvider {

	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {

		Comparator<RepositoryTreeNode<Repository>> sorter = new Comparator<RepositoryTreeNode<Repository>>() {

			public int compare(RepositoryTreeNode<Repository> o1,
					RepositoryTreeNode<Repository> o2) {
				return getRepositoryName(o1.getObject()).compareTo(
						getRepositoryName(o2.getObject()));
			}

		};

		Set<RepositoryTreeNode<Repository>> output = new TreeSet<RepositoryTreeNode<Repository>>(
				sorter);

		for (Repository repo : ((List<Repository>) inputElement)) {
			output.add(new RepositoryTreeNode<Repository>(null,
					RepositoryTreeNodeType.REPO, repo, repo));
		}

		return output.toArray();
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	public Object[] getChildren(Object parentElement) {

		RepositoryTreeNode node = (RepositoryTreeNode) parentElement;
		Repository repo = node.getRepository();

		switch (node.getType()) {

		case BRANCHES: {
			List<RepositoryTreeNode<Ref>> refs = new ArrayList<RepositoryTreeNode<Ref>>();

			for (Ref ref : repo.getAllRefs().values()) {
				refs.add(new RepositoryTreeNode<Ref>(node,
						RepositoryTreeNodeType.REF, repo, ref));
			}

			return refs.toArray();
		}

		case REMOTES: {
			List<RepositoryTreeNode<String>> remotes = new ArrayList<RepositoryTreeNode<String>>();

			Repository rep = node.getRepository();

			Set<String> configNames = rep.getConfig().getSubsections(
					RepositoriesView.REMOTE);

			for (String configName : configNames) {
				remotes.add(new RepositoryTreeNode<String>(node,
						RepositoryTreeNodeType.REMOTE, repo, configName));
			}

			return remotes.toArray();
		}

		case REPO: {
			List<RepositoryTreeNode<Repository>> branches = new ArrayList<RepositoryTreeNode<Repository>>();

			branches.add(new RepositoryTreeNode<Repository>(node,
					RepositoryTreeNodeType.BRANCHES, node.getRepository(), node
							.getRepository()));

			branches.add(new RepositoryTreeNode<Repository>(node,
					RepositoryTreeNodeType.WORKINGDIR, node.getRepository(),
					node.getRepository()));

			branches.add(new RepositoryTreeNode<Repository>(node,
					RepositoryTreeNodeType.PROJECTS, node.getRepository(), node
							.getRepository()));

			branches.add(new RepositoryTreeNode<Repository>(node,
					RepositoryTreeNodeType.REMOTES, node.getRepository(), node
							.getRepository()));

			return branches.toArray();
		}

		case PROJECTS: {
			List<RepositoryTreeNode<File>> projects = new ArrayList<RepositoryTreeNode<File>>();

			Collection<File> result = new HashSet<File>();
			Set<String> traversed = new HashSet<String>();
			collectProjectFilesFromDirectory(result, repo.getDirectory()
					.getParentFile(), traversed, new NullProgressMonitor());
			for (File file : result) {
				projects.add(new RepositoryTreeNode<File>(node,
						RepositoryTreeNodeType.PROJ, repo, file));
			}

			Comparator<RepositoryTreeNode<File>> sorter = new Comparator<RepositoryTreeNode<File>>() {

				public int compare(RepositoryTreeNode<File> o1,
						RepositoryTreeNode<File> o2) {
					return o1.getObject().getName().compareTo(
							o2.getObject().getName());
				}
			};
			Collections.sort(projects, sorter);

			return projects.toArray();
		}

		case WORKINGDIR: {
			List<RepositoryTreeNode<File>> children = new ArrayList<RepositoryTreeNode<File>>();

			File workingDir = repo.getWorkDir();
			if (workingDir == null || !workingDir.exists())
				return null;

			File[] childFiles = workingDir.listFiles();
			for (File file : childFiles) {
				if (file.isDirectory()) {
					children.add(new RepositoryTreeNode<File>(node,
							RepositoryTreeNodeType.FOLDER, repo, file));
				} else {
					children.add(new RepositoryTreeNode<File>(node,
							RepositoryTreeNodeType.FILE, repo, file));
				}
			}

			return children.toArray();
		}

		case FOLDER: {
			List<RepositoryTreeNode<File>> children = new ArrayList<RepositoryTreeNode<File>>();

			File parent = ((File) node.getObject());

			File[] childFiles = parent.listFiles();
			for (File file : childFiles) {
				if (file.isDirectory()) {
					children.add(new RepositoryTreeNode<File>(node,
							RepositoryTreeNodeType.FOLDER, repo, file));
				} else {
					children.add(new RepositoryTreeNode<File>(node,
							RepositoryTreeNodeType.FILE, repo, file));
				}
			}

			return children.toArray();
		}

		default:
			return null;
		}

	}

	public Object getParent(Object element) {

		return ((RepositoryTreeNode) element).getParent();
	}

	public boolean hasChildren(Object element) {
		Object[] children = getChildren(element);
		return children != null && children.length > 0;
	}

	private boolean collectProjectFilesFromDirectory(Collection<File> files,
			File directory, Set<String> directoriesVisited,
			IProgressMonitor monitor) {

		if (monitor.isCanceled()) {
			return false;
		}
		monitor.subTask(NLS.bind(
				UIText.RepositoriesView_Checking_Message,
				directory.getPath()));
		File[] contents = directory.listFiles();
		if (contents == null)
			return false;

		final String dotProject = IProjectDescription.DESCRIPTION_FILE_NAME;
		for (int i = 0; i < contents.length; i++) {
			File file = contents[i];
			if (file.isFile() && file.getName().equals(dotProject)) {
				files.add(file.getParentFile());
				return true;
			}
		}
		for (int i = 0; i < contents.length; i++) {
			if (contents[i].isDirectory()) {
				if (!contents[i].getName().equals(
						GitProjectsImportPage.METADATA_FOLDER)) {
					try {
						String canonicalPath = contents[i].getCanonicalPath();
						if (!directoriesVisited.add(canonicalPath)) {
							continue;
						}
					} catch (IOException exception) {
						StatusManager.getManager().handle(
								new Status(IStatus.ERROR, Activator
										.getPluginId(), exception
										.getLocalizedMessage(), exception));

					}
					collectProjectFilesFromDirectory(files, contents[i],
							directoriesVisited, monitor);
				}
			}
		}
		return true;
	}

	private static String getRepositoryName(Repository repository) {
		return repository.getDirectory().getParentFile().getName();
	}
}
