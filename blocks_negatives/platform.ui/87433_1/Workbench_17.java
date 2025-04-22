					if (e4Workbench.isRestart()) {
						returnCode[0] = PlatformUI.RETURN_RESTART;
					} else {
						e4Workbench.close();
						returnCode[0] = workbench.returnCode;
					}
