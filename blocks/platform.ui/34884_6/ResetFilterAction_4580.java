package org.eclipse.jface.tests.viewers.interactive;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILazyContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class LazyVirtualTableView extends VirtualTableView {

	private List elements;

	public LazyVirtualTableView() {
		super();
		initElements();
	}

	private void initElements() {
		elements = new ArrayList();
		for (int i = 0; i < itemCount; i++) {
			elements.add("Element " + String.valueOf(i));
		}
	}

	@Override
	protected IContentProvider getContentProvider() {
		return new ILazyContentProvider() {

			@Override
			public void updateElement(int index) {
				viewer.replace(elements.get(index), index);
			}

			@Override
			public void dispose() {
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		};
	}

	protected void doRemove(Object[] selection, int[] selectionIndices) {
		for (int i = 0; i < selectionIndices.length; i++) {
			int index = selectionIndices[i];
			elements.remove(index);
		}
		super.doRemove(selection);
	}

	@Override
	protected void resetInput() {
		viewer.setItemCount(itemCount);
		super.resetInput();
	}
}
