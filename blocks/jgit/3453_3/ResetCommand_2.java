	public ResetCommand addPath(String file) {
		if (mode != null)
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().illegalCombinationOfArguments
					"[--mixed | --soft | --hard]"));
		filepaths.add(file);
		return this;
	}

	private void resetIndexForPaths(RevCommit commit) {
		DirCache dc = null;
		final DirCacheEditor edit;
		try {
			dc = repo.lockDirCache();
			edit = dc.editor();

			final TreeWalk tw = new TreeWalk(repo);
			tw.addTree(new DirCacheIterator(dc));
			tw.addTree(commit.getTree());
			tw.setFilter(PathFilterGroup.createFromStrings(filepaths));

			while (tw.next()) {
				final String path = tw.getPathString();
				final CanonicalTreeParser tree = tw.getTree(1
						CanonicalTreeParser.class);
				if (tree == null)
					edit.add(new DirCacheEditor.DeletePath(path));
				else {
					edit.add(new DirCacheEditor.PathEdit(path) {
						@Override
						public void apply(DirCacheEntry ent) {
							ent.setFileMode(tree.getEntryFileMode());
							ent.setObjectId(tree.getEntryObjectId());
							ent.setLastModified(0);
						}
					});
				}
			}

			edit.commit();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (dc != null)
				dc.unlock();
		}
	}

