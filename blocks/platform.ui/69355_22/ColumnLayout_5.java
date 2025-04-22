
		if (cd != null) {
			return FormUtil.computeControlSize(sizeCache, wHint, cd.widthHint, cd.heightHint,
					cd.horizontalAlignment == ColumnLayoutData.FILL);
		}
		return FormUtil.computeControlSize(sizeCache, wHint, SWT.DEFAULT, SWT.DEFAULT, true);
