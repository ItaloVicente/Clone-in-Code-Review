		Runnable updateRunnable = () -> {
			visibleWhenTrackings.forEach((record, currentContextRef) -> {
				IEclipseContext c = currentContextRef.get();
				if (c != null) {
					System.out.println("  updating " + record.toolbarContribution); //$NON-NLS-1$
					record.updateVisibility(c);
					record.getManagerForModel().update(false);
				}
			});
			getUpdater().updateContributionItems(ALL_SELECTOR);
		};
		Throttler throttler = new Throttler(Display.getDefault(), Duration.ofMillis(200), updateRunnable);
