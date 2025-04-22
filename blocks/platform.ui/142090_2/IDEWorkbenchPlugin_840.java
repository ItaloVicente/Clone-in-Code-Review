	}

	public static IDEWorkbenchPlugin getDefault() {
		return inst;
	}

	public static IWorkspace getPluginWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public static void log(String message) {
		getDefault().getLog().log(
				StatusUtil.newStatus(IStatus.ERROR, message, null));
	}

	public static void log(String message, Throwable t) {
		IStatus status = StatusUtil.newStatus(IStatus.ERROR, message, t);
		log(message, status);
	}

