		long time = System.currentTimeMillis() - start;
		return formatSummary(gitDirs, dirCount, time);
	}

	private String formatSummary(final Set<Path> gitDirs, final int[] dirCount,
			long time) {
		String fmtTime = ""; //$NON-NLS-1$
		if (time < 1000) {
			fmtTime = String.format("%dms", Long.valueOf(time)); //$NON-NLS-1$
		} else if (time < 10000) {
			fmtTime = String.format("%.1fs", Double.valueOf(time / 1000.0)); //$NON-NLS-1$
		} else {
			fmtTime = String.format("%ds", Long.valueOf(time / 1000)); //$NON-NLS-1$
		}
		return MessageFormat.format(UIText.RepositorySearchDialog_SearchResult,
				Integer.valueOf(gitDirs.size()), Integer.valueOf(dirCount[0]),
				fmtTime);
