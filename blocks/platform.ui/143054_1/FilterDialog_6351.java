package org.eclipse.ui.internal.views.log;

import java.util.Comparator;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.actions.SelectionProviderAction;

public class EventDetailsDialogAction extends SelectionProviderAction {

	private LogView logView;
	private Control control;
	private ISelectionProvider provider;
	private EventDetailsDialog propertyDialog;
	private Comparator comparator;
	private IMemento memento;

	public EventDetailsDialogAction(LogView logView, Control control, ISelectionProvider provider, IMemento memento) {
		super(provider, Messages.EventDetailsDialog_action);
		this.logView = logView;
		Assert.isNotNull(control);
		this.control = control;
		this.provider = provider;
		this.memento = memento;
	}

	public boolean resetSelection(byte sortType, int sortOrder) {
		IAdaptable element = (IAdaptable) getStructuredSelection().getFirstElement();
		if (element == null)
			return false;
		if (propertyDialog != null && propertyDialog.isOpen()) {
			propertyDialog.resetSelection(element, sortType, sortOrder);
			return true;
		}
		return false;
	}

	public void resetSelection() {
		IAdaptable element = (IAdaptable) getStructuredSelection().getFirstElement();
		if ((element == null) || (!(element instanceof LogEntry)))
			return;
		if (propertyDialog != null && propertyDialog.isOpen())
			propertyDialog.resetSelection(element);
	}

	public void resetDialogButtons() {
		if (propertyDialog != null && propertyDialog.isOpen())
			propertyDialog.resetButtons();
	}

	public void setComparator(Comparator comparator) {
		this.comparator = comparator;
		if (propertyDialog != null && propertyDialog.isOpen())
			propertyDialog.setComparator(comparator);
	}

	@Override
	public void run() {
		if (propertyDialog != null && propertyDialog.isOpen()) {
			resetSelection();
			return;
		}

		IAdaptable element = (IAdaptable) getStructuredSelection().getFirstElement();
		if ((element == null) || (!(element instanceof LogEntry)))
			return;

		propertyDialog = new EventDetailsDialog(control.getShell(), logView, element, provider, comparator, memento);
		propertyDialog.create();
		propertyDialog.getShell().setText(Messages.EventDetailsDialog_title);
		propertyDialog.open();
	}
}
