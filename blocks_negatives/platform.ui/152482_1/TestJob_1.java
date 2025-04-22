			MultiStatus result = new MultiStatus(
					"org.eclipse.ui.examples.jobs", 1, "This is the MultiStatus message", new RuntimeException("This is the MultiStatus exception")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			result
					.add(new Status(
							IStatus.ERROR,
							"org.eclipse.ui.examples.jobs", 1, "This is the child status message", new RuntimeException("This is the child exception"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
