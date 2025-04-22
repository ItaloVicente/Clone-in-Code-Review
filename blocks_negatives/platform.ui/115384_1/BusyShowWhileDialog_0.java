		detailsButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					ProgressManager.getInstance().busyCursorWhile(new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor) throws InvocationTargetException,
								InterruptedException {
							long time = System.currentTimeMillis();
							long delay = PlatformUI.getWorkbench().getProgressService().getLongOperationTime();
							long end = time + delay + delay;
							while (end > System.currentTimeMillis()) {
								final Shell myShell = BusyShowWhileDialog.this.getShell();
								myShell.getDisplay().asyncExec(new Runnable() {
									@Override
									public void run() {
										if(myShell.isDisposed())
											return;
										myShell.getDisplay().sleep();
										myShell.setText(String.valueOf(System.currentTimeMillis()));
									}
								});
							}
						}
					});
				} catch (InvocationTargetException error) {
					error.printStackTrace();
				} catch (InterruptedException error) {
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
