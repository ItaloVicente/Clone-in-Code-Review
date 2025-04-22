		if (!AboutUtils.openBrowser(getShell(),
				getMoreInfoURL(bundleInfo, true))) {
			String message = NLS.bind(
					WorkbenchMessages.AboutPluginsDialog_unableToOpenFile,
					PLUGININFO, bundleInfo.getId());
			StatusUtil.handleStatus(
					WorkbenchMessages.AboutPluginsDialog_errorTitle
							+ ": " + message, StatusManager.SHOW, getShell()); //$NON-NLS-1$
