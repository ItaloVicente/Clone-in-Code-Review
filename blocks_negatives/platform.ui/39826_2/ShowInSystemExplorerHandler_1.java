			File dir = item.getWorkspace().getRoot().getLocation().toFile();
			Process p;
			if (Util.isLinux() || Util.isMac()) {
				p = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", launchCmd }, null, dir); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				p = Runtime.getRuntime().exec(launchCmd, null, dir);
			}
			int retCode = p.waitFor();
			if (retCode != 0 && !Util.isWindows()) {
				log.log(new Status(IStatus.ERROR, IDEWorkbenchPlugin
						.getDefault().getBundle().getSymbolicName(),
			}
		} catch (Exception e) {
			log.log(new Status(IStatus.ERROR, IDEWorkbenchPlugin.getDefault()
					.getBundle().getSymbolicName(), logMsgPrefix
					+ "Unhandled failure.", e)); //$NON-NLS-1$
			throw new ExecutionException("Show in Explorer command failed.", e); //$NON-NLS-1$
		}
