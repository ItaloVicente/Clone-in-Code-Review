
		assertStatusShellOpen();
		Link errorLogLink = StatusDialogUtil.getErrorLogLink();
		if (errorLogLink == null) {
			return;
		}
		assertNotNull("Link to error log should be present", errorLogLink);
