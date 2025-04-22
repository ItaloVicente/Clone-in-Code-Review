		try {
			return bigIntFmt.format(firstNonNull(stats.get(key), "")); //$NON-NLS-1$
		} catch (IllegalArgumentException e) {
			Activator.handleError(e.getMessage(), e, false);
			return UIText.RepositoryStatistics_unknown;
		}
