		exercise(() -> {
			startMeasuring();
			for (int j = 0; j < 10; j++) {
				IEditorPart part;
				try {
					part = IDE.openEditor(activePage, file, true);
				} catch (PartInitException e) {
					throw new AssertionError("Can't open editor for " + file.getName());
