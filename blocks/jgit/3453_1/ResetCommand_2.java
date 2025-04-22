	public ResetCommand addFile(String file) {
		if (mode != null)
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().illegalCombinationOfArguments
					"[--mixed | --soft | --hard]"));
		filepaths.add(file);
		return this;
	}

	private void resetIndexForPaths(RevCommit commit) {
		final DirCacheEditor edit;
		try {
			edit = repo.lockDirCache().editor();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		for (String path : filepaths) {
			try {
				final TreeWalk tw = TreeWalk.forPath(repo
						commit.getTree());
				if (tw != null) {
					edit.add(new DirCacheEditor.PathEdit(path) {
						@Override
						public void apply(DirCacheEntry ent) {
							ent.setFileMode(tw.getFileMode(0));
							ent.setObjectId(tw.getObjectId(0));
						}
					});
				} else {
					edit.add(new DirCacheEditor.DeletePath(path));
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		try {
			edit.commit();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

