				IRunnableWithProgress r = monitor -> {
					monitor.beginTask(Messages.searchingTaskName, IProgressMonitor.UNKNOWN);
					search(rootDir, existingPaths, foundBrowsers, new HashSet<String>(), monitor);
					monitor.done();
				};

				try {
					pm.run(true, true, r);
				} catch (InvocationTargetException ex) {
					Trace.trace(Trace.SEVERE,
									+ ex);
				} catch (InterruptedException ex) {
					Trace.trace(Trace.SEVERE,
									+ ex);
					return;
				}

				if (pm.getProgressMonitor().isCanceled())
					return;
