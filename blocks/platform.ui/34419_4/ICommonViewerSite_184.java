
package org.eclipse.ui.navigator;

import org.eclipse.swt.widgets.Item;

public interface ICommonViewerMapper {
	
	public void addToMap(Object element, Item item);

	public void removeFromMap(Object element, Item item);

	public void clearMap();

	public boolean isEmpty();
	
	public boolean handlesObject(Object object);
		
	public void objectChanged(Object object);
	
	
	

	
}
