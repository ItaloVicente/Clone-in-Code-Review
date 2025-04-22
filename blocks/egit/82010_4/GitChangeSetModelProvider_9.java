package org.eclipse.egit.ui.internal.synchronize;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.internal.storage.GitFileRevision;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.CompareUtils;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.dialogs.CompareTreeView;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class DefaultGitSynchronizer implements GitSynchronizer {

	@NonNull
	private final Display currentDisplay;

	public DefaultGitSynchronizer(@NonNull Display currentDisplay) {
		this.currentDisplay = currentDisplay;
	}

	protected boolean canCompareDirectly(IResource[] resources) {
		if (resources.length == 1) {
			IResource resource = resources[0];
			if (resource instanceof IFile) {
				return canCompareDirectly((IFile) resource);
			} else {
				IPath location = resource.getLocation();
				if (location != null
						&& Files.isSymbolicLink(location.toFile().toPath())) {
					return true;
				}
			}
		}
		return false;
	}

	protected boolean canCompareDirectly(@NonNull IFile file) {
		return true;
	}

	@Override
	public void compare(IResource[] resources, @NonNull Repository repository,
			String leftRev, String rightRev, boolean includeLocal,
			IWorkbenchPage page) throws IOException {
		if (canCompareDirectly(resources)) {
			if (includeLocal) {
				CompareUtils.compareWorkspaceWithRef(
						repository, resources[0], rightRev, page);
			} else {
				final IResource file = resources[0];
				Assert.isNotNull(file);
				final RepositoryMapping mapping = RepositoryMapping
						.getMapping(file);
				if (mapping == null) {
					Activator.error(
							NLS.bind(UIText.GitHistoryPage_errorLookingUpPath,
									file.getLocation(),
									repository),
							null);
					return;
				}
				final String gitPath = mapping.getRepoRelativePath(file);

				CompareUtils.compareBetween(repository, gitPath, leftRev,
						rightRev, page);
			}
		} else {
			synchronize(resources, repository, leftRev, rightRev, includeLocal);
		}
	}

	@Override
	public void compare(IFile file, Repository repository, String leftPath,
			String rightPath, String leftRev, String rightRev,
			boolean includeLocal, IWorkbenchPage page) throws IOException {
		if (canCompareDirectly(file)) {
			if (includeLocal) {
				CompareUtils.compareWorkspaceWithRef(repository, file, rightRev,
						page);
			} else {
				CompareUtils.compareBetween(repository, leftPath, rightPath,
						leftRev, rightRev, page);
			}
		} else {
			synchronize(new IResource[] { file }, repository, leftRev, rightRev,
					includeLocal);
		}
	}

	protected void synchronize(IResource[] resources, Repository repository,
			String leftRev, String rightRev, boolean includeLocal)
			throws IOException {
		if (rightRev.equals(GitFileRevision.INDEX)) {
			openGitTreeCompare(resources, leftRev,
					CompareTreeView.INDEX_VERSION, includeLocal);
		} else if (leftRev.equals(GitFileRevision.INDEX)) {
			Set<IResource> resSet = new HashSet<>(
					Arrays.asList(resources));
			final GitSynchronizeData gsd = new GitSynchronizeData(
					repository, leftRev, rightRev, true, resSet);
			GitModelSynchronize.launch(new GitSynchronizeDataSet(gsd),
					resources);
		} else {
			Set<IResource> resSet = new HashSet<>(
					Arrays.asList(resources));
			final GitSynchronizeData gsd = new GitSynchronizeData(
					repository, leftRev, rightRev, includeLocal,
					resSet);
			GitModelSynchronize.launch(new GitSynchronizeDataSet(gsd),
					resources);
		}
	}

	protected void openGitTreeCompare(IResource[] resources, String leftRev,
			String rightRev, boolean includeLocal) {
		currentDisplay.asyncExec(new Runnable() {

			@Override
			public void run() {
				CompareTreeView view;
				try {
					view = (CompareTreeView) PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage()
							.showView(CompareTreeView.ID);
					if (includeLocal)
						view.setInput(resources, rightRev);
					else
						view.setInput(resources, leftRev, rightRev);
				} catch (PartInitException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
			}
		});
	}

}
