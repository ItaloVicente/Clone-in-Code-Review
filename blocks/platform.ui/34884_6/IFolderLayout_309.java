package org.eclipse.ui;

import org.eclipse.jface.resource.ImageDescriptor;

public interface IFileEditorMapping {
    public IEditorDescriptor getDefaultEditor();

    public IEditorDescriptor[] getEditors();

    public IEditorDescriptor[] getDeletedEditors();

    public String getExtension();

    public ImageDescriptor getImageDescriptor();

    public String getLabel();

    public String getName();
}
