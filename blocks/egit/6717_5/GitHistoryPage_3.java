	private FollowFilter createFollowFilter(String path) {
		FollowFilter followFilter = FollowFilter.create(path);
		followFilter.setRenameCallback(new RenameCallback() {
			@Override
			public void renamed(DiffEntry entry) {
				if (fileViewerInterestingPaths != null) {
					fileViewerInterestingPaths.add(entry.getOldPath());
					fileViewerInterestingPaths.add(entry.getNewPath());
				}
			}
		});
		return followFilter;
	}

