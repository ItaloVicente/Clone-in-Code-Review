		return Job.ASYNC_FINISH;
	}

	public abstract IStatus runInUIThread(IProgressMonitor monitor);

	public void setDisplay(Display runDisplay) {
		Assert.isNotNull(runDisplay);
		cachedDisplay = runDisplay;
	}

	public Display getDisplay() {
		if (cachedDisplay == null) {
			cachedDisplay = Services.getInstance().getDisplay();
		}
		if (cachedDisplay == null) {
			cachedDisplay = Display.getCurrent();
		}
		if (cachedDisplay == null) {
			cachedDisplay = Display.getDefault();
		}
		return cachedDisplay;
	}

	public static IStatus getStatus(Throwable t) {
		String message = StatusUtil.getLocalizedMessage(t);

		return newError(message, t);
	}

	public static IStatus newError(String message, Throwable t) {
		String pluginId = IProgressConstants.PLUGIN_ID;
		int errorCode = IStatus.OK;

		if (t instanceof CoreException) {
			CoreException ce = (CoreException) t;
			pluginId = ce.getStatus().getPlugin();
			errorCode = ce.getStatus().getCode();
		}

		return new Status(IStatus.ERROR, pluginId, errorCode, message,
				StatusUtil.getCause(t));
	}

	protected UISynchronize getUiSynchronize() {
		return Services.getInstance().getUISynchronize();
	}
