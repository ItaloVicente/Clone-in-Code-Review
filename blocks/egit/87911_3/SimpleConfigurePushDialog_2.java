									getShell().getDisplay().asyncExec(() -> {
										PushResultDialog dlg = new PushResultDialog(
												getShell(), repository, result,
												op.getDestinationString(), true,
												PushMode.UPSTREAM);
										dlg.showConfigureButton(false);
										dlg.open();
									});
