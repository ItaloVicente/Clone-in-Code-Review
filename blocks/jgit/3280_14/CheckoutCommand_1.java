		this.paths = new LinkedList<String>();
	}

	public CheckoutCommand addPath(String path) {
		checkCallable();
		this.paths.add(path);
		return this;
	}

	public CheckoutCommand clearPaths() {
		checkCallable();
		this.paths.clear();
		return this;
	}

	protected CheckoutCommand checkoutPaths() throws IOException
			RefNotFoundException {
		RevWalk revWalk = new RevWalk(repo);
		DirCache dc = repo.lockDirCache();
		try {
			TreeWalk treeWalk = new TreeWalk(revWalk.getObjectReader());
			treeWalk.addTree(new DirCacheIterator(dc));
			treeWalk.setFilter(PathFilterGroup.createFromStrings(paths));
			List<String> files = new LinkedList<String>();
			while (treeWalk.next())
				files.add(treeWalk.getPathString());

			if (startCommit != null || startPoint != null) {
				DirCacheEditor editor = dc.editor();
				TreeWalk startWalk = new TreeWalk(revWalk.getObjectReader());
				startWalk.setFilter(treeWalk.getFilter());
				startWalk.addTree(revWalk.parseCommit(getStartPoint())
						.getTree());
				while (startWalk.next()) {
					final ObjectId blobId = startWalk.getObjectId(0);
					editor.add(new PathEdit(startWalk.getPathString()) {

						public void apply(DirCacheEntry ent) {
							ent.setObjectId(blobId);
						}
					});
				}
				editor.commit();
			}

			File workTree = repo.getWorkTree();
			for (String file : files)
				DirCacheCheckout.checkoutEntry(repo
						dc.getEntry(file));
		} finally {
			dc.unlock();
			revWalk.release();
		}
		return this;
