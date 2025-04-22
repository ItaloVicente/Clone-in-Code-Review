		CTabFolder tabFolder = (CTabFolder) control;
		String postion = ((CSSPrimitiveValue) value).getStringValue();
		switch (postion.toLowerCase()) {
		case "bottom":
			tabFolder.setTabPosition(SWT.BOTTOM);
			break;
		case "top":
			tabFolder.setTabPosition(SWT.TOP);
			break;
