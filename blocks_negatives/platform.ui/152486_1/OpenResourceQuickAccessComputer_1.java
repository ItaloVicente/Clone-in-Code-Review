						@Override
						public void execute() {
							try {
								IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(),
										(IFile) resource);
							} catch (PartInitException e) {
								IDEWorkbenchPlugin.log(e.getMessage(), e);
							}
						}
					});
