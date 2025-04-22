								+ IDEWorkbenchMessages.ShowInSystemExplorerHandler_commandUnavailable);
					}

					File dir = item.getWorkspace().getRoot().getLocation().toFile();
					Process p;
					if (Util.isLinux() || Util.isMac()) {
						p = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", launchCmd }, null, dir); //$NON-NLS-1$ //$NON-NLS-2$
					} else {
						p = Runtime.getRuntime().exec(launchCmd, null, dir);
					}
					int retCode = p.waitFor();
					if (retCode != 0 && !Util.isWindows()) {
						return new Status(IStatus.ERROR, IDEWorkbenchPlugin
								.getDefault().getBundle().getSymbolicName(),
								logMsgPrefix + "Execution of '" + launchCmd //$NON-NLS-1$
										+ "' failed with return code: " + retCode); //$NON-NLS-1$
					}
				} catch (Exception e) {
					return new Status(IStatus.ERROR, IDEWorkbenchPlugin.getDefault()
							.getBundle().getSymbolicName(), logMsgPrefix
							+ "Unhandled failure.", e); //$NON-NLS-1$
				}
				return Status.OK_STATUS;
