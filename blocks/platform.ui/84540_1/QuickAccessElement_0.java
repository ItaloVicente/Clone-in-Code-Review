	private static final String WS_START = "^\\s+"; //$NON-NLS-1$
	private static final String WS_END = "\\s+$"; //$NON-NLS-1$
	private static final String ANY_WS = "\\s+"; //$NON-NLS-1$
	private static final String EMPTY_STR = ""; //$NON-NLS-1$
	private static final String PAR_START = "\\("; //$NON-NLS-1$
	private static final String PAR_END = "\\)"; //$NON-NLS-1$
	private static final String ONE_CHAR = ".?"; //$NON-NLS-1$

	private String pFilter;
	private Pattern pattern;

	private Pattern getPattern(String filter) {
		if (pattern == null || !filter.equals(pFilter)) {
			pFilter = filter;
			String sFilter = filter.replaceFirst(WS_START, EMPTY_STR).replaceFirst(WS_END, EMPTY_STR)
					.replaceAll(PAR_START, ONE_CHAR).replaceAll(PAR_END, ONE_CHAR);
			sFilter = String.format(".*(%s).*", sFilter.replaceAll(ANY_WS, ").*(")); //$NON-NLS-1$//$NON-NLS-2$
			pattern = Pattern.compile(sFilter, Pattern.CASE_INSENSITIVE);
		}
		return pattern;
	}

