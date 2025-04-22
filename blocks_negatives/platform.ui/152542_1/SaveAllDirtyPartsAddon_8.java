				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						removeIdleListenerFromWorkbenchDisplay();
						autoSaveJob.cancel();
					}
