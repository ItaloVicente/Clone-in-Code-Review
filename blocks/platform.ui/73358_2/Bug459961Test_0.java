
		if (Util.isGtk()) {
			expected = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND).getRGBA();
			expected.alpha = 0;
		}

