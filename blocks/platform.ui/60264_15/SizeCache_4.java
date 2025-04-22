		if (FormUtil.isWrapControl(control)) {
			int minWidth = computeMinimumWidth();

			if (widthHint != SWT.DEFAULT && widthHint + widthAdjustment < minWidth) {
				if (heightHint == SWT.DEFAULT) {
					return new Point(minWidth, computeHeightAtMinimumWidth());
				}

				widthHint = minWidth - widthAdjustment;
			}

			int minHeight = computeMinimumHeight();

			if (heightHint != SWT.DEFAULT && heightHint + heightAdjustment < minHeight) {
				if (widthHint == SWT.DEFAULT) {
					return new Point(computeWidthAtMinimumHeight(), minHeight);
				}

				heightHint = minHeight - heightAdjustment;
			}
		}
