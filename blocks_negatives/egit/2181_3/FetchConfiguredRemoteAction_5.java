		Job job = new Job(

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				final FetchResult result;
				Exception fetchException = null;
				try {
					result = transport.fetch(new EclipseGitProgressTransformer(
							monitor), config.getFetchRefSpecs());
				} catch (final NotSupportedException e) {
					fetchException = e;
					return new Status(IStatus.ERROR, Activator.getPluginId(),
							UIText.FetchWizard_fetchNotSupported, e);
				} catch (final TransportException e) {
					if (monitor.isCanceled())
						return Status.CANCEL_STATUS;
					fetchException = e;
					return new Status(IStatus.ERROR, Activator.getPluginId(),
							UIText.FetchWizard_transportError, e);
				} finally {
					if (fetchException != null)
						Activator.handleError(fetchException.getMessage(),
								fetchException, shell != null);
				}
				if (shell != null) {
					PlatformUI.getWorkbench().getDisplay().asyncExec(
							new Runnable() {
								public void run() {
									Dialog dialog = new FetchResultDialog(
											shell, repository, result,
											repository.getDirectory()
													.getParentFile().getName()
									dialog.open();
								}
							});
				}
				return Status.OK_STATUS;
			}
