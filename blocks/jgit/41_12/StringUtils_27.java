	public static boolean toBoolean(final String stringValue) {
		if (stringValue == null)
			throw new NullPointerException("Expected boolean string value");

		if (equalsIgnoreCase("yes"
				|| equalsIgnoreCase("true"
				|| equalsIgnoreCase("1"
				|| equalsIgnoreCase("on"
			return true;

		} else if (equalsIgnoreCase("no"
				|| equalsIgnoreCase("false"
				|| equalsIgnoreCase("0"
				|| equalsIgnoreCase("off"
			return false;

		} else {
			throw new IllegalArgumentException("Not a boolean: " + stringValue);
		}
	}

