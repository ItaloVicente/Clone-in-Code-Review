				Point csize;
				if (cwHint == MIN_WIDTH) {
					int minWidth = clientCache.computeMinimumWidth();
					csize = clientCache.computeSize(minWidth, SWT.DEFAULT);
				} else {
					csize = clientCache.computeSize(cwHint, SWT.DEFAULT);
				}
