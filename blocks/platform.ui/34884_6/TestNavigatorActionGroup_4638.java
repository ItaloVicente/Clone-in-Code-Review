package org.eclipse.ui.tests.adaptable;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.internal.ide.IMarkerImageProvider;

public class TestMarkerImageProvider implements IMarkerImageProvider {
    @Override
	public String getImagePath(IMarker marker) {
        return "icons/anything.gif"; //$NON-NLS-1$
    }
}
