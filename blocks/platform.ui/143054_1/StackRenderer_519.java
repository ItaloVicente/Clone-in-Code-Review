		editorList.getShell().addListener(SWT.Deactivate, event -> {
			editorList.getShell().getDisplay().asyncExec(() -> {
				if (!editorList.hasFocus()) {
					editorList.dispose();
				}
			});
		});
