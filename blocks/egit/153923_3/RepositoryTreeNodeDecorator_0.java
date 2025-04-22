	private void decorateConflict(File file, IDecoration decoration) {
		IResourceState state = ResourceStateFactory.getInstance().get(file);
		if (state.hasConflicts()) {
			decoration.addOverlay(UIIcons.OVR_CONFLICT);
		}
	}

