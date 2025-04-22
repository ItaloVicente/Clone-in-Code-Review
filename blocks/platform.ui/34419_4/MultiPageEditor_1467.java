package org.eclipse.ui.part;

import java.util.Arrays;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class MultiEditorInput implements IEditorInput {

    IEditorInput input[];

    String editors[];

    public MultiEditorInput(String[] editorIDs, IEditorInput[] innerEditors) {
        Assert.isNotNull(editorIDs);
        Assert.isNotNull(innerEditors);
        editors = editorIDs;
        input = innerEditors;
    }

    public IEditorInput[] getInput() {
        return input;
    }

    public String[] getEditors() {
        return editors;
    }

    @Override
	public boolean exists() {
        return true;
    }

    @Override
	public ImageDescriptor getImageDescriptor() {
        return null;
    }

    @Override
	public String getName() {
        String name = ""; //$NON-NLS-1$
        for (int i = 0; i < (input.length - 1); i++) {
            name = name + input[i].getName() + "/"; //$NON-NLS-1$
        }
        name = name + input[input.length - 1].getName();
        return name;
    }

    @Override
	public IPersistableElement getPersistable() {
        return null;
    }

    @Override
	public String getToolTipText() {
        return getName();
    }

    @Override
	public Object getAdapter(Class adapter) {
        return null;
    }
    
    
    @Override
	public boolean equals(Object obj) {
        if (this == obj) {
			return true;
		}
        if (!(obj instanceof MultiEditorInput)) {
			return false;
		}
        MultiEditorInput other = (MultiEditorInput) obj;
        return Arrays.equals(this.editors, other.editors) && Arrays.equals(this.input, other.input);
    }
    
    
    @Override
	public int hashCode() {
        int hash = 0;
        for (int i = 0; i < editors.length; i++) {
            hash = hash * 37 + editors[i].hashCode();
        }
        for (int i = 0; i < input.length; i++) {
            hash = hash * 37 + input[i].hashCode();
        }
        return hash;
    }
}
