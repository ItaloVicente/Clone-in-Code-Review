        try {
            monitor.beginTask("", 2000);//$NON-NLS-1$

            projectHandle.create(new SubProgressMonitor(monitor, 1000));
            if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
