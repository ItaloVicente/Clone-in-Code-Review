package org.eclipse.ui;

public interface IMarkerResolutionRelevance {

	default public int getRelevanceForResolution() {
		return 0;
	}
}
