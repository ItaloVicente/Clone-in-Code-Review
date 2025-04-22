		GridLayout gl = new GridLayout();
		gl.marginHeight = 0;
		gl.marginWidth = 0;
		if (isHorizontal(side))
			gl.numColumns = 3;
		region.setLayout(gl);

		viewer = new ProgressCanvasViewer(region, SWT.NO_FOCUS, 1, 36, isHorizontal(side) ? SWT.HORIZONTAL : SWT.VERTICAL);
		viewer.setUseHashlookup(true);
		Control viewerControl = viewer.getControl();
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		Point viewerSizeHints = viewer.getSizeHints();
		if (isHorizontal(side)) {
			gd.widthHint = viewerSizeHints.x;
			gd.heightHint = viewerSizeHints.y;
		} else {
			gd.widthHint = viewerSizeHints.y;
			gd.heightHint = viewerSizeHints.x;
		}
		viewerControl.setLayoutData(gd);
