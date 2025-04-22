		Control control = getLabelControl(parent);
		GridData gd = new GridData();
		control.setLayoutData(gd);

		scale = getScaleControl(parent);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.verticalAlignment = GridData.FILL;
		gd.horizontalSpan = numColumns - 1;
		gd.grabExcessHorizontalSpace = true;
		scale.setLayoutData(gd);
		updateScale();
	}

	@Override
