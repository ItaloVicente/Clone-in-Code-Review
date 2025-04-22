package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.XMLMemento;

public class NavigationHistoryEditorInfo {
    String editorID;

    IEditorInput editorInput;

    int refCount = 0;

    IMemento memento;

    NavigationHistoryEditorInfo(IEditorPart part) {
        editorID = part.getSite().getId();
        editorInput = part.getEditorInput();
    }

    NavigationHistoryEditorInfo(IMemento memento) {
        this.memento = memento;
    }

    boolean isPersistable() {
        if (editorInput != null) {
            IPersistableElement persistable = editorInput.getPersistable();
            return persistable != null;
        }
        return memento != null;
    }

    void handlePartClosed() {
        if (!isPersistable()) {
			return;
		}
        if (memento == null) {
            IPersistableElement persistable = editorInput.getPersistable();
            memento = XMLMemento
                    .createWriteRoot(IWorkbenchConstants.TAG_EDITOR);
            memento.putString(IWorkbenchConstants.TAG_ID, editorID);
            memento.putString(IWorkbenchConstants.TAG_FACTORY_ID, persistable
                    .getFactoryId());
            persistable.saveState(memento);
        }
        editorID = null;
        editorInput = null;
    }

    void restoreEditor() {
        if (memento == null) {
			return;
		}
        String factoryID = memento
                .getString(IWorkbenchConstants.TAG_FACTORY_ID);
        IElementFactory factory = PlatformUI.getWorkbench().getElementFactory(
                factoryID);
        if (factory != null) {
            IAdaptable element = factory.createElement(memento);
            if (element instanceof IEditorInput) {
                editorInput = (IEditorInput) element;
                editorID = memento.getString(IWorkbenchConstants.TAG_ID);
            }
        }
        memento = null;
    }

    void saveState(IMemento mem) {
        if (editorInput != null) {
            IPersistableElement persistable = editorInput.getPersistable();
            mem.putString(IWorkbenchConstants.TAG_ID, editorID);
            mem.putString(IWorkbenchConstants.TAG_FACTORY_ID, persistable
                    .getFactoryId());
            persistable.saveState(mem);
        } else if (memento != null) {
            mem.putMemento(memento);
        }
    }
}
