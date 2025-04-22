package org.eclipse.ui;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.resource.ImageDescriptor;

public interface IEditorRegistry {

    public static final int PROP_CONTENTS = 0x01;

    public static final String SYSTEM_EXTERNAL_EDITOR_ID = "org.eclipse.ui.systemExternalEditor"; //$NON-NLS-1$

    public static final String SYSTEM_INPLACE_EDITOR_ID = "org.eclipse.ui.systemInPlaceEditor"; //$NON-NLS-1$

    public void addPropertyListener(IPropertyListener listener);

    public IEditorDescriptor findEditor(String editorId);

    @Deprecated
	public IEditorDescriptor getDefaultEditor();

    public IEditorDescriptor getDefaultEditor(String fileName);
    
    public IEditorDescriptor getDefaultEditor(String fileName, IContentType contentType);

    public IEditorDescriptor[] getEditors(String fileName);
 
    public IEditorDescriptor[] getEditors(String fileName, IContentType contentType);

    public IFileEditorMapping[] getFileEditorMappings();

    public ImageDescriptor getImageDescriptor(String filename);
	
    public ImageDescriptor getImageDescriptor(String filename, IContentType contentType);

    public void removePropertyListener(IPropertyListener listener);

    public void setDefaultEditor(String fileNameOrExtension, String editorId);

    public boolean isSystemInPlaceEditorAvailable(String filename);

    public boolean isSystemExternalEditorAvailable(String filename);

    public ImageDescriptor getSystemExternalEditorImageDescriptor(
            String filename);
}
