				if (resetType == ResetType.HARD)
					if (!MessageDialog
							.openQuestion(
									getShell(),
									UIText.ResetTargetSelectionDialog_ResetQuestion,
									UIText.ResetTargetSelectionDialog_ResetConfirmQuestion))
						return true;

				try {
					getContainer().run(true, true,
							new IRunnableWithProgress() {
								@Override
								public void run(IProgressMonitor monitor)
										throws InvocationTargetException,
										InterruptedException {

									String jobname = NLS.bind(
											UIText.ResetAction_reset,
											targetBranch);
									final ResetOperation operation = new ResetOperation(
											node.getRepository(), targetBranch,
											resetType);
									JobUtil.scheduleUserWorkspaceJob(operation,
											jobname, JobFamilies.RESET);
								}
							});
				} catch (InvocationTargetException ite) {
					Activator.handleError(
							UIText.ResetCommand_ResetFailureMessage, ite
									.getCause(), true);
					return false;
				} catch (InterruptedException ie) {
				}
