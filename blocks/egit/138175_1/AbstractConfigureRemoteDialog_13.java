		commonUriText = new StyledText(sameUriDetails,
				SWT.SINGLE | SWT.READ_ONLY);
		commonUriText.setBackground(sameUriDetails.getBackground());
		commonUriText.setCaret(null);
		GridDataFactory.fillDefaults().grab(true, false)
				.align(SWT.FILL, SWT.CENTER).applyTo(commonUriText);
