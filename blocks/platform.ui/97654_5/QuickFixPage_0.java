package org.eclipse.ui;

public interface IMarkerRelevance {

	default public int getRelevanceForMarker() {
		return 0;
	}

}
