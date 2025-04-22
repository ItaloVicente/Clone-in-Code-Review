									FetchResultDialog dlg;
									dlg = new FetchResultDialog(getShell(),
											repository, op.execute(monitor), op
													.getSourceString());
									dlg.showConfigureButton(false);
									dlg.open();
