			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					if (isStarting()) {
						WorkbenchPlugin.log(StatusUtil.newStatus(IStatus.WARNING,
								"Event loop should not be run while the Workbench is starting.", //$NON-NLS-1$
								new RuntimeException()));
					}
