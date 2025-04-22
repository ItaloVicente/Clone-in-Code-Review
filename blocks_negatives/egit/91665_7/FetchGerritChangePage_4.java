			};
			job.setUser(true);
			job.schedule();
			return true;
		} else {
			try {
			getWizard().getContainer().run(true, true,
					new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							try {
								internalDoFetch(spec, uri, doCheckout,
											doCreateTag, doChangeBranch,
											doCreateBranch,
										doCheckoutNewBranch, doActivateAdditionalRefs,
										textForTag, textForBranch, monitor);
							} catch (RuntimeException e) {
								throw e;
							} catch (Exception e) {
								throw new InvocationTargetException(e);
							} finally {
								monitor.done();
							}
						}
					});
			} catch (InvocationTargetException e) {
				Activator.handleError(e.getCause().getMessage(), e.getCause(),
						true);
				return false;
			} catch (InterruptedException e) {
