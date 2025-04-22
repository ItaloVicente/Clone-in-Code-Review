		int result = 0;
		while (i < len) {
			int digit = Character.digit(value.charAt(i++), 10);
			if (digit < 0) {
				return result;
			}
			result = result * 10 + digit;
		}
		if (negative) {
			result = -result;
		}
		return result;
	}


	static String getResourceName(IMarker marker) {
		return marker.getResource().getName();
	}

	static String getCreationTime(IMarker marker) {
		try {
			return DateFormat.getDateTimeInstance(DateFormat.LONG,
					DateFormat.MEDIUM).format(
					new Date(marker.getCreationTime()));
		} catch (CoreException e) {
			return null;
		}
	}
