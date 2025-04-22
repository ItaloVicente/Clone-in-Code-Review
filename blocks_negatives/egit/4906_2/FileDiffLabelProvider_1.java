			switch (c.getChange()) {
			case ADD:
				return getDecoratedImage(getEditorImage(c),
						UIIcons.OVR_STAGED_ADD);
			case DELETE:
				return getDecoratedImage(getEditorImage(c),
						UIIcons.OVR_STAGED_REMOVE);
			case COPY:
			case RENAME:
			case MODIFY:
				return getEditorImage(c);
			}
