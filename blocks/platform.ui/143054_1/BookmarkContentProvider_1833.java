		getMarkerDeltas(delta, additions, removals, changes);

		if (additions.size() + removals.size() + changes.size() > 0) {
			viewer.getControl().getDisplay().asyncExec(() -> {
				Control ctrl = viewer.getControl();
				if (ctrl == null || ctrl.isDisposed()) {
