
package org.eclipse.ui.tests.api.workbenchpart;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;

public class DynamicItem extends CompoundContributionItem {

	private Action action1;
	private Action action2;

	public DynamicItem() {
		makeActions();
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(null, "Sample View", message);
	}

	private void makeActions() {
		action1 = new Action() {
			@Override
			public void run() {
				showMessage("Dynamic Item 1 executed");
			}
		};
		action1.setText("Dynamic Item 1");
		action1.setToolTipText("Dynamic Item 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		action2 = new Action() {
			@Override
			public void run() {
				showMessage("Dynamic Item 2 executed");
			}
		};
		action2.setText("Dynamic Item 2");
		action2.setToolTipText("Dynamic Item 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		IContributionItem[] items = { new ActionContributionItem(action1),
				new ActionContributionItem(action2) };
		return items;
	}
}
