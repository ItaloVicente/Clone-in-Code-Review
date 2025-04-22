
		int widthHint;
		int heightHint = SWT.DEFAULT;
		if (cd != null) {
			if (cd.horizontalAlignment == SWT.FILL) {
				if (wHint == SWT.DEFAULT) {
					widthHint = cd.widthHint;
				} else {
					widthHint = wHint;
				}
			} else {
				widthHint = cd.widthHint;
			}
			heightHint = cd.heightHint;
		} else {
			widthHint = wHint;
		}
		Point result = cache.computeSize(controlIndex, widthHint, heightHint);

		if (cd != null && wHint != SWT.DEFAULT && result.x > wHint) {
			result = cache.computeSize(controlIndex, wHint, heightHint);
		}

		return result;
