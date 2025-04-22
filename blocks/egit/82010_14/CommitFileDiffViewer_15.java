package org.eclipse.egit.ui.internal.handler;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.core.AdapterUtils;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class SelectionHandler extends AbstractHandler {

	protected IStructuredSelection getSelection(final ExecutionEvent event) {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection == null || selection.isEmpty())
			selection = HandlerUtil.getActiveMenuSelection(event);
		if (selection instanceof IStructuredSelection)
			return (IStructuredSelection) selection;
		return StructuredSelection.EMPTY;
	}

	protected <V> V getSelectedItem(final Class<V> itemClass,
			final ExecutionEvent event) {
		final Object selected = getSelection(event).getFirstElement();
		return AdapterUtils.adapt(selected, itemClass);
	}

	protected <V> List<V> getSelectedItems(Class<V> itemClass,
			ExecutionEvent event) {
		final List<V> items = new LinkedList<>();
		for (Object selected : getSelection(event).toArray()) {
			V adapted = AdapterUtils.adapt(selected, itemClass);
			if (adapted != null)
				items.add(adapted);
		}
		return items;
	}

	protected IWorkbenchPart getPart(ExecutionEvent event)
			throws ExecutionException {
		return HandlerUtil.getActivePartChecked(event);
	}
}
