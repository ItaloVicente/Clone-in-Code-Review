			}
			if (IDE.getMarkerHelpRegistry().hasResolutions(marker)) {
				if (image == MarkerSupportInternalUtilities.getSeverityImage(IMarker.SEVERITY_WARNING)) {
					image = WorkbenchPlugin.getDefault().getSharedImages()
							.getImage(IDEInternalWorkbenchImages.IMG_OBJS_FIXABLE_WARNING);
				} else if (image == MarkerSupportInternalUtilities.getSeverityImage(IMarker.SEVERITY_ERROR)) {
					image = WorkbenchPlugin.getDefault().getSharedImages()
							.getImage(IDEInternalWorkbenchImages.IMG_OBJS_FIXABLE_ERROR);
				} else if (image == MarkerSupportInternalUtilities.getSeverityImage(IMarker.SEVERITY_INFO)) {
					image = WorkbenchPlugin.getDefault().getSharedImages()
							.getImage(IDEInternalWorkbenchImages.IMG_OBJS_FIXABLE_INFO);
				} else if (image != null) {
					descriptors[IDecoration.BOTTOM_RIGHT] = IDEWorkbenchPlugin.getIDEImageDescriptor(
							MarkerSupportInternalUtilities.IMG_MARKERS_QUICK_FIX_DECORATION_PATH);
				}
				if (image == null) {
					image = WorkbenchPlugin.getDefault().getSharedImages()
							.getImage(IDEInternalWorkbenchImages.IMG_ELCL_QUICK_FIX_ENABLED);
