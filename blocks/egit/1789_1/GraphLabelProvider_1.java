		if (columnIndex == 1 || columnIndex == 2) {
			final PersonIdent author = authorOf(c);
			if (author != null) {
				switch (columnIndex) {
				case 1:
					return author.getName()
							+ " <" + author.getEmailAddress() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
				case 2:
					return fmt.format(author.getWhen());
				}
			}
		}
		if (columnIndex == 4) {
			final PersonIdent committer = committerOf(c);
			if (committer != null) {
				return committer.getName()
						+ " <" + committer.getEmailAddress() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
