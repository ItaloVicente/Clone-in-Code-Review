			final File rootDir = new File(path);
			ProgressMonitorDialog pm = new ProgressMonitorDialog(getShell());

			IRunnableWithProgress r = monitor -> {
				monitor.beginTask(Messages.searchingTaskName, IProgressMonitor.UNKNOWN);
				search(rootDir, existingPaths, foundBrowsers, new HashSet<String>(), monitor);
				monitor.done();
			};

			try {
				pm.run(true, true, r);
			} catch (InvocationTargetException ex) {
				Trace.trace(Trace.SEVERE, "Invocation Exception occured running monitor: " //$NON-NLS-1$
						+ ex);
			} catch (InterruptedException ex) {
				Trace.trace(Trace.SEVERE, "Interrupted exception occured running monitor: " //$NON-NLS-1$
						+ ex);
				return;
			}
