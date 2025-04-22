		IWorkbenchPart activePart = null;
		if (window != null)
			activePart = window.getActivePage().getActivePart();

		if (launchFetch && fetchJob != null)
			try {
				fetchJob.join();
			} catch (InterruptedException e) {
				Activator.logError("Fetch operation interupted", e); //$NON-NLS-1$
			}
