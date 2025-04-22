	private void writeGitPatchHeader(StringBuilder sb) {

		final SimpleDateFormat dtfmt;
		dtfmt = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US); //$NON-NLS-1$
		dtfmt.setTimeZone(commit.getAuthorIdent().getTimeZone());
		sb.append("From").append(" ") //$NON-NLS-1$ //$NON-NLS-2$
				.append(commit.getId().getName()).append(" ") //$NON-NLS-1$
				.append(dtfmt.format(Long.valueOf(System.currentTimeMillis())))
				.append("\n"); //$NON-NLS-1$
		sb.append("From") //$NON-NLS-1$
				.append(": ") //$NON-NLS-1$
				.append(commit.getAuthorIdent().getName())
				.append(" <").append(commit.getAuthorIdent().getEmailAddress()) //$NON-NLS-1$
				.append(">\n"); //$NON-NLS-1$
		sb.append("Date").append(": ") //$NON-NLS-1$ //$NON-NLS-2$
				.append(dtfmt.format(commit.getAuthorIdent().getWhen()))
				.append("\n"); //$NON-NLS-1$
		sb.append("Subject").append(": [PATCH] ") //$NON-NLS-1$ //$NON-NLS-2$
				.append(commit.getShortMessage());

		String message = commit.getFullMessage().substring(
				commit.getShortMessage().length());
		sb.append(message).append("\n\n"); //$NON-NLS-1$
	}

