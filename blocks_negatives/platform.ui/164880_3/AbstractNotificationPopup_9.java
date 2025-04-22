				AbstractNotificationPopup.this.display.asyncExec(new Runnable() {
					@Override
					public void run() {
						Shell shell = AbstractNotificationPopup.this.getShell();
						if (shell == null || shell.isDisposed()) {
							return;
						}

						if (isMouseOver(shell)) {
							scheduleAutoClose();
							return;
						}
