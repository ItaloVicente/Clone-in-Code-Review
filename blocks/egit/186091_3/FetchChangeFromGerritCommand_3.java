package org.eclipse.egit.ui.internal.commands.shared;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.clone.GitSelectRepositoryPage;
import org.eclipse.egit.ui.internal.dialogs.MinimumSizeWizardDialog;
import org.eclipse.egit.ui.internal.dialogs.NonBlockingWizardDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractFetchFromHostCommand
		extends AbstractSharedCommandHandler {

	@Override
	public final Object execute(ExecutionEvent event)
			throws ExecutionException {
		Repository repository = getRepository(event);

		Clipboard clipboard = new Clipboard(
				PlatformUI.getWorkbench().getDisplay());
		String clipText;
		try {
			clipText = (String) clipboard
					.getContents(TextTransfer.getInstance());
		} finally {
			clipboard.dispose();
		}

		if (repository == null) {
			Shell shell = getShell(event);
			repository = askForRepository(shell, createSelectionPage());
			if (repository == null) {
				return null;
			}
		}

		Wizard wiz = createFetchWizard(repository, clipText);
		NonBlockingWizardDialog dlg = new NonBlockingWizardDialog(
				HandlerUtil.getActiveShellChecked(event), wiz);
		dlg.setHelpAvailable(false);
		dlg.open();
		return null;
	}

	protected abstract GitSelectRepositoryPage createSelectionPage();

	protected abstract Wizard createFetchWizard(@NonNull Repository repository,
			String clipText);

	private Repository askForRepository(Shell shell,
			GitSelectRepositoryPage page) {
		Repository[] result = { null };
		Wizard wizard = new Wizard() {

			@Override
			public boolean performFinish() {
				result[0] = page.getRepository();
				return true;
			}
		};
		wizard.addPage(page);
		wizard.setWindowTitle(page.getTitle());
		WizardDialog wizardDialog = new MinimumSizeWizardDialog(shell,
				wizard) {

			@Override
			protected Button createButton(Composite parent, int id,
					String label, boolean defaultButton) {
				String text = label;
				if (id == IDialogConstants.FINISH_ID) {
					text = UIText.GerritSelectRepositoryPage_FinishButtonLabel;
				}
				return super.createButton(parent, id, text, defaultButton);
			}
		};
		wizardDialog.setHelpAvailable(false);
		if (wizardDialog.open() != Window.OK) {
			return null;
		}
		return result[0];
	}
}
