
	private static class IgnoreFilter extends TreeFilter {

		@Override
		public boolean include(TreeWalk walker) throws MissingObjectException
				IncorrectObjectTypeException
			WorkingTreeIterator workingTreeIterator = walker.getTree(WORKDIR
					WorkingTreeIterator.class);
			if (workingTreeIterator != null) {
				boolean ignored = workingTreeIterator.isEntryIgnored();
				return !ignored;
			}
			else
				return true;
		}

		@Override
		public boolean shouldBeRecursive() {
			return true;
		}

		@Override
		public TreeFilter clone() {
			return new IgnoreFilter();
		}
	}
