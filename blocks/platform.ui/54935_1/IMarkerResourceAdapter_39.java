
package org.eclipse.ui.views.markers.internal;

import java.util.List;

public interface IMarkerChangedListener {

    public void markerChanged(List additions, List removals, List changes);

}
