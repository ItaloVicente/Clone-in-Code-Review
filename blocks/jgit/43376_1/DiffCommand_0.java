	private AbstractTreeIterator getAbstractTreeIterator(String rev)
			throws GitAPIException
		if (head == null)
			throw new NoHeadException(JGitText.get().cannotReadTree);
		CanonicalTreeParser p = new CanonicalTreeParser();
		ObjectReader reader = repo.newObjectReader();
		try {
			p.reset(reader
		} finally {
			reader.release();
		}
		return p;
	}

