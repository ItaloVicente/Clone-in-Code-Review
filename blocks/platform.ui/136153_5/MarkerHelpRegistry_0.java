package org.eclipse.ui;

import org.eclipse.core.resources.IMarker;

public interface IMarkerHelpContextProvider {

	public String getHelpContextForMarker(IMarker marker);

	public boolean hasHelpContextForMarker(IMarker marker);
}
