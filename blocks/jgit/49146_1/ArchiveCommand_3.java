		try {
			try (TreeWalk walk = new TreeWalk(repo);
					RevWalk rw = new RevWalk(walk.getObjectReader())) {
				T outa = fmt.createArchiveOutputStream(out
				MutableObjectId idBuf = new MutableObjectId();
				ObjectReader reader = walk.getObjectReader();
