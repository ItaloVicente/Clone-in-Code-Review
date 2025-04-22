package org.eclipse.ui.internal.decorators;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.IDecorationContext;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.internal.WorkbenchMessages;

class DecorationReference {
    Object element;

    Object adaptedElement;

    String undecoratedText;

    boolean forceUpdate = false;

	IDecorationContext[] contexts;

    DecorationReference(Object object, Object adaptedObject, IDecorationContext context) {
        this.contexts = new IDecorationContext[] { context} ;
		Assert.isNotNull(object);
        element = object;
        this.adaptedElement = adaptedObject;
    }

    Object getAdaptedElement() {
        return adaptedElement;
    }

    Object getElement() {
        return element;
    }

    boolean shouldForceUpdate() {
        return forceUpdate;
    }

    void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    void setUndecoratedText(String text) {
        undecoratedText = text;
    }

    String getSubTask() {
        if (undecoratedText == null) {
			return WorkbenchMessages.DecorationReference_EmptyReference;
		}
	return NLS.bind(WorkbenchMessages.DecorationScheduler_DecoratingSubtask, undecoratedText );
    }

	IDecorationContext[] getContexts() {
		return contexts;
	}

	void addContext(IDecorationContext context) {
		IDecorationContext[] newContexts = new IDecorationContext[contexts.length + 1];
		System.arraycopy(contexts, 0, newContexts, 0, contexts.length);
		newContexts[contexts.length] = context;
		contexts = newContexts;
	}
}
