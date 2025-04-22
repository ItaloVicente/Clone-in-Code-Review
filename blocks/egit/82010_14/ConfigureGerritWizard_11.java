package org.eclipse.egit.ui.internal.fetch;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.TrackingRefUpdate;

class TrackingRefUpdateContentProvider implements IStructuredContentProvider {
	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement == null)
			return new TrackingRefUpdate[0];

		final FetchResult result = (FetchResult) inputElement;
		return result.getTrackingRefUpdates().toArray(new TrackingRefUpdate[0]);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}
