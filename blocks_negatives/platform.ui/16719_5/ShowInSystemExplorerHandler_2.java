				StatusManager
						.getManager()
						.handle(new Status(
								IStatus.ERROR,
								IDEWorkbenchPlugin.getDefault().getBundle()
										.getSymbolicName(),
								logMsgPrefix
										+ IDEWorkbenchMessages.ShowInSystemExplorerHandler_commandUnavailable),
								StatusManager.SHOW | StatusManager.LOG);
