		Display display = Display.getCurrent();
		if (display == null) {
			display = Display.getDefault ();
		}
		
        FONTDATA_ARRAY_DEFAULT_DEFAULT = display.getSystemFont().getFontData();
