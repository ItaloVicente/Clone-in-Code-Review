
package org.eclipse.ui.examples.readmetool;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

public class ReadmeMarkerResolutionGenerator implements
        IMarkerResolutionGenerator2 {

    @Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
        return new IMarkerResolution[] { new AddSentenceResolution() };
    }

    @Override
	public boolean hasResolutions(IMarker marker) {
        return true;
    }

}
