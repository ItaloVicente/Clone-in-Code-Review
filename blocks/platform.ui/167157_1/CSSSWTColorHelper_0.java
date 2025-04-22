		Display display = Display.getCurrent();
		if (display == null) {
			display = Display.getDefault();
		}
		if (display != null) {
			return process(display, name.replace('-', '_'));
		}
