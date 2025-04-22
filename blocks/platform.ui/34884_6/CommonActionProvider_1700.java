package org.eclipse.ui.internal.navigator.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.internal.navigator.CommonNavigatorMessages;
import org.eclipse.ui.wizards.IWizardDescriptor;

public class WizardShortcutAction extends Action implements IPluginContribution {
	private IWizardDescriptor descriptor;

	private IWorkbenchWindow window;

	public WizardShortcutAction(IWorkbenchWindow aWindow,
			IWizardDescriptor aDescriptor) {
		super(aDescriptor.getLabel());
		descriptor = aDescriptor;
		setToolTipText(descriptor.getDescription());
		setImageDescriptor(descriptor.getImageDescriptor());
		setId(ActionFactory.NEW.getId());
		this.window = aWindow;
	}

	@Override
	public void run() {

		IWorkbenchWizard wizard;
		try {
			wizard = descriptor.createWizard();
		} catch (CoreException e) {
			ErrorDialog.openError(window.getShell(),
					CommonNavigatorMessages.NewProjectWizard_errorTitle,
					CommonNavigatorMessages.NewProjectAction_text, e
							.getStatus());
			return;
		}

		ISelection selection = window.getSelectionService().getSelection();

		if (selection instanceof IStructuredSelection) {
			wizard
					.init(window.getWorkbench(),
							(IStructuredSelection) selection);
		} else {
			wizard.init(window.getWorkbench(), StructuredSelection.EMPTY);
		}
		
		if(descriptor.canFinishEarly() && !descriptor.hasPages()) {
			wizard.performFinish();
		} else {
			Shell parent = window.getShell();
			WizardDialog dialog = new WizardDialog(parent, wizard);
			dialog.create();
			dialog.open();
		}
	}

	@Override
	public String getLocalId() {
		return descriptor.getId();
	}

	@Override
	public String getPluginId() {
		return descriptor.getId();
	}

}
