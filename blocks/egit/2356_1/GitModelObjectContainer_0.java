		if (name == null) {
			StringBuilder sb = new StringBuilder();
			sb.append("["); //$NON-NLS-1$
			sb.append(baseCommit.getAuthorIdent().getName());
			sb.append("] ("); //$NON-NLS-1$
			sb.append(DATE_FORMAT.format(baseCommit.getAuthorIdent().getWhen()));
			sb.append(") "); //$NON-NLS-1$
			sb.append(baseCommit.getShortMessage());
			name = sb.toString();
		}
