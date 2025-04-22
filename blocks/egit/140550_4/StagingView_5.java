		syncExec(() -> {
		    Object[] unstagedExpanded = unstagedViewer.getVisibleExpandedElements();
		    Object[] stagedExpanded = stagedViewer.getVisibleExpandedElements();
		    setRedraw(false);
		    try {
			refreshViewersInternal();
			unstagedViewer.setExpandedElements(unstagedExpanded);
			stagedViewer.setExpandedElements(stagedExpanded);
		    } finally {
			setRedraw(true);
		    }
