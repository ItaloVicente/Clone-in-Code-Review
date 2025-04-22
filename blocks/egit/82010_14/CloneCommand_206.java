package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.clone.GitCloneWizard;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jface.wizard.WizardDialog;

public class CloneCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {
	private String presetURI;

	public CloneCommand() {
		this(null);
	}

	public CloneCommand(String presetURI) {
		this.presetURI = presetURI;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		GitCloneWizard wizard;
		if (presetURI == null)
			wizard = new GitCloneWizard();
		else
			wizard = new GitCloneWizard(presetURI);
		wizard.setShowProjectImport(true);
		WizardDialog dlg = new WizardDialog(getShell(event), wizard);
		dlg.setHelpAvailable(true);
		dlg.open();
		return null;
	}
}
