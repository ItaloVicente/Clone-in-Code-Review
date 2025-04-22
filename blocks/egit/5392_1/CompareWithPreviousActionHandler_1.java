				if (path.length() > 0) {
					FollowFilter filter = FollowFilter.create(path);
					filter.setRenameCallback(new RenameCallback() {

						public void renamed(DiffEntry entry) {
							if (previousPath.get() == null)
								previousPath.set(entry.getOldPath());
						}
					});
					rw.setTreeFilter(filter);
				}

