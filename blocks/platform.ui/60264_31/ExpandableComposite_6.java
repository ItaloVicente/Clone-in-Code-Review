			int resultWidth = width + marginWidth + marginWidth + thmargin + thmargin;

			if (wHint != SWT.DEFAULT) {
				resultWidth = wHint;
			}

			int resultHeight = height + marginHeight + marginHeight + tvmargin + tvmargin;

			if (hHint != SWT.DEFAULT) {
				resultHeight = hHint;
			}

			Point result = new Point(resultWidth, resultHeight);
