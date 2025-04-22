		processEvents();
		fViewer = null;
		if (fShell != null) {
			fShell.dispose();
			fShell = null;
		}
		fRootElement = null;
		fModel = null;
