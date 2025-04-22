
package org.eclipse.ui.views.markers.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.tasklist.ITaskListResourceAdapter;

class DefaultMarkerResourceAdapter implements ITaskListResourceAdapter {

    private static ITaskListResourceAdapter singleton;

    DefaultMarkerResourceAdapter() {
        super();
    }

    static ITaskListResourceAdapter getDefault() {
        if (singleton == null) {
			singleton = new DefaultMarkerResourceAdapter();
		}
        return singleton;
    }

    @Override
	public IResource getAffectedResource(IAdaptable adaptable) {
        IResource resource = (IResource) adaptable.getAdapter(IResource.class);

        if (resource == null) {
			return (IFile) adaptable.getAdapter(IFile.class);
		} else {
			return resource;
		}
    }
}
