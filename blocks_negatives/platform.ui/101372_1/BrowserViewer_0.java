        ToolItem refresh = new ToolItem(toolbar, SWT.NONE);
        refresh.setImage(ImageResource
                .getImage(ImageResource.IMG_ELCL_NAV_REFRESH));
        refresh.setHotImage(ImageResource
                .getImage(ImageResource.IMG_CLCL_NAV_REFRESH));
        refresh.setDisabledImage(ImageResource
                .getImage(ImageResource.IMG_DLCL_NAV_REFRESH));
        refresh.setToolTipText(Messages.actionWebBrowserRefresh);
		refresh.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> refresh()));

