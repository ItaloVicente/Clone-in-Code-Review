	private void schedule(final Job j) {
		IWorkbenchPartSite site = getSite();
		if (site != null) {
			final IWorkbenchSiteProgressService p;
			p = (IWorkbenchSiteProgressService) site
					.getAdapter(IWorkbenchSiteProgressService.class);
			if (p != null) {
				p.schedule(j, 0, true /* use half-busy cursor */);
				return;
			}
		}
		j.schedule();
	}

	private IndexDiff doReload(final Repository repository, IProgressMonitor monitor, String jobTitle) {
