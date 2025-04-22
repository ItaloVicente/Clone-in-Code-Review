		CTabFolder tabFolder = (CTabFolder) control;
		String position = ((CSSPrimitiveValue) value).getStringValue();
		switch (position.toLowerCase()) {
		case "bottom":
			tabFolder.setTabPosition(SWT.BOTTOM);
			break;
		case "top":
			tabFolder.setTabPosition(SWT.TOP);
			break;
