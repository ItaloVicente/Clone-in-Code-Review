			getTargetPart().getSite().getWorkbenchWindow().run(true, false,
					new IRunnableWithProgress() {
						public void run(IProgressMonitor arg0)
								throws InvocationTargetException,
								InterruptedException {
							try {
								op.execute(arg0);
							} catch (CoreException e) {
								throw new InvocationTargetException(e);
							}
						}
					});
		} catch (InvocationTargetException e) {
			Activator.handleError(UIText.Track_error, e, true);
		} catch (InterruptedException e) {
			Activator.handleError(UIText.Track_error, e, true);
		}
