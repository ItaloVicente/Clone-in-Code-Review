		int code = System.identityHashCode(referenceImageOrDescriptor);
		for (ImageDescriptor overlay : overlays) {
			if (overlay != null) {
				code ^= overlay.hashCode();
			}
		}
		return code;
