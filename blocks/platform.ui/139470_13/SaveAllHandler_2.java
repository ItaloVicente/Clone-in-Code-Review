	private EPartService getPartService(IWorkbenchWindow window) {
		EPartService partService = null;
		if (window instanceof WorkbenchWindow) {
			try {
				partService = ((WorkbenchWindow) window).getModel().getContext().get(EPartService.class);
			} catch (Exception e) {
			}
		}
		return partService;
	}
