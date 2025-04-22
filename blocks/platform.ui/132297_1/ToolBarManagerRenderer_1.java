		AtomicReference<IEclipseContext> currentContext = new AtomicReference<>();
		Runnable updateRunnable = () -> {
			try {
				visibleWhenTrackings.forEach((record, currentContextRef) -> {
					IEclipseContext c = currentContextRef.get();
					if (c != null) {
						record.updateVisibility(c);
						record.getManagerForModel().update(false);
					}
				});
				getUpdater().updateContributionItems(ALL_SELECTOR);
			} finally {
				currentContext.set(null);
			}
		};
		Throttler throttler = new Throttler(Display.getDefault(), Duration.ofMillis(200), updateRunnable);
