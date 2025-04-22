		if (legacyWindow.isClosing()) {
			String warningMessage = MessageFormat.format(
					"Cannot open editor \"{0}\" while workbench window is closing", //$NON-NLS-1$
					editorId);
			IStatus warningStatus = new Status(IStatus.WARNING, PlatformUI.PLUGIN_ID, warningMessage,
					new UnsupportedOperationException());
			WorkbenchPlugin.log(warningStatus);
			return null;
		}

