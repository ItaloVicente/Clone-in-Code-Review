				if ("".equals(launchCmd)) { //$NON-NLS-1$
					return statusReporter.newStatus(IStatus.ERROR, logMsgPrefix
							+ IDEWorkbenchMessages.ShowInSystemExplorerHandler_commandUnavailable, null);
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
					return statusReporter.newStatus(IStatus.ERROR, "Execution of '" + launchCmd //$NON-NLS-1$
							+ "' failed with return code: " + retCode, null); //$NON-NLS-1$
