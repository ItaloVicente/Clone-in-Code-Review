						double cutoff = 0.95;
						int expectedProgressCount = Math.max(1, WorkbenchPlugin.getDefault()
								.getBundleCount() / 10);
						progressMonitor.beginTask("", expectedProgressCount); //$NON-NLS-1$
						bundleListener = workbench.new StartupProgressBundleListener(
								progressMonitor, (int) (expectedProgressCount * cutoff));
