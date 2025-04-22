package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.op.TagOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.ValidationUtils;
import org.eclipse.egit.ui.internal.dialogs.CreateTagDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Tag;
import org.eclipse.jgit.revplot.PlotCommit;
import org.eclipse.jgit.revwalk.RevCommit;

public class CreateTagOnCommitActionHandler extends RepositoryActionHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		PlotCommit commit = (PlotCommit) getSelection(event).getFirstElement();
		final Repository repo = getRepository(false, event);

		CreateTagDialog dialog = new CreateTagDialog(getShell(event),
				ValidationUtils
						.getRefNameInputValidator(repo, Constants.R_TAGS),
				commit.getId());

		dialog.setExistingTags(getRevTags(event));
		if (dialog.open() != Window.OK)
			return null;

		final Tag tag = new Tag(repo);
		PersonIdent personIdent = new PersonIdent(repo);
		String tagName = dialog.getTagName();

		tag.setTag(tagName);
		tag.setTagger(personIdent);
		tag.setMessage(dialog.getTagMessage());

		tag.setObjId(commit.getId());

		try {
			new TagOperation(repo, tag, false)
					.execute(new NullProgressMonitor());
		} catch (CoreException e) {
			throw new ExecutionException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		try {
			IStructuredSelection sel = getSelection(null);
			return sel.size() == 1
					&& sel.getFirstElement() instanceof RevCommit;
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, false);
			return false;
		}
	}
}
