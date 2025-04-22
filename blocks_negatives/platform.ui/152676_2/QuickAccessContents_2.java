			final String finalFilter = filter;
			for (int providerIndex = 0; providerIndex < providers.length && (showAllMatches || countTotal < maxCount)
					&& !aMonitor.isCanceled(); providerIndex++) {
				if (aMonitor.isCanceled()) {
					break;
				}
				if (entries[providerIndex] == null) {
					entries[providerIndex] = new ArrayList<>();
					indexPerProvider[providerIndex] = 0;
				}
				int count = 0;
				QuickAccessProvider provider = providers[providerIndex];
				boolean isPreviousPickProvider = (provider instanceof PreviousPicksProvider);
				if (category != null && !category.equalsIgnoreCase(provider.getName()) && !isPreviousPickProvider) {
					continue;
				}
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
						job.setPriority(Job.INTERACTIVE);
						job.schedule();
						try {
							job.join(0, new NullProgressMonitor());
						} catch (Exception e) {
							WorkbenchPlugin.log(e);
