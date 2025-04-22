			if (retCode != 0) {
				log.log(new Status(
						IStatus.ERROR,
						IDEWorkbenchPlugin.getDefault().getBundle()
								.getSymbolicName(),
						logMsgPrefix
								+ "Execution of launch command failed with return code: " + retCode)); //$NON-NLS-1$
