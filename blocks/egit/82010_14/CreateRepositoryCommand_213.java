package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.IOException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.repository.CreateBranchWizard;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNodeType;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class CreateBranchCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final RepositoryTreeNode node = getSelectedNodes(event).get(0);

		if (node.getType() == RepositoryTreeNodeType.ADDITIONALREF) {
			Ref ref = (Ref) node.getObject();
			try (RevWalk rw = new RevWalk(node.getRepository())) {
				RevCommit baseCommit = rw
						.parseCommit(ref.getLeaf().getObjectId());
				WizardDialog dlg = new WizardDialog(
						getShell(event),
						new CreateBranchWizard(node.getRepository(), baseCommit.name()));
				dlg.setHelpAvailable(false);
				dlg.open();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
			return null;
		}
		String base = null;
		if (node.getObject() instanceof Ref)
			base = ((Ref) node.getObject()).getName();
		new WizardDialog(getShell(event), new CreateBranchWizard(node
				.getRepository(), base)).open();
		return null;
	}

	@Override
	public boolean isEnabled() {
		return selectedRepositoryHasHead();
	}

}
