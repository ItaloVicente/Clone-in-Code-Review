	private static final void openView(IWorkbenchWindow window, final MPartDescriptor viewDescriptor,
			EPartService partService) {
		String viewId = viewDescriptor.getElementId();
		if (CompatibilityPart.COMPATIBILITY_VIEW_URI.equals(viewDescriptor.getContributionURI())) {
			IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				try {
					page.showView(viewId);
				} catch (PartInitException e) {
					handleViewError(viewId, e);
				}
			}
		} else {
			MPart part = partService.findPart(viewId);
			if (part == null) {
				MPlaceholder placeholder = partService.createSharedPart(viewId);
				part = (MPart) placeholder.getRef();
			}
			partService.showPart(part, PartState.ACTIVATE);
		}
	}

	private static MPartDescriptor getViewDescriptor(MApplication app, String id) {
		List<MPartDescriptor> descriptors = app.getDescriptors();
		for (MPartDescriptor descriptor : descriptors) {
			if (id.equals(descriptor.getElementId()) && isView(descriptor)) {
				return descriptor;
			}
		}
		return null;
	}

	private static boolean isView(MPartDescriptor descriptor) {
		return descriptor.getTags().contains("View"); //$NON-NLS-1$
	}

	private static void handleViewError(String id, PartInitException e) {
		StatusUtil.handleStatus(e.getStatus(), "View could not be opened: " + id, //$NON-NLS-1$
				StatusManager.SHOW);
	}

	private static void handleMissingView(final Object id) {
		ExecutionException e = new ExecutionException("View could not be found: " + id); //$NON-NLS-1$
		StatusUtil.handleStatus(e, StatusManager.SHOW);
