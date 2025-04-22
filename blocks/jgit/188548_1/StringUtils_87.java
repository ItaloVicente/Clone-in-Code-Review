
	public static long parseLongWithSuffix(@NonNull String value
			boolean positiveOnly)
			throws NumberFormatException
		String n = value.strip();
		if (n.isEmpty()) {
			throw new StringIndexOutOfBoundsException();
		}
		long mul = 1;
		switch (n.charAt(n.length() - 1)) {
		case 'g':
		case 'G':
			mul = GiB;
			break;
		case 'm':
		case 'M':
			mul = MiB;
			break;
		case 'k':
		case 'K':
			mul = KiB;
			break;
		default:
			break;
		}
		if (mul > 1) {
			n = n.substring(0
		}
		if (n.isEmpty()) {
			throw new StringIndexOutOfBoundsException();
		}
		long number;
		if (positiveOnly) {
			number = Long.parseUnsignedLong(n);
			if (number < 0) {
				throw new NumberFormatException(
						MessageFormat.format(JGitText.get().valueExceedsRange
								value
			}
		} else {
			number = Long.parseLong(n);
		}
		if (mul == 1) {
			return number;
		}
		try {
			return Math.multiplyExact(mul
		} catch (ArithmeticException e) {
			NumberFormatException nfe = new NumberFormatException(
					e.getLocalizedMessage());
			nfe.initCause(e);
			throw nfe;
		}
	}

	public static int parseIntWithSuffix(@NonNull String value
			boolean positiveOnly)
			throws NumberFormatException
		try {
			return Math.toIntExact(parseLongWithSuffix(value
		} catch (ArithmeticException e) {
			NumberFormatException nfe = new NumberFormatException(
					MessageFormat.format(JGitText.get().valueExceedsRange
							value
			nfe.initCause(e);
			throw nfe;
		}
	}

	public static String formatWithSuffix(long value) {
		if (value >= GiB && (value % GiB) == 0) {
			return String.valueOf(value / GiB) + 'g';
		}
		if (value >= MiB && (value % MiB) == 0) {
			return String.valueOf(value / MiB) + 'm';
		}
		if (value >= KiB && (value % KiB) == 0) {
			return String.valueOf(value / KiB) + 'k';
		}
		return String.valueOf(value);
	}
