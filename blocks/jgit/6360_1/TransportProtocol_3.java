
	public Transport open(URIish uri)
			throws NotSupportedException
		throw new NotSupportedException(JGitText
				.get().transportNeedsRepository);
	}
