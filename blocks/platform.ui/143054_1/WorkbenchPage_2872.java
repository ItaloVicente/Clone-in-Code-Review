		final IEditorPart result[] = new IEditorPart[1];
		final PartInitException ex[] = new PartInitException[1];
		BusyIndicator.showWhile(legacyWindow.getWorkbench().getDisplay(), () -> {
			try {
				result[0] = busyOpenEditor(input, editorID, activate, matchFlags, editorState, notify);
			} catch (PartInitException e) {
				ex[0] = e;
			}
		});
		if (ex[0] != null) {
			throw ex[0];
		}
		return result[0];
	}
