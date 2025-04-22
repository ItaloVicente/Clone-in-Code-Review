
package org.eclipse.ui.views.markers.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ide.undo.UpdateMarkersOperation;

public class ActionMarkCompleted extends MarkerSelectionProviderAction {

	public ActionMarkCompleted(ISelectionProvider provider) {
		super(provider, MarkerMessages.markCompletedAction_title);
		setEnabled(false);
	}

	@Override
	public void run() {
		IMarker[] markers = getSelectedMarkers();
		Map attrs = new HashMap();
		attrs.put(IMarker.DONE, Boolean.TRUE);
		IUndoableOperation op = new UpdateMarkersOperation(markers, attrs,
				getText(), true);
		execute(op, getText(), null, null);

	}

	@Override
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(false);
		if (selection == null || selection.isEmpty()) {
			return;
		}
		for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
			Object obj = iterator.next();
			if (!(obj instanceof ConcreteMarker)) {
				return;
			}
			IMarker marker = ((ConcreteMarker) obj).getMarker();
			if (!marker.getAttribute(IMarker.USER_EDITABLE, true)) {
				return;
			}
			if (marker.getAttribute(IMarker.DONE, false)) {
				return;
			}
		}
		setEnabled(true);
	}
}
