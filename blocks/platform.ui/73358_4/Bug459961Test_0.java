
		if (System.getProperty("org.eclipse.swt.internal.gtk.version", "").startsWith("3.")) { // $NON-NLS-1//NON-NLS-3
			expected = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND).getRGBA();
			expected.alpha = 0;
		}

