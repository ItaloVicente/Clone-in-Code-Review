				display.asyncExec(() -> {
					synchronized (RCPTestWorkbenchAdvisor.class) {
						if (callDisplayAccess)
							asyncWithDisplayAccess = !isSTARTED() ? Boolean.TRUE : Boolean.FALSE;
						else
							asyncWithoutDisplayAccess = !isSTARTED() ? Boolean.TRUE : Boolean.FALSE;
					}
				});
