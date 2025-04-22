		autoRefresh = new ToolItem(toolbar, SWT.CHECK);
		autoRefresh.setImage(ImageResource.getImage(ImageResource.IMG_ELCL_NAV_REFRESH));
		autoRefresh.setHotImage(ImageResource.getImage(ImageResource.IMG_CLCL_NAV_REFRESH));
		autoRefresh.setDisabledImage(ImageResource.getImage(ImageResource.IMG_DLCL_NAV_REFRESH));
		autoRefresh.setToolTipText(Messages.actionWebBrowserAutoRefresh);
		autoRefresh.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> autoRefresh()));

