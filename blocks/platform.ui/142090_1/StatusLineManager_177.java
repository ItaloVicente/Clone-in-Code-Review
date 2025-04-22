		statusLine = null;

		IContributionItem items[] = getItems();
		for (IContributionItem item : items) {
			item.dispose();
		}
	}

	public Control getControl() {
		return statusLine;
	}

	protected IProgressMonitor getProgressMonitorDelegate() {
		return (IProgressMonitor) getControl();
	}

	@Override
