package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.egit.ui.internal.push.SimpleConfigurePushDialog;
import org.eclipse.egit.ui.internal.selection.SelectionUtils;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.swt.SWT;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

public class DynamicSimplePushCommand extends CompoundContributionItem {

	@Override
	protected IContributionItem[] getContributionItems() {
		Repository repo = getSelectedRepository();
		if (repo != null) {
			String commandID = "org.eclipse.egit.ui.team.SimplePush"; //$NON-NLS-1$
			CommandContributionItemParameter parameter = new CommandContributionItemParameter(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
					commandID, commandID, SWT.PUSH);
			parameter.label = "Dynamic Push to Upstream"; //$NON-NLS-1$
			parameter.mnemonic = "P"; //$NON-NLS-1$

			RemoteConfig config = SimpleConfigurePushDialog
					.getConfiguredRemoteCached(repo);
			if (config != null && config.getURIs().size() == 1) {
				parameter.label += " (" //$NON-NLS-1$
						+ config.getURIs().get(0).getHost() + ")";//$NON-NLS-1$
			}

			return new IContributionItem[] {
					new CommandContributionItem(parameter) };
		}
		return new IContributionItem[] {};
	}

	private Repository getSelectedRepository() {
		Repository repo = null;
		IStructuredSelection selection = getSelection();
		if (selection != null) {
			repo = SelectionUtils.getRepository(selection);
		}
		return repo;
	}

	private IStructuredSelection getSelection() {
		try {
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			ISelectionService selectionService = window.getSelectionService();
			ISelection selection = selectionService.getSelection();
			if (selection != null) {
				return SelectionUtils.getStructuredSelection(selection);
			}
		} catch (Exception e) {
		}
		return null;
	}
}
