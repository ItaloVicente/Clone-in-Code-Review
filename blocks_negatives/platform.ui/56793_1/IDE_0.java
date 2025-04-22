		IGotoMarker gotoMarker = null;
		if (editor instanceof IGotoMarker) {
			gotoMarker = (IGotoMarker) editor;
		} else {
			gotoMarker = editor.getAdapter(IGotoMarker.class);
		}
