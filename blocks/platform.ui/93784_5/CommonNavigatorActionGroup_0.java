		boolean hideSearchFilterAction = viewerDescriptor
				.getBooleanConfigProperty("org.eclipse.ui.navigator.hideSearchFilterAction"); //$NON-NLS-1$
		if (!hideSearchFilterAction) {
			filterAction = new CommonNavigatorSearchFilterAction(commonViewer);
			filterAction.setId(ActionFactory.FIND.getId());
			ImageDescriptor findIcon = NavigatorPlugin
					.getImageDescriptor("icons/full/elcl16/find.png"); //$NON-NLS-1$
			filterAction.setImageDescriptor(findIcon);
			filterAction.setHoverImageDescriptor(findIcon);
		}

