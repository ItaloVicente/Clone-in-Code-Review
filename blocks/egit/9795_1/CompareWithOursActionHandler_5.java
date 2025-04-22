
package org.eclipse.egit.ui.internal.actions;

import java.util.Set;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.internal.CompareUtils;
import org.eclipse.egit.ui.internal.GitCompareFileRevisionEditorInput;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class CompareWithIndexStageActionHandler extends
		RepositoryActionHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IPath[] locations = getSelectedLocations(event);

		if (locations.length == 1 && locations[0].toFile().isFile()) {
			final IPath baseLocation = locations[0];
			final ITypedElement left = CompareUtils
					.getFileTypedElement(baseLocation);
			final ITypedElement right = CompareUtils.getIndexTypedElement(
					baseLocation, getStage());
			final ITypedElement ancestor = CompareUtils.getIndexTypedElement(
					baseLocation, DirCacheEntry.STAGE_1);
			final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
					left, right, ancestor, null);
			IWorkbenchPage workBenchPage = HandlerUtil.getActiveWorkbenchWindowChecked(event).getActivePage();
			CompareUtils.openInCompare(workBenchPage, in);
		}
		return null;
	}

	protected abstract int getStage();

	@Override
	public boolean isEnabled() {
		IPath[] paths = getSelectedLocations();
		if (paths.length == 1) {
			IPath path = paths[0];
			RepositoryMapping mapping = RepositoryMapping.getMapping(path);
			if (mapping != null) {
				Set<String> conflictingFiles = getConflictingFiles(mapping
						.getRepository());
				String relativePath = mapping.getRepoRelativePath(path);
				return conflictingFiles.contains(relativePath);
			}
		}
		return false;
	}
}
