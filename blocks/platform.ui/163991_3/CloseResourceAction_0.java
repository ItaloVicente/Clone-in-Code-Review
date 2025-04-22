
		try {
			getShell().setLayoutDeferred(true);
			closeMatchingEditors(projects, false);
		} finally {
			getShell().setLayoutDeferred(false);
		}
