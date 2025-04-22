		desc.id = element.getAttribute(ATT_ID);
		desc.markerType = element.getAttribute(ATT_MARKER_TYPE);
		desc.imagePath = element.getAttribute(ATT_ICON);
		desc.className = element.getAttribute(ATT_PROVIDER_CLASS);
		if (desc.imagePath != null) {
			desc.imageDescriptor = getImageDescriptor(desc);
		}
		if (desc.className == null) {
			desc.element = null;
			desc.pluginBundle = null;
		}
		descriptors.add(desc);
	}

	public ImageDescriptor getImageDescriptor(IMarker marker) {
		int size = descriptors.size();
		for (int i = 0; i < size; i++) {
			Descriptor desc = descriptors.get(i);
			try {
				if (marker.isSubtypeOf(desc.markerType)) {
					if (desc.className != null) {
						if (desc.pluginBundle.getState()==Bundle.ACTIVE) {
							if (desc.provider == null) {
