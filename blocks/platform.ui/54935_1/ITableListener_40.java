
package org.eclipse.ui.views.markers.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;

public interface IMarkerResourceAdapter {

    public IResource getAffectedResource(IAdaptable adaptable);

}
