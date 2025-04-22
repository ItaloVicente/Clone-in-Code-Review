		return getPageFromPart(part);
	}

	protected GitHistoryPage getPage(ExecutionEvent event)
			throws ExecutionException {
		IWorkbenchPart part = getPart(event);
		return getPageFromPart(part);
	}

	private GitHistoryPage getPageFromPart(IWorkbenchPart part) {
