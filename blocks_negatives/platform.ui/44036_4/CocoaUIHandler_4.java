			statusReporter
					.get()
					.report(new Status(
							IStatus.WARNING,
							CocoaUIProcessor.FRAGMENT_ID,
							"Unhandled menu type: " + item.getClass() + ": " + item), //$NON-NLS-1$ //$NON-NLS-2$ $NON-NLS-2$
