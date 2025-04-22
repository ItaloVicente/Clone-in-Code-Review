	public static String calcText(int index, String name, String toolTip, boolean rtl) {
		StringBuilder sb = new StringBuilder();

		int mnemonic = index + 1;
		StringBuilder nm = new StringBuilder();
		nm.append(mnemonic);
		if (mnemonic <= MAX_MNEMONIC_SIZE) {
			nm.insert(nm.length() - (mnemonic + "").length(), '&'); //$NON-NLS-1$
		}
