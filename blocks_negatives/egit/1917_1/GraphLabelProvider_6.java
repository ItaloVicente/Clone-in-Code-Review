
		final PersonIdent author = authorOf(c);
		if (author != null) {
			switch (columnIndex) {
			case 1:
			case 2:
				return fmt.format(author.getWhen());
