
		if (shouldReturnNoWindowError(result)) {
			result = new MultiStatus(PlatformUI.PLUGIN_ID, IStatus.ERROR,
					WorkbenchMessages.Workbench_noWindowsRestored, null);
		}

