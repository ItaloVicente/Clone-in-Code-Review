	private void initResourceTracking() {
		final String property = System.getProperty("org.eclipse.swt.graphics.Resource.reportNonDisposed"); //$NON-NLS-1$
		if ("stacks".equalsIgnoreCase(property)) { //$NON-NLS-1$
			NonDisposedReporter reporter = createNonDisposedReporter();
			Resource.trackNonDisposed(reporter != null, reporter);
		}
	}

	public NonDisposedReporter createNonDisposedReporter() {
		return new IDENonDisposedReporter();
	}

	private static class IDENonDisposedReporter implements NonDisposedReporter {

		@Override
		public void onNonDisposedResource(Resource resource, NonDisposedException allocationStack) {
			IDEWorkbenchPlugin.log(null,
					StatusUtil.newStatus(IStatus.ERROR, "Not properly disposed SWT resource: " + resource, //$NON-NLS-1$
							allocationStack));
		}

	}

