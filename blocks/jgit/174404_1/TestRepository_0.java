		public CommitBuilder addSymlink(String path
			return edit(new PathEdit(path) {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.SYMLINK);
					ent.setObjectId(id);
				}
			});
		}

