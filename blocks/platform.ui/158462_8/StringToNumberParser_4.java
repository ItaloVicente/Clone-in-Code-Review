
	public static Format getDefaultFormat() {
		if (IS_ICU_PRESENT) {
			return IcuNumberFormatFactory.getInstance();
		}
		return java.text.NumberFormat.getInstance();
	}

	public static Format getDefaultNumberFormat() {
		if (IS_ICU_PRESENT) {
			return IcuNumberFormatFactory.getNumberInstance();
		}
		return java.text.NumberFormat.getNumberInstance();
	}

	public static Format getDefaultIntegerFormat() {
		if (IS_ICU_PRESENT) {
			return IcuNumberFormatFactory.getIntegerInstance();
		}
		return java.text.NumberFormat.getIntegerInstance();
	}


	private static class IcuNumberFormatFactory {
		private static Format getInstance() {
			return com.ibm.icu.text.NumberFormat.getInstance();
		}
		private static Format getNumberInstance() {
			return com.ibm.icu.text.NumberFormat.getNumberInstance();
		}

		private static Format getIntegerInstance() {
			return com.ibm.icu.text.NumberFormat.getIntegerInstance();
		}
	}

	private static boolean findIcuPresent() {
		try {
			Class.forName("com.ibm.icu.text.NumberFormat"); //$NON-NLS-1$
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
