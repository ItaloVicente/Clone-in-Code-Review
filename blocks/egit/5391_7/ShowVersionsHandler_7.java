		Object input = getPage().getInputInternal().getSingleFile();
		if (input == null)
			return null;
		IWorkbenchPage workBenchPage = HandlerUtil
				.getActiveWorkbenchWindowChecked(event).getActivePage();
		boolean errorOccurred = false;
		List<ObjectId> ids = new ArrayList<ObjectId>();
