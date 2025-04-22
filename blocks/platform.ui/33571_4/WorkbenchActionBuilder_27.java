
	private IContributionItem getItem(String actionId, String commandId, String image, String disabledImage,
			String label, String tooltip, String helpContextId) {
		ISharedImages sharedImages = getWindow().getWorkbench().getSharedImages();

		IActionCommandMappingService acms = getWindow().getService(IActionCommandMappingService.class);
