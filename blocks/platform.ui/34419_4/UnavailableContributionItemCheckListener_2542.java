package org.eclipse.ui.internal.dialogs.cpd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ICheckable;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

public class TreeManager {
	public static final int CHECKSTATE_UNCHECKED = 0;
	public static final int CHECKSTATE_GRAY = 1;
	public static final int CHECKSTATE_CHECKED = 2;

	private static ICheckStateProvider checkStateProvider = null;
	private static IBaseLabelProvider labelProvider = null;
	private static ICheckStateListener viewerCheckListener = null;
	private static ITreeContentProvider treeContentProvider = null;

	private List<CheckListener> listeners = new ArrayList<CheckListener>();
	private LocalResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());

	public interface CheckListener {
		public void checkChanged(TreeItem changedItem);
	}

	public static class ModelListenerForCheckboxTree implements CheckListener {
		private CheckboxTreeViewer treeViewer;
		public ModelListenerForCheckboxTree(TreeManager manager, CheckboxTreeViewer treeViewer) {
			this.treeViewer = treeViewer;
			manager.addListener(this);
		}

		@Override
		public void checkChanged(TreeItem changedItem) {
			treeViewer.update(changedItem, null);
		}
	}

	public static class ModelListenerForCheckboxTable implements CheckListener {
		private CheckboxTableViewer tableViewer;
		public ModelListenerForCheckboxTable(TreeManager manager, CheckboxTableViewer tableViewer) {
			this.tableViewer = tableViewer;
			manager.addListener(this);
		}

		@Override
		public void checkChanged(TreeItem changedItem) {
			tableViewer.update(changedItem, null);
		}
	}

	public static class ViewerCheckStateListener implements ICheckStateListener {
		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
			Object checked = event.getElement();
			if(checked instanceof TreeItem) {
				((TreeItem)checked).setChangedByUser(true);
				((TreeItem)checked).setCheckState(event.getChecked());
			}
		}
	}

	public static class CheckStateProvider implements ICheckStateProvider {
		@Override
		public boolean isChecked(Object element) {
			return ((TreeItem)element).checkState != CHECKSTATE_UNCHECKED;
		}

		@Override
		public boolean isGrayed(Object element) {
			return ((TreeItem)element).checkState == CHECKSTATE_GRAY;
		}
	}

	public static class TreeItemLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof TreeItem) {
				return ((TreeItem) element).getLabel();
			}
			return super.getText(element);
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof TreeItem) {
				return ((TreeItem) element).getImage();
			}
			return super.getImage(element);
		}
	}

	public static class TreeItemContentProvider implements ITreeContentProvider {
		@Override
		public Object[] getChildren(Object parentElement) {
			return ((TreeItem)parentElement).getChildren().toArray();
		}

		@Override
		public Object getParent(Object element) {
			return ((TreeItem)element).getParent();
		}

		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		@Override
		public void dispose() {}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
	}

	public static ICheckStateProvider getCheckStateProvider() {
		if(checkStateProvider == null) {
			checkStateProvider = new CheckStateProvider();
		}
		return checkStateProvider;
	}

	public static IBaseLabelProvider getLabelProvider() {
		if(labelProvider == null) {
			labelProvider = new TreeItemLabelProvider();
		}
		return labelProvider;
	}

	public static ITreeContentProvider getTreeContentProvider() {
		if(treeContentProvider == null)
			treeContentProvider = new TreeItemContentProvider();
		return treeContentProvider;
	}

	public ICheckStateListener getViewerCheckStateListener() {
		if(viewerCheckListener == null)
			viewerCheckListener = new ViewerCheckStateListener();
		return viewerCheckListener;
	}

	public class TreeItem {
		private String label;
		private ImageDescriptor imageDescriptor;
		private Image image;
		private TreeItem parent;
		private List<TreeItem> children;
		private int checkState;
		private boolean changedByUser;

		public TreeItem(String label) {
			this.label = label;
			this.children = new ArrayList<TreeItem>();
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public Image getImage() {
			if(image == null) {
				if(imageDescriptor == null) {
					return null;
				}
				image = resourceManager.createImage(imageDescriptor);
			}
			return image;
		}

		public void setImageDescriptor(ImageDescriptor imageDescriptor) {
			this.imageDescriptor = imageDescriptor;
		}

		public void addChild(TreeItem newChild) {
            newChild.parent = this;
            children.add(newChild);
            synchParents(newChild);
        }

		public List<TreeItem> getChildren() {
			return children;
		}

		public int getChildrenCount() {
			return children.size();
		}

		public TreeItem getParent() {
			return parent;
		}

        private void internalSetCheckState(int newState) {
			if (newState == checkState)
				return;

			checkState = newState;
			fireListeners(this);
        }

		public void setCheckState(boolean checked) {
			int newState = checked ? CHECKSTATE_CHECKED : CHECKSTATE_UNCHECKED;
			if (checkState == newState)
				return;
			internalSetCheckState(newState);

			synchChildren(this);
			synchParents(this);
		}

		public boolean getState() {
			return !(checkState == CHECKSTATE_UNCHECKED);
		}

		int getCheckState() {
			return checkState;
		}

		private void synchChildren(TreeItem changedItem) {
			int newState = changedItem.checkState;

			if (newState != CHECKSTATE_GRAY) {
				for (TreeItem treeItem : changedItem.children) {
					TreeItem curItem = treeItem;
					curItem.internalSetCheckState(newState);
					curItem.setChangedByUser(changedItem.isChangedByUser());

					synchChildren(curItem);
				}
			}
		}

		private void synchParents(TreeItem changedItem) {
			if(changedItem.parent == null)
				return;

			int newState = changedItem.checkState;

			if (newState == CHECKSTATE_GRAY) {
				while (changedItem.parent != null && changedItem.parent.checkState != CHECKSTATE_GRAY) {
					changedItem.parent.internalSetCheckState(CHECKSTATE_GRAY);
					if (changedItem.isChangedByUser()) {
						changedItem.parent.setChangedByUser(true);
					}
					changedItem = changedItem.parent;
				}
			} else {
				boolean checkedFound = newState == CHECKSTATE_CHECKED;
				boolean uncheckedFound = newState == CHECKSTATE_UNCHECKED;
				for (Iterator<TreeItem> i = changedItem.parent.children.iterator(); i.hasNext()
						&& (!checkedFound || !uncheckedFound);) {
					TreeItem item = i.next();
					switch(item.checkState) {
					case CHECKSTATE_CHECKED: {
						checkedFound = true;
						break;
					} case CHECKSTATE_GRAY: {
						checkedFound = uncheckedFound = true;
						break;
					} case CHECKSTATE_UNCHECKED: {
						uncheckedFound = true;
						break;
					}}
				}

				int oldState = changedItem.parent.checkState;
				if(checkedFound && uncheckedFound) {
					changedItem.parent.internalSetCheckState(CHECKSTATE_GRAY);
				} else if (checkedFound) {
					changedItem.parent.internalSetCheckState(CHECKSTATE_CHECKED);
				} else {
					changedItem.parent.internalSetCheckState(CHECKSTATE_UNCHECKED);
				}
				if(oldState != changedItem.parent.checkState) {
					if (changedItem.isChangedByUser()) {
						changedItem.parent.setChangedByUser(true);
					}
					synchParents(changedItem.parent);
				}
			}
		}

		public void setChangedByUser(boolean changedByUser) {
			this.changedByUser = changedByUser;
		}

		public boolean isChangedByUser() {
			return changedByUser;
		}

		@Override
		public String toString() {
			return label + ", check=" + getState() + ", changed=" + changedByUser; //$NON-NLS-1$ //$NON-NLS-2$
		}

	}

	public TreeManager() {
		listeners = new ArrayList<CheckListener>();
	}

	public void addListener(CheckListener listener) {
		listeners.add(listener);
	}

	public CheckListener getCheckListener(ICheckable viewer) {
		if(viewer instanceof CheckboxTreeViewer)
			return new ModelListenerForCheckboxTree(this, (CheckboxTreeViewer)viewer);
		if(viewer instanceof CheckboxTableViewer)
			return new ModelListenerForCheckboxTable(this, (CheckboxTableViewer)viewer);
		return null;
	}

	public void attachAll(CheckboxTreeViewer viewer) {
		viewer.setLabelProvider(getLabelProvider());
		viewer.setCheckStateProvider(getCheckStateProvider());
		viewer.setContentProvider(getTreeContentProvider());
		viewer.addCheckStateListener(getViewerCheckStateListener());
		getCheckListener(viewer);
	}

	public void attachAll(CheckboxTableViewer viewer) {
		viewer.setLabelProvider(getLabelProvider());
		viewer.setCheckStateProvider(getCheckStateProvider());
		viewer.setContentProvider(getTreeContentProvider());
		viewer.addCheckStateListener(getViewerCheckStateListener());
		getCheckListener(viewer);
	}

	public void removeListener(CheckListener listener) {
		listeners.remove(listener);
	}

	private void fireListeners(TreeItem item) {
		for (CheckListener checkListener : listeners) {
			CheckListener listener = checkListener;
			listener.checkChanged(item);
		}
	}

	public void dispose() {
		resourceManager.dispose();
		resourceManager = null;
		listeners.clear();
		listeners = null;
	}
}
