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
		List<String> files = new LinkedList<String>();

		TreeWalk treeWalk = new TreeWalk(revWalk.getObjectReader());
		RevCommit head = revWalk.parseCommit(repo.getRef(Constants.HEAD)
				.getObjectId());
		treeWalk.addTree(head.getTree());
		treeWalk.setFilter(PathFilterGroup.createFromStrings(paths));
		while (treeWalk.next())
			files.add(treeWalk.getPathString());

		DirCache dc = repo.lockDirCache();
		if (startCommit != null || startPoint != null) {
			DirCacheEditor editor = dc.editor();
			TreeWalk startWalk = new TreeWalk(revWalk.getObjectReader());
			startWalk.setFilter(treeWalk.getFilter());
			startWalk.addTree(revWalk.parseCommit(getStartPoint()).getTree());
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

		try {
			File workTree = repo.getWorkTree();
			for (String file : files)
				DirCacheCheckout.checkoutEntry(repo
						dc.getEntry(file));
		} finally {
			dc.unlock();
		}
		return this;
