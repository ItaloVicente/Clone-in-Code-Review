		commitLink.setFont(JFaceResources.getBannerFont());
		commitLink.setForeground(JFaceColors.getHyperlinkText(commitLink
				.getDisplay()));
		Cursor handCursor = new Cursor(commitLink.getDisplay(), SWT.CURSOR_HAND);
		UIUtils.hookDisposal(commitLink, handCursor);
		commitLink.setCursor(handCursor);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(commitLink);
