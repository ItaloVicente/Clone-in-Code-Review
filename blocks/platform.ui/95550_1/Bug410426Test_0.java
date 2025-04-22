		LogListener logListener = entry -> {
			if (entry.getLevel() == LogService.LOG_ERROR && entry.getException() instanceof ClassCastException
					&& entry.getException().getMessage()
							.contains("MenuManager cannot be cast to org.eclipse.jface.action.ContributionItem")) { // $NON-NLS-N$
				cces.add((ClassCastException) entry.getException());
			}
		};
		LogFilter logFilter = (bundle, loggerName, logLevel) -> logLevel == LogService.LOG_ERROR && loggerName == null
				&& "org.eclipse.equinox.event".equals(bundle.getSymbolicName());
