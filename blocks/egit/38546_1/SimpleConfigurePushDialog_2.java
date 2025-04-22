									getShell().getDisplay().asyncExec(
											new Runnable() {

												public void run() {
													PushResultDialog dlg = new PushResultDialog(
															getShell(),
															repository,
															result,
															op
