	public void refreshViewers() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				unstagedTableViewer.refresh();
				stagedTableViewer.refresh();
				updateSectionText();
			}
		});
	}

