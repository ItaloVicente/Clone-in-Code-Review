		switch (c.getState()) {
		case ADDED:
			return getDecoratedImage(getEditorImage(c), UIIcons.OVR_STAGED_ADD);
		case CHANGED:
			return getDecoratedImage(getEditorImage(c), UIIcons.OVR_DIRTY);
		case REMOVED:
			return getDecoratedImage(getEditorImage(c),
					UIIcons.OVR_STAGED_REMOVE);
		case MISSING:
			return getDecoratedImage(getEditorImage(c),
					UIIcons.OVR_STAGED_REMOVE);
		case MODIFIED:
			return getEditorImage(c);
		case PARTIALLY_MODIFIED:
			return getDecoratedImage(getEditorImage(c), UIIcons.OVR_DIRTY);
		case CONFLICTING:
			return getDecoratedImage(getEditorImage(c), UIIcons.OVR_CONFLICT);
		case UNTRACKED:
			return getDecoratedImage(getEditorImage(c), UIIcons.OVR_UNTRACKED);
		default:
			return getEditorImage(c);
		}
