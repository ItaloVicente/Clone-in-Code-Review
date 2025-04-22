					display.syncExec(() -> {
						synchronized (RCPTestWorkbenchAdvisor.class) {
							if (callDisplayAccess)
								syncWithDisplayAccess = !isSTARTED() ? Boolean.TRUE : Boolean.FALSE;
							else
								syncWithoutDisplayAccess = !isSTARTED() ? Boolean.TRUE : Boolean.FALSE;
