	static String getxName(String name, int zoom) {
		if (zoom == 100) {
			return name;
		}
		int dot = name.lastIndexOf('.');
		if (dot != -1 && (zoom == 150 || zoom == 200)) {
			String lead = name.substring(0, dot);
			String tail = name.substring(dot);
			if (InternalPolicy.DEBUG_LOAD_URL_IMAGE_DESCRIPTOR_2x_PNG_FOR_GIF && ".gif".equalsIgnoreCase(tail)) { //$NON-NLS-1$
				tail = ".png"; //$NON-NLS-1$
			}
			String x = zoom == 150 ? "@1.5x" : "@2x"; //$NON-NLS-1$ //$NON-NLS-2$
			String file = lead + x + tail;
			return file;
		}
		return null;
