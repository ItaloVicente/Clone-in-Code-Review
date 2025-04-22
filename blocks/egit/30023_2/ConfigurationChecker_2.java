				if (PlatformUI.isWorkbenchRunning())
					PlatformUI.getWorkbench().getDisplay()
							.asyncExec(new Runnable() {
								public void run() {
									check();
								}
							});
				else
					schedule(1000L);
