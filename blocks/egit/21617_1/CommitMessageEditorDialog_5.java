package org.eclipse.egit.ui.internal.history.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.CommonUtils;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.egit.ui.internal.history.GitHistoryPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;

public class SquashHandler extends AbstractHistoryCommandHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repository = getRepository(event);
		List<RevCommit> commits = getSelectedCommits();

		try {
			if (!areCommitsInCurrentBranch(commits, repository)) {
				MessageDialog.openError(getPage().getSite().getShell(),
						UIText.SquashHandler_Error_Title,
						UIText.SquashHandler_CommitsNotOnCurrentBranch);
				return null;
			}
		} catch (IOException e) {
			throw new ExecutionException(
					UIText.SquashHandler_ErrorCheckingIfCommitsAreOnCurrentBranch, e);
		}

		List<RepositoryCommit> repositoryCommits = new ArrayList<RepositoryCommit>();
		for (RevCommit commit : commits)
			repositoryCommits.add(new RepositoryCommit(repository, commit));

		final IStructuredSelection selected = new StructuredSelection(
				repositoryCommits);
		CommonUtils.runCommand(
				org.eclipse.egit.ui.internal.commit.command.SquashHandler.ID,
				selected);

		return null;
	}

	private boolean areCommitsInCurrentBranch(Collection<RevCommit> commits,
			Repository repository) throws IOException {

		RevWalk walk = new RevWalk(repository);
		ObjectId headCommitId = repository.resolve(Constants.HEAD);
		RevCommit headCommit = walk.parseCommit(headCommitId);

		for (final RevCommit commit : commits) {
			walk.reset();
			walk.markStart(headCommit);

			RevFilter revFilter = new RevFilter() {
				@Override
				public boolean include(RevWalk walker, RevCommit cmit)
						throws StopWalkException, MissingObjectException,
						IncorrectObjectTypeException, IOException {

					return cmit.equals(commit);
				}

				@Override
				public RevFilter clone() {
					return null;
				}
			};
			walk.setRevFilter(revFilter);

			if (walk.next() == null)
				return false;
		}
		return true;
	}

	@Override
	public boolean isEnabled() {
		GitHistoryPage page = getPage();
		if (page == null)
			return false;
		IStructuredSelection selection = getSelection(page);
		if (selection.isEmpty())
			return false;

		List elements = selection.toList();
		int parentsNotSelected = 0;
		for (Object element : elements) {
			RevCommit commit = (RevCommit) element;

			if (commit.getParentCount() != 1)
				return false;

			RevCommit parentCommit = commit.getParent(0);
			if (!elements.contains(parentCommit))
				parentsNotSelected++;

			if (parentsNotSelected > 1)
				return false;
		}

		if (parentsNotSelected != 1)
			return false;

		return true;
	}

}
