package org.eclipse.ui.part;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

public class MultiPageSelectionProvider implements IPostSelectionProvider {

    private ListenerList listeners = new ListenerList();
    
    private ListenerList postListeners = new ListenerList();

    private MultiPageEditorPart multiPageEditor;

    public MultiPageSelectionProvider(MultiPageEditorPart multiPageEditor) {
        Assert.isNotNull(multiPageEditor);
        this.multiPageEditor = multiPageEditor;
    }

    @Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
        listeners.add(listener);
    }

    @Override
	public void addPostSelectionChangedListener(ISelectionChangedListener listener) {
    	postListeners.add(listener);
	}

    public void fireSelectionChanged(final SelectionChangedEvent event) {
        Object[] listeners = this.listeners.getListeners();
        fireEventChange(event, listeners);
    }

    public void firePostSelectionChanged(final SelectionChangedEvent event) {
		Object[] listeners = postListeners.getListeners();
		fireEventChange(event, listeners);
	}

	private void fireEventChange(final SelectionChangedEvent event, Object[] listeners) {
		for (int i = 0; i < listeners.length; ++i) {
            final ISelectionChangedListener l = (ISelectionChangedListener) listeners[i];
            SafeRunner.run(new SafeRunnable() {
                @Override
				public void run() {
                    l.selectionChanged(event);
                }
            });
        }
	}
    
    public MultiPageEditorPart getMultiPageEditor() {
        return multiPageEditor;
    }

    @Override
	public ISelection getSelection() {
        IEditorPart activeEditor = multiPageEditor.getActiveEditor();
        if (activeEditor != null) {
            ISelectionProvider selectionProvider = activeEditor.getSite()
                    .getSelectionProvider();
            if (selectionProvider != null) {
				return selectionProvider.getSelection();
			}
        }
        return StructuredSelection.EMPTY;
    }

    @Override
	public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        listeners.remove(listener);
    }
    
    @Override
	public void removePostSelectionChangedListener(ISelectionChangedListener listener) {
    	postListeners.remove(listener);
	}

    @Override
	public void setSelection(ISelection selection) {
        IEditorPart activeEditor = multiPageEditor.getActiveEditor();
        if (activeEditor != null) {
            ISelectionProvider selectionProvider = activeEditor.getSite()
                    .getSelectionProvider();
            if (selectionProvider != null) {
				selectionProvider.setSelection(selection);
			}
        }
    }
}
