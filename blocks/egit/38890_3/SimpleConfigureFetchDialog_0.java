									final FetchResult result = op
											.execute(monitor);
									getShell().getDisplay().asyncExec(
											new Runnable() {

												public void run() {
													FetchResultDialog dlg;
													dlg = new FetchResultDialog(
															getShell(),
															repository,
															result,
															op.getSourceString());
													dlg.showConfigureButton(false);
													dlg.open();
												}
											});
