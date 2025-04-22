
package org.eclipse.ui.internal.navigator.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class SkeletonViewerFilter extends ViewerFilter {

	public static final SkeletonViewerFilter INSTANCE = new SkeletonViewerFilter();

	private SkeletonViewerFilter() {
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		return true;
	}

}
