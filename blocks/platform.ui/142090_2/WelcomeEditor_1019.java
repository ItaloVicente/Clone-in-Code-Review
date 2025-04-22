		InputStream is = null;
		try {
			is = url.openStream();
			read(is);
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR,
					IDEWorkbenchPlugin.IDE_WORKBENCH, 1, IDEWorkbenchMessages.WelcomeEditor_accessException, e);
			IDEWorkbenchPlugin.log(IDEWorkbenchMessages.WelcomeEditor_readFileError, status);
		} finally {
			try {
				if (is != null) {
