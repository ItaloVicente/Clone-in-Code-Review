package org.eclipse.egit.ui.internal.commit.command;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.op.TagOperation;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.egit.ui.internal.dialogs.CreateTagDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.TagBuilder;
import org.eclipse.ui.handlers.HandlerUtil;

public class CreateTagHandler extends CommitCommandHandler {

	public static final String ID = "org.eclipse.egit.ui.commit.CreateTag"; //$NON-NLS-1$

	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<RepositoryCommit> commits = getCommits(event);
		if (commits.size() == 1) {
			RepositoryCommit commit = commits.get(0);

			CreateTagDialog dialog = new CreateTagDialog(
					HandlerUtil.getActiveShellChecked(event), commit
							.getRevCommit().getId(), commit.getRepository());

			if (dialog.open() != Window.OK)
				return null;

			final TagBuilder tag = new TagBuilder();
			PersonIdent personIdent = new PersonIdent(commit.getRepository());
			String tagName = dialog.getTagName();

			tag.setTag(tagName);
			tag.setTagger(personIdent);
			tag.setMessage(dialog.getTagMessage());
			tag.setObjectId(commit.getRevCommit());

			try {
				new TagOperation(commit.getRepository(), tag,
						dialog.shouldOverWriteTag())
						.execute(new NullProgressMonitor());
			} catch (CoreException e) {
				throw new ExecutionException(e.getMessage(), e);
			}
		}
		return null;
	}

}
