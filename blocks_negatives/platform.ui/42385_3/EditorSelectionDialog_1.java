			Cursor busy = new Cursor(shell.getDisplay(), SWT.CURSOR_WAIT);
			shell.setCursor(busy);
			EditorRegistry reg = (EditorRegistry) WorkbenchPlugin.getDefault()
					.getEditorRegistry();
			externalEditors = reg.getSortedEditorsFromOS();
			externalEditors = filterEditors(externalEditors);
			shell.setCursor(null);
			busy.dispose();
