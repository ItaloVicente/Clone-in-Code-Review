									CheckoutCommand co = new Git(repository)
											.checkout();
									try {
										co.setName(textForBranch).call();
									} catch (CheckoutConflictException e) {
										final CheckoutResult result = co
												.getResult();

										if (result.getStatus() == Status.CONFLICTS) {
											final Shell shell = getWizard()
													.getContainer().getShell();

											shell.getDisplay().asyncExec(
													new Runnable() {
														public void run() {
															new CheckoutConflictDialog(
																	shell,
																	repository,
																	result.getConflictList())
																	.open();
														}
													});
										}
									}
