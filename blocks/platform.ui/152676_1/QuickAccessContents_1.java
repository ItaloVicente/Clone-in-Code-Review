			boolean isPreviousPickProvider = provider instanceof PreviousPicksProvider;
			if (category != null && !category.equalsIgnoreCase(provider.getName()) && !isPreviousPickProvider) {
				continue;
			}
			if (!filter.isEmpty() || isPreviousPickProvider || showAllMatches) {
				AtomicReference<List<QuickAccessElement>> sortedElementRef = new AtomicReference<>();
				if (provider.requiresUiAccess()) {
					UIJob job = new UIJob(
							NLS.bind(QuickAccessMessages.QuickAccessContents_processingProviderInUI,
									provider.getName())) {
						@Override
						public IStatus runInUIThread(IProgressMonitor monitor) {
							sortedElementRef.set(Arrays.asList(provider.getElementsSorted(finalFilter, monitor)));
							return Status.OK_STATUS;
