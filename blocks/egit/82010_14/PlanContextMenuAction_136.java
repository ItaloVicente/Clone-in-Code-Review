package org.eclipse.egit.ui.internal.rebase;

import org.eclipse.egit.core.internal.rebase.RebaseInteractivePlan;
import org.eclipse.egit.core.internal.rebase.RebaseInteractivePlan.PlanElement;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.bindings.keys.SWTKeySupport;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;

class PlanContextMenuAction extends Action {

	private RebaseInteractivePlan.ElementAction action;
	private TreeViewer planViewer;
	private RebaseInteractiveStepActionToolBarProvider actionToolbarProvider;

	public PlanContextMenuAction(String text, ImageDescriptor image,
			RebaseInteractivePlan.ElementAction action, TreeViewer planViewer,
			RebaseInteractiveStepActionToolBarProvider actionToolbarProvider) {
		super(text, image);
		int accelerator = actionToolbarProvider.getActionAccelerators()
				.get(action).intValue();
		if (accelerator == SWT.DEL) {
			setText(text + '\t'
					+ SWTKeySupport.getKeyFormatterForPlatform()
							.format(SWTKeySupport.convertAcceleratorToKeyStroke(
									accelerator)));
		}
		setAccelerator(accelerator);
		this.action = action;
		this.planViewer = planViewer;
		this.actionToolbarProvider = actionToolbarProvider;
	}

	@Override
	public void run() {
		ISelection selection = planViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			for (Object selectedRow : structuredSelection.toList()) {
				if (selectedRow instanceof PlanElement) {
					PlanElement planElement = (PlanElement) selectedRow;
					planElement.setPlanElementAction(action);
				}
			}
			actionToolbarProvider.mapActionItemsToSelection(selection);
		}
	}
}
