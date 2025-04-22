        if (input == null || editorID == null) {
            throw new IllegalArgumentException();
        }

        final IEditorPart result[] = new IEditorPart[1];
        final PartInitException ex[] = new PartInitException[1];
		BusyIndicator.showWhile(legacyWindow.getWorkbench().getDisplay(),
                new Runnable() {
                    public void run() {
                        try {
