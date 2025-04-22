
		private Point computeSize(SizeCache clientCache, int wHint) {
			if (wHint == MIN_WIDTH) {
				int minWidth = clientCache.computeMinimumWidth();
				return clientCache.computeSize(minWidth, SWT.DEFAULT);
			}
			return clientCache.computeSize(wHint, SWT.DEFAULT);
		}
