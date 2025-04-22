	private ImageDescriptor addConflictDecorationIfNecessary(File file,
			ImageDescriptor base) {
		IResourceState state = ResourceStateFactory.getInstance().get(file);
		if (state.hasConflicts()) {
			return new DecorationOverlayDescriptor(base, UIIcons.OVR_CONFLICT,
					IDecoration.BOTTOM_RIGHT);
		}
		return base;
	}

