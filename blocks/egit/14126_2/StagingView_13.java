				unstagedViewer.setExpandedElements(unstagedExpanded);
				stagedViewer.setExpandedElements(stagedExpanded);
			}
		});
	}

	Object[] getExpandedUnstagedElements() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				expandedUnstagedElements = unstagedViewer.getExpandedElements();
			}
		});
		return expandedUnstagedElements;
	}

	Object[] getExpandedStagedElements() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				expandedStagedElements = stagedViewer.getExpandedElements();
			}
		});
		return expandedStagedElements;
	}

	void setExpandedUnstagedElements(final Object[] expandedElements) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				unstagedViewer.setExpandedElements(expandedElements);
			}
		});
	}

	void setExpandedStagedElements(final Object[] expandedElements) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				stagedViewer.setExpandedElements(expandedElements);
