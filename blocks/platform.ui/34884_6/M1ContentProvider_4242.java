
package org.eclipse.ui.tests.navigator.m12;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.tests.navigator.m12.model.M1Project;

public class M1AdapterFactory implements IAdapterFactory {

	public Object getAdapter(Object object, Class adapterType) {
		if (object instanceof M1Project
				&& IProject.class.isAssignableFrom(adapterType)) {
			IResource res = ((M1Project) object).getResource();
			if (res instanceof IProject) {
				return res;
			}
		}
		return null;
	}

	public Class[] getAdapterList() {
		return new Class[] { IProject.class };
	}

}
            
