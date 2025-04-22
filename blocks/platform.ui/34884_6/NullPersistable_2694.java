package org.eclipse.ui.internal.part;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.internal.EditorReference;

public class NullEditorInput implements IEditorInput {

	private EditorReference editorReference;

	public NullEditorInput() {
	}

	public NullEditorInput(EditorReference editorReference) {
		Assert.isLegal(editorReference != null);
		this.editorReference= editorReference;

	}

    @Override
	public boolean exists() {
        return false;
    }

    @Override
	public ImageDescriptor getImageDescriptor() {
        return ImageDescriptor.getMissingImageDescriptor();
    }

    @Override
	public String getName() {
		String result = null;
		if (editorReference != null) {
			result = editorReference.getName();
		}
		if (result != null) {
			return result;
		}
        return ""; //$NON-NLS-1$
    }

    @Override
	public IPersistableElement getPersistable() {
        return null;
    }

    @Override
	public String getToolTipText() {
		if (editorReference != null)
			return editorReference.getTitleToolTip();
        return ""; //$NON-NLS-1$
    }

    @Override
	public Object getAdapter(Class adapter) {
        return null;
    }

}
