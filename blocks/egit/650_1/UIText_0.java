package org.eclipse.egit.core.op;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.CoreText;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.GitIndex.Entry;

public class DiscardChangesOperation implements IEGitOperation {

	IResource[] files;

	public DiscardChangesOperation(IResource[] files) {
		this.files = files;
	}

	public void execute(IProgressMonitor monitor) throws CoreException {
		IWorkspaceRunnable action = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				List<IResource> allFiles = new ArrayList<IResource>();
				for (IResource res : files) {
					allFiles.addAll(getAllMembers(res));
				}
				for (IResource res : allFiles) {
					Repository repo = getRepository(res);
					if (repo == null) {
						IStatus status = Activator.error(
								CoreText.DiscardChangesOperation_repoNotFound,
								null);
						throw new CoreException(status);
					}
					discardChange(res, repo, monitor);
				}
			}
		};
		ResourcesPlugin.getWorkspace().run(action, monitor);
	}

	private static Repository getRepository(IResource resource) {
		IProject project = resource.getProject();
		RepositoryMapping repositoryMapping = RepositoryMapping
				.getMapping(project);
		if (repositoryMapping != null)
			return repositoryMapping.getRepository();
		else
			return null;
	}

	private void discardChange(IResource res, Repository repository,
			IProgressMonitor monitor) throws CoreException {
		String resRelPath = RepositoryMapping.getMapping(res)
				.getRepoRelativePath(res);
		try {
			Entry e = repository.getIndex().getEntry(resRelPath);

			if (e != null && e.getStage() == 0
					&& e.isModified(repository.getWorkDir())) {
				repository.getIndex().checkoutEntry(repository.getWorkDir(), e);
				repository.getIndex().write();
				res.refreshLocal(0, monitor);
			}
		} catch (IOException e) {
			IStatus status = Activator.error(
					CoreText.DiscardChangesOperation_discardFailed, e);
			throw new CoreException(status);
		}
	}

	private ArrayList<IResource> getAllMembers(IResource res) {
		ArrayList<IResource> ret = new ArrayList<IResource>();
		if (res.getLocation().toFile().isFile()) {
			ret.add(res);
		} else {
			getAllMembersHelper(res, ret);
		}
		return ret;
	}

	private void getAllMembersHelper(IResource res, ArrayList<IResource> ret) {
		ArrayList<IResource> tmp = new ArrayList<IResource>();
		if (res instanceof IContainer) {
			IContainer cont = (IContainer) res;
			try {
				for (IResource r : cont.members()) {
					if (r.getLocation().toFile().isFile()) {
						tmp.add(r);
					} else {
						getAllMembersHelper(r, tmp);
					}
				}
			} catch (CoreException e) {
				return;
			}

			ret.addAll(tmp);
		}
	}

}
