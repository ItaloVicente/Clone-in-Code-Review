		PersonIdent author;
		if (blame.getSourceCommit(line) == null) {
			author = dummyDate;
		} else {
			author = blame.getSourceAuthor(line);
		}
