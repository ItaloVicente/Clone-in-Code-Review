			Rectangle bounds = editor.getClientArea();
			Point colorSize = colorLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT,
					force);
			Point rgbSize = rgbLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT,
					force);
			int ty = (bounds.height - rgbSize.y) / 2;
			if (ty < 0) {
