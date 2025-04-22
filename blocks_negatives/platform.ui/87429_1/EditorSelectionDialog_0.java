		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				if (editorTable.isDisposed()) {
					return;
				}
				fillEditorTable();
				updateEnableState();
