				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						synchronized (RCPTestWorkbenchAdvisor.class) {
							if (callDisplayAccess)
								asyncWithDisplayAccess = !isSTARTED() ? Boolean.TRUE
										: Boolean.FALSE;
							else
								asyncWithoutDisplayAccess = !isSTARTED() ? Boolean.TRUE
										: Boolean.FALSE;
						}
					}});
