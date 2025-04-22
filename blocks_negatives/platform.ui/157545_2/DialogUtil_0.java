	/**
	 * Removes the '&amp;' accelerator indicator from a label, if any. Also removes the
	 * () accelerators which are used in Asian languages.
	 */
	public static String removeAccel(String label) {

		if (startBracket >= 0) {
			int endBracket = label.indexOf(')');

			if ((endBracket - startBracket) == 3) {
				return label.substring(0, startBracket) + label.substring(endBracket + 1);
			}
		}

		int i = label.indexOf('&');
		if (i >= 0) {
			label = label.substring(0, i) + label.substring(i + 1);
		}

		return label;
	}

