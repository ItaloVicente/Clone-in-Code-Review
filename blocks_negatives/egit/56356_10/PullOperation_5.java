		IProgressMonitor monitor;
		if (m == null)
			monitor = new NullProgressMonitor();
		else
			monitor = m;
		monitor.beginTask(NLS.bind(CoreText.PullOperation_TaskName, Integer
				.valueOf(repositories.length)), repositories.length * 2);
