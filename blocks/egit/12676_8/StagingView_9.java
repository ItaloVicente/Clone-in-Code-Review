	public int getUnstagedPresentation() {
		return unstagedPresentation;
	}

	public int getStagedPresentation() {
		return stagedPresentation;
	}

	public void refreshViewers() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				Object[] unstagedExpanded = unstagedViewer
						.getExpandedElements();
				Object[] stagedExpanded = stagedViewer.getExpandedElements();
				unstagedViewer.refresh();
				stagedViewer.refresh();
				unstagedViewer.setExpandedElements(unstagedExpanded);
				stagedViewer.setExpandedElements(stagedExpanded);
			}
		});
	}

