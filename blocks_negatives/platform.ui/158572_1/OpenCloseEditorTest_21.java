		exercise(new TestRunnable() {
			@Override
			public void run() throws Exception {
				startMeasuring();
				for (int j = 0; j < 10; j++) {
					IEditorPart part = IDE.openEditor(activePage, file, true);
					processEvents();
					activePage.closeEditor(part, false);
					processEvents();

