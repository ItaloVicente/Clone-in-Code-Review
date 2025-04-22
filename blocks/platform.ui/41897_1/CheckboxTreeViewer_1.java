package org.eclipse.e4.ui.dialogs.viewer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

public class ContainerCheckedTreeViewer extends CheckboxTreeViewer {

	public ContainerCheckedTreeViewer(Composite parent) {
		super(parent);
		initViewer();
	}

	public ContainerCheckedTreeViewer(Composite parent, int style) {
		super(parent, style);
		initViewer();
	}

	public ContainerCheckedTreeViewer(Tree tree) {
		super(tree);
		initViewer();
	}

	private void initViewer() {
		setUseHashlookup(true);
		addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				doCheckStateChanged(event.getElement());
			}
		});
		addTreeListener(new ITreeViewerListener() {
			@Override
			public void treeCollapsed(TreeExpansionEvent event) {
			}

			@Override
			public void treeExpanded(TreeExpansionEvent event) {
				Widget item = findItem(event.getElement());
				if (item instanceof TreeItem) {
					initializeItem((TreeItem) item);
				}
			}
		});
	}

	protected void doCheckStateChanged(Object element) {
		Widget item = findItem(element);
		if (item instanceof TreeItem) {
			TreeItem treeItem = (TreeItem) item;
			treeItem.setGrayed(false);
			updateChildrenItems(treeItem);
			updateParentItems(treeItem.getParentItem());
		}
	}

	private void initializeItem(TreeItem item) {
		if (item.getChecked() && !item.getGrayed()) {
			updateChildrenItems(item);
		}
	}

	private void updateChildrenItems(TreeItem parent) {
		Item[] children = getChildren(parent);
		boolean state = parent.getChecked();
		for (Item element : children) {
			TreeItem curr = (TreeItem) element;
			if (curr.getData() != null
					&& ((curr.getChecked() != state) || curr.getGrayed())) {
				curr.setChecked(state);
				curr.setGrayed(false);
				updateChildrenItems(curr);
			}
		}
	}

	private void updateParentItems(TreeItem item) {
		if (item != null) {
			Item[] children = getChildren(item);
			boolean containsChecked = false;
			boolean containsUnchecked = false;
			for (Item element : children) {
				TreeItem curr = (TreeItem) element;
				containsChecked |= curr.getChecked();
				containsUnchecked |= (!curr.getChecked() || curr.getGrayed());
			}
			item.setChecked(containsChecked);
			item.setGrayed(containsChecked && containsUnchecked);
			updateParentItems(item.getParentItem());
		}
	}


	@Override
	public boolean setChecked(Object element, boolean state) {
		if (super.setChecked(element, state)) {
			doCheckStateChanged(element);
			return true;
		}
		return false;
	}


	@Override
	public void setCheckedElements(Object[] elements) {
		super.setCheckedElements(elements);
		for (Object element : elements) {
			doCheckStateChanged(element);
		}
	}


	@Override
	protected void setExpanded(Item item, boolean expand) {
		super.setExpanded(item, expand);
		if (expand && item instanceof TreeItem) {
			initializeItem((TreeItem) item);
		}
	}


	@Override
	public Object[] getCheckedElements() {
		Object[] checked = super.getCheckedElements();
		List<Object> result = new ArrayList<Object>();
		for (Object curr : checked) {
			result.add(curr);
			Widget item = findItem(curr);
			if (item != null) {
				Item[] children = getChildren(item);
				if (children.length == 1 && children[0].getData() == null) {
					collectChildren(curr, result);
				}
			}
		}
		return result.toArray();
	}

	private void collectChildren(Object element, List<Object> result) {
		Object[] filteredChildren = getFilteredChildren(element);
		for (Object curr : filteredChildren) {
			result.add(curr);
			collectChildren(curr, result);
		}
	}

}
