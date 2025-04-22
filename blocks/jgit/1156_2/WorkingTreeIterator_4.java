	public boolean isEntryIgnored() throws IOException {
		return isEntryIgnored(pathLen);
	}

	protected boolean isEntryIgnored(final int pLen) throws IOException {
		IgnoreNode rules = getIgnoreNode();
		if (rules != null) {
			String p = TreeWalk.pathOf(path
			switch (rules.isIgnored(p
			case IGNORED:
				return true;
			case NOT_IGNORED:
				return false;
			case CHECK_PARENT:
				break;
			}
		}
		if (parent instanceof WorkingTreeIterator)
			return ((WorkingTreeIterator) parent).isEntryIgnored(pLen);
		return false;
	}

	private IgnoreNode getIgnoreNode() throws IOException {
		if (ignoreNode instanceof PerDirectoryIgnoreNode)
			ignoreNode = ((PerDirectoryIgnoreNode) ignoreNode).load();
		return ignoreNode;
	}

