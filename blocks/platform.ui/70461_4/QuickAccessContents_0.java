	Pattern categoryPattern;

	protected Pattern getCategoryPattern() {
		if (categoryPattern == null) {
			StringBuilder sb = new StringBuilder();
			sb.append("^(:?"); //$NON-NLS-1$
			for (int i = 0; i < providers.length; i++) {
				if (i != 0)
					sb.append("|"); //$NON-NLS-1$
				sb.append(providers[i].getName());
			}
			sb.append("):\\s?(.*)"); //$NON-NLS-1$
			String regex = sb.toString();
			categoryPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		}
		return categoryPattern;
	}

