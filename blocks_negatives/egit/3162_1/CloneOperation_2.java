			try {
				doInit(new SubProgressMonitor(monitor, 100));
				doFetch(new SubProgressMonitor(monitor, 4000));
				doCheckout(new SubProgressMonitor(monitor, 900));
			} finally {
				closeLocal();
