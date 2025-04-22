package org.eclipse.ui.internal.views.markers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IMarker;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.part.MarkerTransfer;
import org.eclipse.ui.views.markers.MarkerViewHandler;

public class CopyMarkerDescriptionHandler extends MarkerViewHandler {
	static String createMarkersReport(final IMarker[] markers) {
		final String NEWLINE = System.getProperty("line.separator"); //$NON-NLS-1$

		final StringBuffer report = new StringBuffer();
		for (int i = 0; i < markers.length; i++) {
			if (i > 0) {
				report.append(NEWLINE);
			}
			Object message = markers[i].getAttribute(IMarker.MESSAGE, ""); //$NON-NLS-1$
			if (message != null) {
				report.append(message);
			}
		}
		return report.toString();
	}

	@Override
	public Object execute(final ExecutionEvent event) {
		final ExtendedMarkersView view = getView(event);
		if (view == null) {
			return null;
		}

		setClipboard(view);
		return this;
	}

	private void setClipboard(final ExtendedMarkersView view) {
		final IMarker[] markers = view.getSelectedMarkers();
		final String markerReport = createMarkersReport(markers);

		Object[] data = new Object[] { markers, markerReport };
		Transfer[] transferTypes = new Transfer[] { MarkerTransfer.getInstance(), TextTransfer.getInstance() };

		view.getClipboard().setContents(data, transferTypes);
	}
}
