package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;

public interface IWorkingSet extends IPersistableElement, IAdaptable {
    public IAdaptable[] getElements();

    public String getId();

    @Deprecated
	public ImageDescriptor getImage();
    
    public ImageDescriptor getImageDescriptor();

    public String getName();

	public void setElements(IAdaptable[] elements);

    public void setId(String id);

    public void setName(String name);
    
    public boolean isEditable();
 
	public boolean isVisible();
    
    public String getLabel();
    
	public void setLabel(String label);
	
	public boolean isSelfUpdating();
	
	public boolean isAggregateWorkingSet();	
	
	public boolean isEmpty();
	
	public IAdaptable[] adaptElements(IAdaptable[] objects);
}
