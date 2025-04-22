	public static Point computeControlSize(SizeCache cache, int constrainedWidth,
			int widthHintFromLayoutData, int heightHintFromLayoutData, boolean isFillAligned) {
		int widthHint;
		int heightHint = heightHintFromLayoutData;
		if (isFillAligned) {
			if (constrainedWidth == SWT.DEFAULT) {
				widthHint = widthHintFromLayoutData;
			} else {
				widthHint = constrainedWidth;
			}
		} else {
			widthHint = widthHintFromLayoutData;
		}

		Point result = cache.computeSize(widthHint, heightHint);

		if (!isFillAligned && constrainedWidth != SWT.DEFAULT && result.x > constrainedWidth) {
			result = cache.computeSize(constrainedWidth, heightHint);
		}

		return result;
	}

