	private String wcFilter;
	private Pattern wcPattern;

	private Pattern getWildcardsPattern(String filter) {
		if (wcPattern == null || !filter.equals(wcFilter)) {
			wcFilter = filter;
			String sFilter = filter.replaceFirst(WS_START, EMPTY_STR).replaceFirst(WS_END, EMPTY_STR)
					.replaceAll(PAR_START, ONE_CHAR).replaceAll(PAR_END, ONE_CHAR);
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<sFilter.length(); i++) {
				char c = sFilter.charAt(i);
				if(c=='*'||c=='?') {
					sb.append(").").append(c).append("("); //$NON-NLS-1$ //$NON-NLS-2$
				} else {
					sb.append(c);
				}
			}
			sFilter = String.format(".*(%s).*", sb.toString()); //$NON-NLS-1$
			wcPattern = Pattern.compile(sFilter, Pattern.CASE_INSENSITIVE);
		}
		return wcPattern;
	}

	int i = 0;
