			getWizard().getContainer().run(true, true,
					new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							int totalWork = 1;
							if (doCheckout)
								totalWork++;
							if (doCreateTag || doCreateBranch)
								totalWork++;
							monitor.beginTask(
									UIText.FetchGerritChangePage_GetChangeTaskName,
									totalWork);
