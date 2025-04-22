package org.eclipse.egit.ui.internal;

import java.util.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

public class CachedCheckboxTreeViewer extends ContainerCheckedTreeViewer {

	private Set checkState = new HashSet();

	protected CachedCheckboxTreeViewer(Tree tree) {
		super(tree);
		addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				updateCheckState(event.getElement(), event.getChecked());
			}
		});
		setUseHashlookup(true);
	}

	protected void updateCheckState(final Object element, final boolean state) {
		if (state) {
			if (checkState == null) {
				checkState = new HashSet();
			}

			ITreeContentProvider contentProvider = null;
			if (getContentProvider() instanceof ITreeContentProvider) {
				contentProvider = (ITreeContentProvider) getContentProvider();
			}

			if (contentProvider != null) {
				Object[] children = contentProvider.getChildren(element);
				if (children != null && children.length > 0) {
					for (int i = 0; i < children.length; i++) {
						updateCheckState(children[i], state);
					}
				} else {
					checkState.add(element);
				}
			} else {
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
					for (int i = 0; i < children.length; i++) {
						updateCheckState(children[i], state);
					}

				}
			}
			checkState.remove(element);
		}
	}

	public void restoreLeafCheckState() {
		if (checkState == null)
			return;

		getTree().setRedraw(false);
		super.setCheckedElements(new Object[0]);
		setGrayedElements(new Object[0]);
		Iterator iter = checkState.iterator();
		Object element = null;
		if (iter.hasNext())
			expandAll();
		while (iter.hasNext()) {
			element = iter.next();
			super.setChecked(element, true);
		}
		getTree().setRedraw(true);
	}

	public Object[] getCheckedLeafElements() {
		if (checkState == null) {
			return new Object[0];
		}
		return checkState.toArray(new Object[checkState.size()]);
	}

	public int getCheckedLeafCount() {
		if (checkState == null) {
			return 0;
		}
		return checkState.size();
	}

	public boolean setChecked(Object element, boolean state) {
		updateCheckState(element, state);
		return super.setChecked(element, state);
	}

	public void setCheckedElements(Object[] elements) {
		super.setCheckedElements(elements);
		if (checkState == null) {
			checkState = new HashSet();
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

	public void setAllChecked(boolean state) {
		super.setAllChecked(state);
		if (state) {

			Object[] visible = getFilteredChildren(getRoot());
			if (checkState == null) {
				checkState = new HashSet();
			}

			ITreeContentProvider contentProvider = null;
			if (getContentProvider() instanceof ITreeContentProvider) {
				contentProvider = (ITreeContentProvider) getContentProvider();
			}

			if (contentProvider == null) {
				for (int i = 0; i < visible.length; i++) {
					checkState.add(visible[i]);
				}
			} else {
				Set toCheck = new HashSet();
				for (int i = 0; i < visible.length; i++) {
					addFilteredChildren(visible[i], contentProvider, toCheck);
				}
				checkState.addAll(toCheck);
			}
		} else {
			if (checkState != null) {
				Object[] visible = filter(checkState.toArray());
				for (int i = 0; i < visible.length; i++) {
					checkState.remove(visible[i]);
				}
			}
		}
	}

	private void addFilteredChildren(Object element, ITreeContentProvider contentProvider, Collection result) {
		if (!contentProvider.hasChildren(element)) {
			result.add(element);
		} else {
			Object[] visibleChildren = getFilteredChildren(element);
			for (int i = 0; i < visibleChildren.length; i++) {
				addFilteredChildren(visibleChildren[i], contentProvider, result);
			}
		}
	}

	public void remove(Object[] elementsOrTreePaths) {
		for (int i = 0; i < elementsOrTreePaths.length; i++) {
			updateCheckState(elementsOrTreePaths[i], false);
		}
		super.remove(elementsOrTreePaths);
	}

	public void remove(Object elementsOrTreePaths) {
		updateCheckState(elementsOrTreePaths, false);
		super.remove(elementsOrTreePaths);
	}

}
