package org.eclipse.ui.internal.dialogs.cpd;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.dialogs.cpd.CustomizePerspectiveDialog.DisplayItem;

class UnavailableContributionItemCheckListener implements
		ICheckStateListener {

	private final CustomizePerspectiveDialog dialog;
	private CheckboxTreeViewer viewer;
	private ICheckStateListener originalListener;

	public UnavailableContributionItemCheckListener(CustomizePerspectiveDialog dialog, CheckboxTreeViewer viewer,
			ICheckStateListener originalListener) {
		this.dialog = dialog;
		this.viewer = viewer;
		this.originalListener = originalListener;
	}

	@Override
	public void checkStateChanged(CheckStateChangedEvent event) {
		DisplayItem item = (DisplayItem) event.getElement();
		ViewerFilter[] filters = viewer.getFilters();
		boolean isEffectivelyAvailable = CustomizePerspectiveDialog.isEffectivelyAvailable(item, filters.length > 0 ? filters[0] : null);

		if (isEffectivelyAvailable) {
			originalListener.checkStateChanged(event);
			return;
		}

		boolean isAvailable = CustomizePerspectiveDialog.isAvailable(item);
		viewer.update(event.getElement(), null);

		if (isAvailable) {
			MessageBox mb = new MessageBox(viewer.getControl().getShell(), SWT.OK | SWT.ICON_WARNING | SWT.SHEET);
			mb.setText(WorkbenchMessages.HideItemsCannotMakeVisible_dialogTitle);
			mb.setMessage(NLS.bind(WorkbenchMessages.HideItemsCannotMakeVisible_unavailableChildrenText,
					item.getLabel()));
			mb.open();
			viewer.getExpandedState(item);
		} else {
			MessageBox mb = new MessageBox(viewer.getControl().getShell(), SWT.YES | SWT.NO | SWT.ICON_WARNING
					| SWT.SHEET);
			mb.setText(WorkbenchMessages.HideItemsCannotMakeVisible_dialogTitle);
			final String errorExplanation = NLS.bind(
					WorkbenchMessages.HideItemsCannotMakeVisible_unavailableCommandGroupText, item.getLabel(),
					item.getActionSet());
			final String message = NLS
					.bind("{0}{1}{1}{2}", new Object[] { errorExplanation, CustomizePerspectiveDialog.NEW_LINE, WorkbenchMessages.HideItemsCannotMakeVisible_switchToCommandGroupTab }); //$NON-NLS-1$
			mb.setMessage(message);
			if (mb.open() == SWT.YES) {
				dialog.showActionSet(item);
			}
		}
	}

}
