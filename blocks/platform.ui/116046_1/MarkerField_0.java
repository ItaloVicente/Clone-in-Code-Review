		IMarker marker = item.getMarker();
		if (marker != null) {
			String contextId = IDE.getMarkerHelpRegistry().getHelp(marker);
			if (contextId != null) {
				if (image == null)
					image = JFaceResources.getImage(Dialog.DLG_IMG_HELP);
				else {
					descriptors[IDecoration.TOP_RIGHT] = IDEWorkbenchPlugin
							.getIDEImageDescriptor(MarkerSupportInternalUtilities.IMG_MARKERS_HELP_DECORATION_PATH);
