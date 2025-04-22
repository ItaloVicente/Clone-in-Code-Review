
package org.eclipse.ui.views.markers.internal;

import java.util.ArrayList;
import java.util.Collection;

class MarkerNodeRefreshRecord{
	Collection removedMarkers;
	Collection addedMarkers;
	Collection changedMarkers;
	
	MarkerNodeRefreshRecord(Collection removed, Collection added, Collection changed){
		removedMarkers = new ArrayList(removed);
		addedMarkers =  new ArrayList(added);
		changedMarkers =  new ArrayList(changed);
	}

	public void remove(MarkerNode node) {
		removedMarkers.add(node);
		
	}
	
	public void add(MarkerNode node) {
		addedMarkers.add(node);
		
	}
}
