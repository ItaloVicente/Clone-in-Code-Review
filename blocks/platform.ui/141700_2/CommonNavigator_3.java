		boolean enableToolTipSupport = commonViewer.getNavigatorContentService().getViewerDescriptor()
				.getBooleanConfigProperty(INavigatorViewerDescriptor.PROP_ENABLE_TOOLTIP_SUPPORT);
		if (enableToolTipSupport) {
			ColumnViewerToolTipSupport.enableFor(commonViewer);
		}

