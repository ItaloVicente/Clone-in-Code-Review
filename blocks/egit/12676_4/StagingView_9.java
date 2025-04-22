	public int getUnstagedPresentation() {
		return unstagedPresentation;
	}

	public int getStagedPresentation() {
		return stagedPresentation;
	}

	public void refreshViewers() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				unstagedViewer.refresh();
				stagedViewer.refresh();
			}
		});
	}

