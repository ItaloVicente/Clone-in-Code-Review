		try {
			page = contributor.createPage(element);
		} catch (CoreException e) {
			IStatus errStatus = StatusUtil.newStatus(e.getStatus(), WorkbenchMessages.PropertyPageNode_errorMessage);
			StatusManager.getManager().handle(errStatus, StatusManager.SHOW);
			page = new EmptyPropertyPage();
		}
		setPage(page);
	}
