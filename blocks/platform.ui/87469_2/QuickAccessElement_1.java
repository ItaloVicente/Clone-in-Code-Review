		Pattern p;
		if (filter.contains("*") || filter.contains("?")) { //$NON-NLS-1$ //$NON-NLS-2$
			p = getWildcardsPattern(filter);
		} else {
			p = getWhitespacesPattern(filter);
		}
