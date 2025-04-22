
package org.eclipse.ui.internal;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PlatformUI;

public class EditorHistoryItem {

    private IEditorInput input;

    private IEditorDescriptor descriptor;

    private IMemento memento;

    public EditorHistoryItem(IEditorInput input, IEditorDescriptor descriptor) {
        this.input = input;
        this.descriptor = descriptor;
    }

    public EditorHistoryItem(IMemento memento) {
        this.memento = memento;
    }

    public IEditorDescriptor getDescriptor() {
        return descriptor;
    }

    public IEditorInput getInput() {
        return input;
    }

    public boolean isRestored() {
        return memento == null;
    }

    public String getName() {
        if (isRestored() && getInput() != null) {
            return getInput().getName();
        } else if (memento != null) {
            String name = memento.getString(IWorkbenchConstants.TAG_NAME);
            if (name != null) {
                return name;
            }
        }
        return ""; //$NON-NLS-1$
    }

    public String getToolTipText() {
        if (isRestored() && getInput() != null) {
            return getInput().getToolTipText();
        } else if (memento != null) {
            String name = memento.getString(IWorkbenchConstants.TAG_TOOLTIP);
            if (name != null) {
                return name;
            }
        }
        return ""; //$NON-NLS-1$
    }

    public boolean matches(IEditorInput input) {
        if (isRestored()) {
			return input.equals(getInput());
		}
        if (!getName().equals(input.getName())) {
			return false;
		}
        if (!getToolTipText().equals(input.getToolTipText())) {
			return false;
		}
        IPersistableElement persistable = input.getPersistable();
        String inputId = persistable == null ? null : persistable
                .getFactoryId();
        String myId = getFactoryId();
        return myId == null ? inputId == null : myId.equals(inputId);
    }

    public String getFactoryId() {
        if (isRestored()) {
            if (input != null) {
                IPersistableElement persistable = input.getPersistable();
                if (persistable != null) {
                    return persistable.getFactoryId();
                }
            }
        } else if (memento != null) {
            return memento.getString(IWorkbenchConstants.TAG_FACTORY_ID);
        }
        return null;
    }

    public IStatus restoreState() {
        Assert.isTrue(!isRestored());

        IStatus result = Status.OK_STATUS;
        IMemento memento = this.memento;
        this.memento = null;

        String factoryId = memento
                .getString(IWorkbenchConstants.TAG_FACTORY_ID);
        if (factoryId == null) {
            WorkbenchPlugin
                    .log("Unable to restore mru list - no input factory ID.");//$NON-NLS-1$
            return result;
        }
        IElementFactory factory = PlatformUI.getWorkbench().getElementFactory(
                factoryId);
        if (factory == null) {
            return result;
        }
        IMemento persistableMemento = memento
                .getChild(IWorkbenchConstants.TAG_PERSISTABLE);
        if (persistableMemento == null) {
            WorkbenchPlugin
                    .log("Unable to restore mru list - no input element state: " + factoryId);//$NON-NLS-1$
            return result;
        }
        IAdaptable adaptable = factory.createElement(persistableMemento);
        if (adaptable == null || (adaptable instanceof IEditorInput) == false) {
            return result;
        }
        input = (IEditorInput) adaptable;
        String editorId = memento.getString(IWorkbenchConstants.TAG_ID);
        if (editorId != null) {
            IEditorRegistry registry = WorkbenchPlugin.getDefault()
                    .getEditorRegistry();
            descriptor = registry.findEditor(editorId);
        }
        return result;
    }

    public boolean canSave() {
        return !isRestored()
                || (getInput() != null && getInput().getPersistable() != null);
    }

    public IStatus saveState(IMemento memento) {
        if (!isRestored()) {
            memento.putMemento(this.memento);
        } else if (input != null) {

            IPersistableElement persistable = input.getPersistable();
            if (persistable != null) {
                IMemento persistableMemento = memento
                        .createChild(IWorkbenchConstants.TAG_PERSISTABLE);
                persistable.saveState(persistableMemento);
                memento.putString(IWorkbenchConstants.TAG_FACTORY_ID,
                        persistable.getFactoryId());
                if (descriptor != null && descriptor.getId() != null) {
                    memento.putString(IWorkbenchConstants.TAG_ID, descriptor
                            .getId());
                }
                memento
                        .putString(IWorkbenchConstants.TAG_NAME, input
                                .getName());
                memento.putString(IWorkbenchConstants.TAG_TOOLTIP, input
                        .getToolTipText());
            }
        }
        return Status.OK_STATUS;
    }

}
