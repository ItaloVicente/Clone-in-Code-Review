        try {
            getWindow().openPage(desc.getId(), pageInput);
        } catch (WorkbenchException e) {
        	StatusUtil.handleStatus(
							e.getMessage(), e, StatusManager.SHOW);
        }
    }
