	public static boolean isSticky(IViewPart part) {
		String id = part.getSite().getId();
		IStickyViewDescriptor[] descs = PlatformUI.getWorkbench()
				.getViewRegistry().getStickyViews();
		for (IStickyViewDescriptor desc : descs) {
			if (desc.getId().equals(id)) {
