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

	@Override
	public boolean setInput(Object object) {
		try {
			trace = GitTraceLocation.HISTORYVIEW.isActive();
			if (trace)
				GitTraceLocation.getTrace().traceEntry(
						GitTraceLocation.HISTORYVIEW.getLocation(), object);

			if (object == getInput())
				return true;
			this.input = null;
			return super.setInput(object);
		} finally {
			if (trace)
				GitTraceLocation.getTrace().traceExit(
						GitTraceLocation.HISTORYVIEW.getLocation());
		}
	}

	@Override
