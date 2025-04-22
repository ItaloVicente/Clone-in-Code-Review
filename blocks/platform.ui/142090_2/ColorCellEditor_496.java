			Point colorSize = colorLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT,
					force);
			Point rgbSize = rgbLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT,
					force);
			return new Point(colorSize.x + GAP + rgbSize.x, Math.max(
					colorSize.y, rgbSize.y));
		}

		@Override
