		if (selection.isEmpty()) {
			return;
		}
		List list = selection.toList();
		IMarker[] markers = new IMarker[list.size()];
		list.toArray(markers);
