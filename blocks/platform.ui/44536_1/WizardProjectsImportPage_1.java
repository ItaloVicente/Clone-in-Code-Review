			try {
				operation.run(monitor);
			} catch (InvocationTargetException e) {
				if (e.getCause() instanceof CoreException) {
					return ((CoreException) e.getCause()).getStatus();
				}
				return new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, 2,
						e.getCause().getLocalizedMessage(), e);
