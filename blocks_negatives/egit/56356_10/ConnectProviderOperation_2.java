		IProgressMonitor monitor;
		if (m == null) {
			monitor = new NullProgressMonitor();
		} else {
			monitor = m;
		}

		monitor.beginTask(CoreText.ConnectProviderOperation_connecting,
				100 * projects.size());
