
	private AbstractTreeIterator getRevision(String name)
			throws IOException
		if (head == null) {
			throw new NoHeadException(JGitText.get().cannotReadTree);
		}
		CanonicalTreeParser p = new CanonicalTreeParser();
		try (ObjectReader reader = repo.newObjectReader()) {
			p.reset(reader
		}
		return p;
	}
