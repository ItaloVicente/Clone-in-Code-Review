		detailsButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			try {
				ProgressManager.getInstance().busyCursorWhile(monitor -> {
					long time = System.currentTimeMillis();
					long delay = PlatformUI.getWorkbench().getProgressService().getLongOperationTime();
					long end = time + delay + delay;
					while (end > System.currentTimeMillis()) {
						final Shell myShell = BusyShowWhileDialog.this.getShell();
						myShell.getDisplay().asyncExec(() -> {
							if (myShell.isDisposed())
								return;
							myShell.getDisplay().sleep();
							myShell.setText(String.valueOf(System.currentTimeMillis()));
						});
					}
				});
			} catch (InvocationTargetException error) {
				error.printStackTrace();
			} catch (InterruptedException error) {
