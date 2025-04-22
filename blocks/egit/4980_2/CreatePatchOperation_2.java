		String template = headerFormat.getTemplate();
		String[] segments = template.split("\\$\\{"); //$NON-NLS-1$
		Stack<String> evaluated = new Stack<String>();
		evaluated.add(segments[0]);

		for (int i = 1; i < segments.length; i++) {
			String segment = segments[i];
			String value = null;
			int brace = segment.indexOf('}');
			if (brace > 0) {
				String keyword = segment.substring(0, brace);
				keyword = keyword.toUpperCase().replaceAll(" ", "_"); //$NON-NLS-1$ //$NON-NLS-2$
				value = processKeyword(commit, DiffHeaderKeyword.valueOf(keyword));
			}

			String trailingCharacters = segment.substring(brace + 1);
			if (value != null) {
				evaluated.add(value);
				evaluated.add(trailingCharacters);
			} else if (!evaluated.isEmpty()) {
				evaluated.add(trailingCharacters);
			}
		}
		StringBuffer buffer = new StringBuffer();
		for (String string : evaluated) {
			buffer.append(string);
		}

		sb.append(buffer);
	}

	private static String processKeyword(RevCommit commit, DiffHeaderKeyword keyword) {
		final SimpleDateFormat dtfmt = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US); //$NON-NLS-1$
		switch (keyword) {
		case SHA1:
			return commit.getId().getName();
		case AUTHOR:
			return commit.getAuthorIdent().getName()
					+ " <" + commit.getAuthorIdent().getEmailAddress() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
		case AUTHOR_DATE:
			dtfmt.setTimeZone(commit.getAuthorIdent().getTimeZone());
			return dtfmt.format(commit.getAuthorIdent().getWhen());
		case DATE:
			return dtfmt.format(Long.valueOf(System.currentTimeMillis()));
		case TITLE_LINE:
			return commit.getShortMessage();
		case FULL_COMMIT_MESSAGE:
			return commit.getFullMessage().substring(
					commit.getShortMessage().length());
		default:
			return null;
		}
