	private Image handleSymlinkDecoration(StagingEntry entry, Image base) {
		Image image = base;
		try {
			Repository repository = entry.getRepository();
			FS fs = repository.getFS();
			if (!fs.supportsSymlinks()) {
				return image;
			}
			State state = entry.getState();
			if (State.REMOVED == state || State.MISSING == state
					|| State.MISSING_AND_CHANGED == state) {
				ObjectId headCommitId = repository.resolve(
						Constants.HEAD);
				if (headCommitId != null) {
					RevWalk revWalk = new RevWalk(repository);
					RevCommit headCommit = revWalk.parseCommit(headCommitId);
					RevTree headTree = headCommit.getTree();
					TreeWalk tw = TreeWalk.forPath(repository, entry.getPath(),
							headTree);
					if (FileMode.SYMLINK == tw.getFileMode(0))
						image = addSymlinkDecorationToImage(image);
				}
			} else {
				if (fs.isSymLink(entry.getLocation().toFile()))
					image = addSymlinkDecorationToImage(image);
			}
		} catch (IOException e) {
			Activator.error(UIText.StagingViewLabelProvider_SymlinkError, e);
		}
		return image;
	}

	private Image addSymlinkDecorationToImage(Image base) {
		DecorationOverlayIcon decorated = new DecorationOverlayIcon(base,
				SYMLINK, IDecoration.TOP_RIGHT);
		return (Image) this.resourceManager.get(decorated);
	}

