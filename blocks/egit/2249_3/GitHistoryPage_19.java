		});
		job = rj;
		if (trace)
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.HISTORYVIEW.getLocation(),
					"Scheduling GenerateHistoryJob"); //$NON-NLS-1$
		schedule(rj);
	}

	private void schedule(final Job j) {
		final IWorkbenchPartSite site = getWorkbenchSite();
		if (site != null) {
			final IWorkbenchSiteProgressService p;
			p = (IWorkbenchSiteProgressService) site
					.getAdapter(IWorkbenchSiteProgressService.class);
			if (p != null) {
				p.schedule(j, 0, true /* use half-busy cursor */);
				return;
