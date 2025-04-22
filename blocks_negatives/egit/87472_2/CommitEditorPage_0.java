		PersonIdent author = commit.getAuthorIdent();
		if (author != null)
			message = replaceSignedOffByLine(message, author);
		PersonIdent committer = commit.getCommitterIdent();
		if (committer != null)
			message = replaceSignedOffByLine(message, committer);

