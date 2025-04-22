			sb.append(path.segment(i));
		}
		return sb.toString();
	}

	static int getLineNumber(IMarker marker) {
		return marker.getAttribute(IMarker.LINE_NUMBER, -1);
	}

	static String getLocation(IMarker marker) {
		return marker.getAttribute(IMarker.LOCATION, "");//$NON-NLS-1$
	}

	static String getMessage(IMarker marker) {
		return marker.getAttribute(IMarker.MESSAGE, "");//$NON-NLS-1$
	}

	static int getNumericValue(String value) {
		boolean negative = false;
		int i = 0;
		int len = value.length();

		if (i < len && value.charAt(i) == '#') {
			++i;
		}

		if (i < len && value.charAt(i) == '-') {
			negative = true;
