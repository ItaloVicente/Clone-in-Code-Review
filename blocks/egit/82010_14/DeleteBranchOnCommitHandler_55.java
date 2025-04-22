package org.eclipse.egit.ui.internal.history.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.op.TagOperation;
import org.eclipse.egit.ui.internal.dialogs.CreateTagDialog;
import org.eclipse.egit.ui.internal.history.GitHistoryPage;
import org.eclipse.egit.ui.internal.push.PushTagsWizard;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TagBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.ui.handlers.HandlerUtil;

public class CreateTagOnCommitHandler extends AbstractHistoryCommandHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ObjectId commitId = getSelectedCommitId(event);
		final Repository repo = getRepository(event);

		CreateTagDialog dialog = new CreateTagDialog(
				HandlerUtil.getActiveShellChecked(event), commitId, repo);

		if (dialog.open() != Window.OK)
			return null;

		final TagBuilder tag = new TagBuilder();
		PersonIdent personIdent = new PersonIdent(repo);
		String tagName = dialog.getTagName();

		tag.setTag(tagName);
		tag.setTagger(personIdent);
		tag.setMessage(dialog.getTagMessage());
		tag.setObjectId(commitId, Constants.OBJ_COMMIT);

		try {
			new TagOperation(repo, tag, dialog.shouldOverWriteTag())
					.execute(new NullProgressMonitor());
		} catch (CoreException e) {
			throw new ExecutionException(e.getMessage(), e);
		}

		if (dialog.shouldStartPushWizard())
			PushTagsWizard.openWizardDialog(repo, tagName);

		return null;
	}

	@Override
	public boolean isEnabled() {
		GitHistoryPage page = getPage();
		if (page == null)
			return false;
		IStructuredSelection sel = getSelection(page);
		return sel.size() == 1 && sel.getFirstElement() instanceof RevCommit;
	}
}
