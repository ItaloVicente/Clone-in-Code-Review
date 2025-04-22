		final ImageDescriptor base = UIUtils.getEditorImage(getPath());
		switch (getChange()) {
		case ADD:
			return new DecorationOverlayDescriptor(base,
					UIIcons.OVR_STAGED_ADD, IDecoration.BOTTOM_RIGHT);
		case DELETE:
			return new DecorationOverlayDescriptor(base,
					UIIcons.OVR_STAGED_REMOVE, IDecoration.BOTTOM_RIGHT);
		default:
			return base;
		}
