	public static AboutItem scan(String s) {
		ArrayList<int[]> linkRanges = new ArrayList<int[]>();
		ArrayList<String> links = new ArrayList<String>();


		int urlSeparatorOffset = s.indexOf("://"); //$NON-NLS-1$
		while (urlSeparatorOffset >= 0) {

			boolean startDoubleQuote = false;

