			@Override
			public void run() {
				if (editorTable.isDisposed()) {
					return;
				}
				fillEditorTable();
				updateEnableState();
			}
		});
