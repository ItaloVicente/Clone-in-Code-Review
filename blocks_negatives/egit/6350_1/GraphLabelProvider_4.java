		if (columnIndex == 0)
			return c.getId().abbreviate(7).name();
		if (columnIndex == 1)
			return c.getShortMessage();
		if (columnIndex == 2 || columnIndex == 3) {
			final PersonIdent author = authorOf(c);
			if (author != null)
				switch (columnIndex) {
				case 2:
					if (showEmail)
						return author.getName()
					else
						return author.getName();
				case 3:
					return getDateFormatter().formatDate(author);
				}
		}
		if (columnIndex == 4 || columnIndex == 5) {
			final PersonIdent committer = committerOf(c);
			if (committer != null)
				switch (columnIndex) {
				case 4:
					if (showEmail)
						return committer.getName()
					else
						return committer.getName();
				case 5:
					return getDateFormatter().formatDate(committer);
				}
		}

	}

	private GitDateFormatter getDateFormatter() {
		if (dateFormatter == null)
			dateFormatter = new GitDateFormatter(format);
		return dateFormatter;
	}

	private PersonIdent authorOf(final RevCommit c) {
		if (lastCommit != c) {
			lastCommit = c;
			lastAuthor = c.getAuthorIdent();
			lastCommitter = c.getCommitterIdent();
		}
		return lastAuthor;
	}

	private PersonIdent committerOf(final RevCommit c) {
		if (lastCommit != c) {
			lastCommit = c;
			lastAuthor = c.getAuthorIdent();
			lastCommitter = c.getCommitterIdent();
		}
		return lastCommitter;
	}

	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	/**
	 * @param relative {@code true} if the date column should show relative dates
	 */
	public void setRelativeDate(boolean relative) {
		dateFormatter = null;
		if (relative)
			format = Format.RELATIVE;
		else
			format = Format.LOCALE;
	}

	/**
	 * @param showEmail true to show e-mail addresses, false otherwise
	 */
	public void setShowEmailAddresses(boolean showEmail) {
		this.showEmail = showEmail;
