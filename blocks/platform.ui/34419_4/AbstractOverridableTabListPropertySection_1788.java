package org.eclipse.ui.internal.views.properties.tabbed.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchPart;

public class TabbedPropertyViewer extends StructuredViewer {

	protected TabbedPropertyList list;
	protected List elements;
	protected IWorkbenchPart part;

	public TabbedPropertyViewer(TabbedPropertyList list) {
		this.list = list;
		hookControl(list);
		elements = new ArrayList();
	}

	public Object getElementAt(int index) {
		if (index >= 0 && index < elements.size()) {
			return elements.get(index);
		}
		return null;
	}

	public int getSelectionIndex() {
		return list.getSelectionIndex();
	}

	protected Widget doFindInputItem(Object element) {
		return null;
	}

	protected Widget doFindItem(Object element) {
		return null;
	}

	protected void doUpdateItem(Widget item, Object element, boolean fullMap) {
	}

	protected List getSelectionFromWidget() {
		int index = list.getSelectionIndex();
		if (index == TabbedPropertyList.NONE) {
			return Collections.EMPTY_LIST;
		}
		List result = new ArrayList(1);
		result.add(getElementAt(index));
		return result;
	}

	protected void internalRefresh(Object element) {
	}

	public void reveal(Object element) {
	}

	protected void setSelectionToWidget(List l, boolean reveal) {
		if (l == null || l.size() == 0) { // clear selection
			list.deselectAll();
		} else {
			Object object = l.get(0);
			int index = -1;
			for (int i = 0; i < elements.size(); i++) {
				if (elements.get(i) == object) {
					index = i;
				}
			}
			Assert.isTrue(index != -1, "Could not set the selected tab in the tabbed property viewer");//$NON-NLS-1$
			list.select(index);
		}
	}

	public Control getControl() {
		return list;
	}

	protected void inputChanged(Object input, Object oldInput) {
		elements.clear();
		Object[] children = getSortedChildren(getRoot());
		list.removeAll();
		for (int i = 0; i < children.length; i++) {
			elements.add(children[i]);
			mapElement(children[i], list);
		}
		list.setElements(children);
	}

	public void setInput(IWorkbenchPart part, ISelection selection) {
		this.part = part;
		setInput(selection);
	}

	public IWorkbenchPart getWorkbenchPart() {
		return part;
	}


	public List getElements() {
		return elements;
	}
}
