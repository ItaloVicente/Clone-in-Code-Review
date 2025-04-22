
package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.internal.navigator.resources.workbench.ResourceExtensionContentProvider;

public class TestContentProviderResource extends
		ResourceExtensionContentProvider {

	public static boolean _returnBadObject;

	public static void resetTest() { 
		_returnBadObject = false;
	}
	
	public Object[] getChildren(Object parentElement) {

		if (_returnBadObject && parentElement instanceof IProject)
			return new Object[] { new Object(), new Object(), new Object() };
		return super.getChildren(parentElement);

	}

}
