
		if (this.navigatorTree != null) {
			runWithInlineEditor();
		} else {
			if (LTKLauncher.openRenameWizard(null, getStructuredSelection())) {
				return;
			}
