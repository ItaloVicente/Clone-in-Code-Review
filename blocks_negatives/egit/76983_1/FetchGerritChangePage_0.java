			getWizard().getContainer().run(true, true,
					new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							try {
								internalDoFetch(spec, uri, doCheckout,
