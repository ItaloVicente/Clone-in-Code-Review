				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						Shell currentShell = getShell();
						if (currentShell == null || currentShell.isDisposed()) {
							return;
						}
						finishedRun();
