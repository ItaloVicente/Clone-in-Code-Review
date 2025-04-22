        try {
            getWindow().getWorkbench().openWorkbenchWindow(desc.getId(),
                    pageInput);
        } catch (WorkbenchException e) {
			StatusUtil.handleStatus(
							e.getMessage(), e, StatusManager.SHOW);
