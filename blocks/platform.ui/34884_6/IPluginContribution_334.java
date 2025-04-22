package org.eclipse.ui;

public interface IPlaceholderFolderLayout {
	
    public void addPlaceholder(String viewId);
    
    public String getProperty(String id);
    
    public void setProperty(String id, String value);
}
