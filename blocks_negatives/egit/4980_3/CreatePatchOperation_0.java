		final SimpleDateFormat dtfmt;
		dtfmt = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US); //$NON-NLS-1$
		dtfmt.setTimeZone(commit.getAuthorIdent().getTimeZone());
				.append(dtfmt.format(Long.valueOf(System.currentTimeMillis())))
				.append(commit.getAuthorIdent().getName())
				.append(dtfmt.format(commit.getAuthorIdent().getWhen()))
				.append(commit.getShortMessage());

		String message = commit.getFullMessage().substring(
				commit.getShortMessage().length());
