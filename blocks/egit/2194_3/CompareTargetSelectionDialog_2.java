
package org.eclipse.egit.ui.internal.actions;

import java.io.IOException;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.internal.storage.GitFileRevision;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.FileRevisionTypedElement;
import org.eclipse.egit.ui.internal.GitCompareFileRevisionEditorInput;
import org.eclipse.egit.ui.internal.LocalFileRevision;
import org.eclipse.egit.ui.internal.dialogs.CompareTargetSelectionDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.team.core.history.IFileRevision;

public class CompareWithRefActionHandler extends RepositoryActionHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IResource resource = getSelectedResources(event)[0];
		final RepositoryMapping mapping = RepositoryMapping.getMapping(resource
				.getProject());
		if (mapping == null || mapping.getRepository() == null)
			return null;
		final Repository repo = mapping.getRepository();

		CompareTargetSelectionDialog dlg = new CompareTargetSelectionDialog(
				getShell(event), repo, resource.getFullPath().toString());
		if (dlg.open() == Window.OK) {

			final IFile baseFile = (IFile) resource;

			final ITypedElement base = new FileRevisionTypedElement(
					new LocalFileRevision(baseFile));

			final ITypedElement next;
			try {
				next = getElementForRef(mapping.getRepository(), mapping
						.getRepoRelativePath(baseFile), dlg.getRefName());
			} catch (IOException e) {
				Activator.handleError(
						UIText.CompareWithIndexAction_errorOnAddToIndex, e,
						true);
				return null;
			}

			final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
					base, next, null);
			in.getCompareConfiguration().setRightLabel(dlg.getRefName());
			CompareUI.openCompareEditor(in);
		}
		return null;
	}

	private ITypedElement getElementForRef(final Repository repository,
			final String gitPath, final String refName) throws IOException {
		ObjectId commitId = repository.resolve(refName + "^{commit}"); //$NON-NLS-1$
		RevWalk rw = new RevWalk(repository);
		RevCommit commit = rw.parseCommit(commitId);
		rw.release();

		IFileRevision nextFile = GitFileRevision.inCommit(repository, commit,
				gitPath, null);

		FileRevisionTypedElement element = new FileRevisionTypedElement(
				nextFile);
		return element;
	}

	@Override
	public boolean isEnabled() {
		final IResource[] selectedResources = getSelectedResources();
		if (selectedResources.length != 1)
			return false;

		final IResource resource = selectedResources[0];
		if (!(resource instanceof IFile)) {
			return false;
		}
		final RepositoryMapping mapping = RepositoryMapping.getMapping(resource
				.getProject());
		return mapping != null;
	}

}
