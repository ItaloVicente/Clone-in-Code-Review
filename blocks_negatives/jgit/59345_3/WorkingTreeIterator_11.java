		return isEntryIgnored(pathLen);
	}

	/**
	 * Determine if the entry path is ignored by an ignore rule.
	 *
	 * @param pLen
	 *            the length of the path in the path buffer.
	 * @return true if the entry is ignored by an ignore rule.
	 * @throws IOException
	 *             a relevant ignore rule file exists but cannot be read.
	 */
	protected boolean isEntryIgnored(final int pLen) throws IOException {
		return isEntryIgnored(pLen, mode, false);
	}

	/**
	 * Determine if the entry path is ignored by an ignore rule. Consider
	 * possible rule negation from child iterator.
	 *
	 * @param pLen
	 *            the length of the path in the path buffer.
	 * @param fileMode
	 *            the original iterator file mode
	 * @param negatePrevious
	 *            true if the previous matching iterator rule was negation
	 * @return true if the entry is ignored by an ignore rule.
	 * @throws IOException
	 *             a relevant ignore rule file exists but cannot be read.
	 */
	private boolean isEntryIgnored(final int pLen, int fileMode,
			boolean negatePrevious)
			throws IOException {
		IgnoreNode rules = getIgnoreNode();
		if (rules != null) {
			int pOff = pathOffset;
			if (0 < pOff)
				pOff--;
			String p = TreeWalk.pathOf(path, pOff, pLen);
			switch (rules.isIgnored(p, FileMode.TREE.equals(fileMode),
					negatePrevious)) {
			case IGNORED:
				return true;
			case NOT_IGNORED:
				return false;
			case CHECK_PARENT:
				negatePrevious = false;
				break;
			case CHECK_PARENT_NEGATE_FIRST_MATCH:
				negatePrevious = true;
				break;
			}
		}
		if (parent instanceof WorkingTreeIterator)
			return ((WorkingTreeIterator) parent).isEntryIgnored(pLen, fileMode,
					negatePrevious);
		return false;
	}

	private IgnoreNode getIgnoreNode() throws IOException {
		if (ignoreNode instanceof PerDirectoryIgnoreNode)
			ignoreNode = ((PerDirectoryIgnoreNode) ignoreNode).load();
		return ignoreNode;
