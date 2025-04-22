				StatusManager
						.getManager()
						.handle(new Status(
								IStatus.ERROR,
								IDEWorkbenchPlugin.getDefault().getBundle()
										.getSymbolicName(),
								logMsgPrefix
										+ IDEWorkbenchMessages.ShowInSystemExplorerHandler_notDetermineLocation),
								StatusManager.SHOW | StatusManager.LOG);
