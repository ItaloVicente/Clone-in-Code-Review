
	public StyledString getStyledText(Object element) {
		StyledString styled = new StyledString();
		final StagingEntry c = (StagingEntry) element;
		if (c.getState() == StagingEntry.State.MODIFIED
				|| c.getState() == StagingEntry.State.PARTIALLY_MODIFIED)
			styled.append('>').append(' ');
		if (fileNameMode) {
			IPath parsed = Path.fromOSString(c.getPath());
			if (parsed.segmentCount() > 1) {
				styled.append(parsed.lastSegment());
				styled.append(' ');
				styled.append('-', StyledString.QUALIFIER_STYLER);
				styled.append(' ');
				styled.append(parsed.removeLastSegments(1).toString(),
						StyledString.QUALIFIER_STYLER);
			} else
				styled.append(c.getPath());
		} else
			styled.append(c.getPath());

		return styled;
	}

	public Image getImage(Object element) {
		final StagingEntry c = (StagingEntry) element;
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
	}
