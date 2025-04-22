		super.partActivated(part);
		if (part instanceof IEditorPart) {
			updateActiveEditor();
			updateState();
		}
	}

	@Override
