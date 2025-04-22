									getShell().getDisplay().asyncExec(
											new Runnable() {

												@Override
												public void run() {
													PushResultDialog dlg = new PushResultDialog(
															getShell(),
															repository,
															result,
															op
													.getDestinationString(),
													true);
													dlg.showConfigureButton(false);
													dlg.open();
												}
											});
