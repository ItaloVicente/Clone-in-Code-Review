		boolean ok = true;
		if (userIsDirty) {
			try {
				userConfigEditor.save();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
				ok = false;
			}
		}
		if (sysIsDirty) {
