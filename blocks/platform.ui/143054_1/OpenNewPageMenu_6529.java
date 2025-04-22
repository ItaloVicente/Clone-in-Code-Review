		try {
			getWindow().openPage(desc.getId(), pageInput);
		} catch (WorkbenchException e) {
			StatusUtil.handleStatus(WorkbenchMessages.OpenNewPageMenu_dialogTitle + ": " + //$NON-NLS-1$
					e.getMessage(), e, StatusManager.SHOW);
		}
	}
