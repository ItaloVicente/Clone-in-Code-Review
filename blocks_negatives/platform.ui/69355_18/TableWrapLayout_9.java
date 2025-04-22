	Point computeSize(int childIndex, int width, int indent, int maxWidth, int maxHeight) {
		int widthArg = width - indent;
		SizeCache controlCache = cache.getCache(childIndex);
		if (!isWrap(controlCache.getControl()))
			widthArg = SWT.DEFAULT;
		Point size = controlCache.computeSize(widthArg, SWT.DEFAULT);
		if (maxWidth!=SWT.DEFAULT)
			size.x = Math.min(size.x, maxWidth);
		if (maxHeight!=SWT.DEFAULT)
			size.y = Math.min(size.y, maxHeight);
		size.x += indent;
		return size;
	}

