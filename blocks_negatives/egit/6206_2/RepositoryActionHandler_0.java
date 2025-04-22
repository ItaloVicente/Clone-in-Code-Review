				filter.setRenameCallback(new RenameCallback() {
					public void renamed(DiffEntry entry) {
						if (previousPath.get() == null)
							previousPath.set(entry.getOldPath());
					}
				});
