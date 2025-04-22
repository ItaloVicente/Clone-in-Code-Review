					display.syncExec(new Runnable() {
						@Override
						public void run() {
							synchronized (RCPTestWorkbenchAdvisor.class) {
								if (callDisplayAccess)
									syncWithDisplayAccess = !isSTARTED() ? Boolean.TRUE
											: Boolean.FALSE;
								else
									syncWithoutDisplayAccess = !isSTARTED() ? Boolean.TRUE
											: Boolean.FALSE;
							}
