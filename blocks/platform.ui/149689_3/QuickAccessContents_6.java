				if (!filter.isEmpty() || isPreviousPickProvider || showAllMatches) {
					AtomicReference<QuickAccessElement[]> sortedElementRef = new AtomicReference<>();
					if (provider.requiresUiAccess()) {
						UIJob job = new UIJob(
								NLS.bind(QuickAccessMessages.QuickAccessContents_processingProviderInUI,
										provider.getName())) {
							@Override
							public IStatus runInUIThread(IProgressMonitor monitor) {
								sortedElementRef.set(provider.getElementsSorted(finalFilter, monitor));
								return Status.OK_STATUS;
							}
						};
						job.schedule();
						try {
							job.join(0, new NullProgressMonitor());
						} catch (Exception e) {
							WorkbenchPlugin.log(e);
						}
					} else {
						sortedElementRef.set(provider.getElementsSorted(filter, aMonitor));
					}
					QuickAccessElement[] sortedElements = sortedElementRef.get();
