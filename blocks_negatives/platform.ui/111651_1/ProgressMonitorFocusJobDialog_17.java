				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						if (alreadyClosed) {
						}
						Shell shell = getShell();
						if (shell != null && shell.isDisposed())
							return;

						runnable.run();
