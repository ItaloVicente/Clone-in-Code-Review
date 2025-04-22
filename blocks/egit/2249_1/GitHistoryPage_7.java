	public void refresh() {
		this.input = null;
		inputSet();
	}

	public void setCompareMode(boolean compareMode) {
		store.setValue(UIPreferences.RESOURCEHISTORY_COMPARE_MODE, compareMode);
	}

	public ISelectionProvider getSelectionProvider() {
		return graph.getTableView();
	}

	public void onRefsChanged(final RefsChangedEvent e) {
		if (input == null || e.getRepository() != input.getRepository())
			return;
	
		if (getControl().isDisposed())
			return;
	
		synchronized (this) {
			if (refschangedRunnable == null) {
				refschangedRunnable = new Runnable() {
					public void run() {
						if (!getControl().isDisposed()) {
							if (GitTraceLocation.HISTORYVIEW.isActive())
								GitTraceLocation
										.getTrace()
										.trace(
												GitTraceLocation.HISTORYVIEW
														.getLocation(),
												"Executing async repository changed event"); //$NON-NLS-1$
							refschangedRunnable = null;
							initAndStartRevWalk(true);
						}
					}
				};
				getControl().getDisplay().asyncExec(refschangedRunnable);
			}
		}
	}

