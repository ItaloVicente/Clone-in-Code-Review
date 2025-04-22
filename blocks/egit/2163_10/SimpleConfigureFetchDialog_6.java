									FetchResultDialog dlg;
									dlg = new FetchResultDialog(getShell(),
											repository, op.execute(monitor), op
													.getSourceString());
									dlg.open();
								} catch (CoreException e) {
									Activator.handleError(e.getMessage(), e,
											true);
