	public boolean isEntryIgnored() {
		return isEntryIgnored(path
	}

	protected boolean isEntryIgnored(byte[] buf
		if (ignoreNode != null) {
			String relpath = TreeWalk.pathOf(buf
			boolean isDirectory = FileMode.TREE.equals(mode);
			switch (ignoreNode.isIgnored(relpath
			case IGNORED:
				return true;
			case NOT_IGNORED:
				return false;
			case CHECK_PARENT:
				break;
			}
		}
		if (parent instanceof WorkingTreeIterator)
			return ((WorkingTreeIterator) parent).isEntryIgnored(buf
		return false;
	}

