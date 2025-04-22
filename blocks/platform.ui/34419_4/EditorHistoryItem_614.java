
package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IMemento;

public class EditorHistory {
    public static final int MAX_SIZE = 15;

    private ArrayList fifoList = new ArrayList(MAX_SIZE);

    public EditorHistory() {
        super();
    }

    public void add(IEditorInput input, IEditorDescriptor desc) {
		if (input != null && input.exists()) {
			add(new EditorHistoryItem(input, desc), 0);
		}
    }

    private void add(EditorHistoryItem newItem, int index) {
        if (newItem.isRestored()) {
            remove(newItem.getInput());
        }

        if (fifoList.size() == MAX_SIZE) {
            fifoList.remove(MAX_SIZE - 1);
        }

        fifoList.add(index < MAX_SIZE ? index : MAX_SIZE - 1, newItem);
    }

    public EditorHistoryItem[] getItems() {
        refresh();
        EditorHistoryItem[] array = new EditorHistoryItem[fifoList.size()];
        fifoList.toArray(array);
        return array;
    }

    public void refresh() {
        Iterator iter = fifoList.iterator();
        while (iter.hasNext()) {
            EditorHistoryItem item = (EditorHistoryItem) iter.next();
            if (item.isRestored()) {
                IEditorInput input = item.getInput();
                if (input != null && !input.exists()) {
					iter.remove();
				}
            }
        }
    }

    public void remove(EditorHistoryItem item) {
        fifoList.remove(item);
    }

    public void remove(IEditorInput input) {
        if (input == null) {
            return;
        }
        Iterator iter = fifoList.iterator();
        while (iter.hasNext()) {
            EditorHistoryItem item = (EditorHistoryItem) iter.next();
            if (item.matches(input)) {
                iter.remove();
            }
        }
    }

    public IStatus restoreState(IMemento memento) {
        IMemento[] mementos = memento.getChildren(IWorkbenchConstants.TAG_FILE);
        for (int i = 0; i < mementos.length; i++) {
            EditorHistoryItem item = new EditorHistoryItem(mementos[i]);
            if (!"".equals(item.getName()) || !"".equals(item.getToolTipText())) { //$NON-NLS-1$ //$NON-NLS-2$
                add(item, fifoList.size());
            }
        }
        return Status.OK_STATUS;
    }

    public IStatus saveState(IMemento memento) {
        Iterator iterator = fifoList.iterator();
        while (iterator.hasNext()) {
            EditorHistoryItem item = (EditorHistoryItem) iterator.next();
            if (item.canSave()) {
                IMemento itemMemento = memento
                        .createChild(IWorkbenchConstants.TAG_FILE);
                item.saveState(itemMemento);
            }
        }
        return Status.OK_STATUS;
    }
}
