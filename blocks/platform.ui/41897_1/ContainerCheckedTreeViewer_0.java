package org.eclipse.e4.ui.dialogs.viewer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.e4.ui.dialogs.filteredtree.FilteredCheckboxTree;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;


public class CachedCheckboxTreeViewer extends ContainerCheckedTreeViewer {

	private Set<Object> checkState = new HashSet<Object>();
	private static final Object[] NO_ELEMENTS = new Object[0];

	public CachedCheckboxTreeViewer(Tree tree) {
		super(tree);
		addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				updateCheckState(event.getElement(), event.getChecked());
			}
		});
		setUseHashlookup(true);
	}

	protected void updateCheckState(Object element, boolean state) {
		if (state) {
			if (checkState == null) {
				checkState = new HashSet<Object>();
			}

			ITreeContentProvider contentProvider = null;
			if (getContentProvider() instanceof ITreeContentProvider) {
				contentProvider = (ITreeContentProvider) getContentProvider();
			}

			if (contentProvider != null) {
				Object[] children = contentProvider.getChildren(element);
				if (children != null && children.length > 0) {
					for (Object element2 : children) {
						updateCheckState(element2, state);
					}
				} else if (!checkState.contains(element)) {
					checkState.add(element);
				}
			} else if (!checkState.contains(element)) {
				checkState.add(element);
			}
		} else if (checkState != null) {
			ITreeContentProvider contentProvider = null;
			if (getContentProvider() instanceof ITreeContentProvider) {
				contentProvider = (ITreeContentProvider) getContentProvider();
			}

			if (contentProvider != null) {
				Object[] children = contentProvider.getChildren(element);
				if (children.length > 0) {
					for (Object element2 : children) {
						updateCheckState(element2, state);
					}

				}
			}
			checkState.remove(element);
		}
	}

	public void restoreLeafCheckState() {
		if (checkState == null) {
			return;
		}

		getTree().setRedraw(false);
		super.setCheckedElements(NO_ELEMENTS);
		setGrayedElements(NO_ELEMENTS);
		if (!checkState.isEmpty()) {
			expandAll();
		}
		Iterator<Object> iter = checkState.iterator();
		Object element = null;

		while (iter.hasNext()) {
			element = iter.next();
			super.setChecked(element, true);
		}
		getTree().setRedraw(true);
	}

	public Object[] getCheckedLeafElements() {
		if (checkState == null) {
			return NO_ELEMENTS;
		}
		return checkState.toArray(new Object[checkState.size()]);
	}

	public int getCheckedLeafCount() {
		if (checkState == null) {
			return 0;
		}
		return checkState.size();
	}

	@Override
	public boolean setChecked(Object element, boolean state) {
		updateCheckState(element, state);
		return super.setChecked(element, state);
	}

	@Override
	public void setCheckedElements(Object[] elements) {
		super.setCheckedElements(elements);
		if (checkState == null) {
			checkState = new HashSet<Object>();
		} else {
			checkState.clear();
		}
		ITreeContentProvider contentProvider = null;
		if (getContentProvider() instanceof ITreeContentProvider) {
			contentProvider = (ITreeContentProvider) getContentProvider();
		}

		for (int i = 0; i < elements.length; i++) {
			Object[] children = contentProvider != null ? contentProvider.getChildren(elements[i]) : null;
			if (!getGrayed(elements[i]) && (children == null || children.length == 0)) {
				if (!checkState.contains(elements[i])) {
					checkState.add(elements[i]);
				}
			}
		}
	}

	@Override
	public boolean setSubtreeChecked(Object element, boolean state) {
		Widget widget = internalExpand(element, false);
		if (widget instanceof TreeItem) {
			TreeItem item = (TreeItem) widget;
			setChecked(element, state);
			setCheckedChildren(item, state);
			return true;
		}
		return false;
	}

	@Override
	protected void setCheckedChildren(Item item, boolean state) {
		createChildren(item);
		Item[] items = getChildren(item);
		if (items != null) {
			for (Item it : items) {
				if (it.getData() != null && (it instanceof TreeItem)) {
					TreeItem treeItem = (TreeItem) it;
					treeItem.setChecked(state);
					setChecked(treeItem.getData(), state);
					setCheckedChildren(treeItem, state);
				}
			}
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setAllChecked(boolean state) {
		super.setAllChecked(state);
		if (state) {

			Object[] visible = getFilteredChildren(getRoot());
			if (checkState == null) {
				checkState = new HashSet<Object>();
			}

			ITreeContentProvider contentProvider = null;
			if (getContentProvider() instanceof ITreeContentProvider) {
				contentProvider = (ITreeContentProvider) getContentProvider();
			}

			if (contentProvider == null) {
				for (Object element : visible) {
					checkState.add(element);
				}
			} else {
				Set<Object> toCheck = new HashSet<Object>();
				for (Object element : visible) {
					addFilteredChildren(element, contentProvider, toCheck);
				}
				checkState.addAll(toCheck);
			}
		} else {
			if (checkState != null) {
				Object[] visible = filter(checkState.toArray());
				for (Object element : visible) {
					checkState.remove(element);
				}
			}
		}
	}

	private void addFilteredChildren(Object element, ITreeContentProvider contentProvider, Collection<Object> result) {
		if (!contentProvider.hasChildren(element)) {
			result.add(element);
		} else {
			Object[] visibleChildren = getFilteredChildren(element);
			for (Object visibleChild : visibleChildren) {
				addFilteredChildren(visibleChild, contentProvider, result);
			}
		}
	}

	@Override
	public void remove(Object[] elementsOrTreePaths) {
		for (Object elementsOrTreePath : elementsOrTreePaths) {
			updateCheckState(elementsOrTreePath, false);
		}
		super.remove(elementsOrTreePaths);
	}

	@Override
	public void remove(Object elementsOrTreePaths) {
		updateCheckState(elementsOrTreePaths, false);
		super.remove(elementsOrTreePaths);
	}

}
