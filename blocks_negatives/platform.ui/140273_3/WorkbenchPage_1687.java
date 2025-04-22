	public IEditorPart openEditor(final IEditorInput input, final String editorID,
			final boolean activate, final int matchFlags, final IMemento editorState,
			final boolean notify) throws PartInitException {
        if (input == null || editorID == null) {
            throw new IllegalArgumentException();
        }

        final IEditorPart result[] = new IEditorPart[1];
        final PartInitException ex[] = new PartInitException[1];
		BusyIndicator.showWhile(legacyWindow.getWorkbench().getDisplay(),
				() -> {
					try {
						result[0] = busyOpenEditor(input, editorID, activate, matchFlags, editorState, notify);
					} catch (PartInitException e) {
						ex[0] = e;
					}
				});
        if (ex[0] != null) {
			throw ex[0];
