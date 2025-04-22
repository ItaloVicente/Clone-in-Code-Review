								FetchOperationResult result = fetchOp
										.getOperationResult();
								FetchResultDialog dlg = new FetchResultDialog(
										getShell(), repository, result, config
												.getName());
								dlg.showConfigureButton(false);
								dlg.open();

