			if (textLabel instanceof Label) {
				Point defSize = textLabelCache.computeSize(SWT.DEFAULT,
						SWT.DEFAULT);
				if (defSize.y == size.y) {
					size.x = Math.min(defSize.x, size.x);
				}
