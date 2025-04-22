
	private class FetchJob extends Job {
		private final Transport transport;

		private final List<RefSpec> refSpecs;

		private final String sourceString;

		public FetchJob(final Transport transport,
				final List<RefSpec> refSpecs, final String sourceString) {
			super(NLS.bind(UIText.FetchWizard_jobName, sourceString));
			this.transport = transport;
			this.refSpecs = refSpecs;
			this.sourceString = sourceString;
		}

		@Override
		protected IStatus run(IProgressMonitor actMonitor) {
			IProgressMonitor monitor = actMonitor;
			if (monitor == null)
				monitor = new NullProgressMonitor();
			final FetchResult result;
			try {
				result = transport.fetch(new EclipseGitProgressTransformer(
						monitor), refSpecs);
			} catch (final NotSupportedException e) {
				return new Status(IStatus.ERROR, Activator.getPluginId(),
						UIText.FetchWizard_fetchNotSupported, e);
			} catch (final TransportException e) {
				if (monitor.isCanceled())
					return Status.CANCEL_STATUS;
				return new Status(IStatus.ERROR, Activator.getPluginId(),
						UIText.FetchWizard_transportError, e);
			}

			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					final Shell shell = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell();
					final Dialog dialog = new FetchResultDialog(shell, localDb,
							result, sourceString);
					dialog.open();
				}
			});
			return Status.OK_STATUS;
		}
	}
