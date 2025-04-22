		if (launchFetch && fetchJob != null)
			try {
				fetchJob.join();
			} catch (InterruptedException e) {
				Activator.logError("Fetch operation interupted", e); //$NON-NLS-1$
			}

