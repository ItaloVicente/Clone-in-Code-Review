	static boolean isValidPath(final byte[] path) {
		if (path.length == 0)

		boolean componentHasChars = false;
		for (final byte c : path) {
			switch (c) {
			case 0:

			case '/':
				if (componentHasChars)
					componentHasChars = false;
				else
					return false;
				break;
			case '\\':
			case ':':
				if (SystemReader.getInstance().isWindows())
					return false;
			default:
				componentHasChars = true;
			}
		}
		return componentHasChars;
	}

