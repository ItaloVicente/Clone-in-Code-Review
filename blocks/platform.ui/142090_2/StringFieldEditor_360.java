		getLabelControl(parent);

		textField = getTextControl(parent);
		GridData gd = new GridData();
		gd.horizontalSpan = numColumns - 1;
		if (widthInChars != UNLIMITED) {
			GC gc = new GC(textField);
			try {
				Point extent = gc.textExtent("X");//$NON-NLS-1$
				gd.widthHint = widthInChars * extent.x;
			} finally {
				gc.dispose();
			}
		} else {
			gd.horizontalAlignment = GridData.FILL;
			gd.grabExcessHorizontalSpace = true;
		}
		textField.setLayoutData(gd);
	}

	@Override
