	public static AboutItem scan(String s) {
		ArrayList linkRanges = new ArrayList();
		ArrayList links = new ArrayList();


		int urlSeparatorOffset = s.indexOf("://"); //$NON-NLS-1$
		while (urlSeparatorOffset >= 0) {

			boolean startDoubleQuote = false;

