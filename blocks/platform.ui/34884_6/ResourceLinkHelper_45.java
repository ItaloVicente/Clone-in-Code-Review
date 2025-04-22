
package org.eclipse.ui.internal.navigator.resources.workbench;



import org.eclipse.core.resources.IResource;
import org.eclipse.ui.views.navigator.ResourceSorter;

import com.ibm.icu.text.Collator;

public class ResourceExtensionSorter extends ResourceSorter {

	private Collator icuCollator;

	public ResourceExtensionSorter() {
		super(ResourceSorter.NAME);
		icuCollator = Collator.getInstance();
	}
	
    @Override
	protected int compareNames(IResource resource1, IResource resource2) {
    	return icuCollator.compare(resource1.getName(), resource2.getName());
    }

	
}
