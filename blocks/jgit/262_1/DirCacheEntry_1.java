
	private static String toString(final byte[] path) {
		return Constants.CHARSET.decode(ByteBuffer.wrap(path)).toString();
	}

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

			default:
				componentHasChars = true;
			}
		}
		return componentHasChars;
	}
