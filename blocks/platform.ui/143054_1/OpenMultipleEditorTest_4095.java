		for (int i = 0; i < 100; i++) {
			IFile file = getProject().getFile(i + "." + extension);
			IDE.openEditor(activePage, file, true);
			processEvents();
		}
		if (closeAll) {
			activePage.closeAllEditors(false);
		}
		else {
			IEditorPart [] parts = activePage.getEditors();
			for (IEditorPart part : parts) {
				activePage.closeEditor(part, false);
			}
		}
		stopMeasuring();
		commitMeasurements();
		assertPerformance();
	}
