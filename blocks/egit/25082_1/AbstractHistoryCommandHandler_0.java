		return getPageFromPart(part);
	}

	protected GitHistoryPage getPage(ExecutionEvent event)
			throws ExecutionException {
		IWorkbenchPart part = getPart(event);
		GitHistoryPage page = getPageFromPart(part);
		if (page == null)
			throw new ExecutionException(
					UIText.AbstractHistoryCommandHandler_CouldNotGetPageMessage);
		return page;
	}

	private GitHistoryPage getPageFromPart(IWorkbenchPart part) {
