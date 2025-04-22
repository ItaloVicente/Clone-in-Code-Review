
	private static class FollowingSWTWalk extends SWTWalk {
		private RenameCallback callback = new RenameCallback() {
			@Override
			public void renamed(DiffEntry entry) {
				renamedEntries.add(entry);
			}
		};
		private List<DiffEntry> renamedEntries = new ArrayList<DiffEntry>(4);

		FollowingSWTWalk(Repository repo) {
			super(repo);
		}

		@Override
		protected void reset(int aRetainFlags) {
			super.reset(aRetainFlags);
			renamedEntries.clear();
		}

		public List<DiffEntry> getRenamedEntries() {
			return new ArrayList<DiffEntry>(renamedEntries);
		}

		@Override
		public void setTreeFilter(TreeFilter filter) {
			super.setTreeFilter(filter);
			if (filter instanceof FollowFilter) {
				FollowFilter followFilter = (FollowFilter) filter;
				RenameCallback renameCallback = followFilter.getRenameCallback();
				if (renameCallback == null) {
					followFilter.setRenameCallback(callback);
				}
			}
		}
	}
