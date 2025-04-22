		if (contentService.getViewerDescriptor()
				.isVisibleContentExtension("org.eclipse.ui.navigator.resourceContent")) { //$NON-NLS-1$
			this.userFiltersTab = new UserFiltersTab(customizationsTabFolder, this.commonViewer);
			createTabItem(customizationsTabFolder,
					CommonNavigatorMessages.CommonFilterSelectionDialog_User_Resource_Filters, userFiltersTab,
					FILTER_ICON);
		}
