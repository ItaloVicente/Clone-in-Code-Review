	@Override
	protected ImageDescriptor decorateImage(ImageDescriptor input, Object element) {
		ImageDescriptor res = super.decorateImage(input, element);
		if (res == null) {
			return null;
		}
		IResource resource = Adapters.adapt(element, IResource.class);
		if (resource != null && (resource.getType() != IResource.PROJECT || ((IProject) resource).isOpen())) {
			ImageDescriptor overlay = null;
			switch (getHighestProblemSeverity(resource)) {
			case IMarker.SEVERITY_ERROR:
				overlay = PlatformUI.getWorkbench().getSharedImages()
						.getImageDescriptor(ISharedImages.IMG_DEC_FIELD_ERROR);
				break;
			case IMarker.SEVERITY_WARNING:
				overlay = PlatformUI.getWorkbench().getSharedImages()
						.getImageDescriptor(ISharedImages.IMG_DEC_FIELD_WARNING);
				break;
			}
			if (overlay != null) {
				res = new DecorationOverlayIcon(res, overlay, IDecoration.BOTTOM_LEFT);
			}
		}
		return res;
	}


	private int getHighestProblemSeverity(IResource resource) {
		int problemSeverity = -1;
		try {
			for (IMarker marker : resource.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE)) {
				problemSeverity = Math.max(problemSeverity, marker.getAttribute(IMarker.SEVERITY, -1));
			}
		} catch (CoreException e) {
			WorkbenchNavigatorPlugin.log(e.getMessage(),
					new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.PLUGIN_ID, e.getMessage(), e));
		}
		return problemSeverity;
	}


