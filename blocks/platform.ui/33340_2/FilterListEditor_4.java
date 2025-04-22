    @Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
    	super.doFillIntoGrid(parent, numColumns);
        List list = getListControl(parent);
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, numColumns - 1, 1);
    	PixelConverter pixelConverter = new PixelConverter(parent);
        gd.widthHint = pixelConverter.convertWidthInCharsToPixels(65);
        list.setLayoutData(gd);
    }

