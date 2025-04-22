		Display display = Display.getCurrent();
		if (display == null) {
			display = Display.getDefault();
		}
		if (display != null) {
			name = name.replace('-', '_');
			if (name.startsWith("#")) { //$NON-NLS-1$
				name = name.substring(1);
			}
			return process(display, name);
		}
