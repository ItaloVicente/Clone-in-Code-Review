		if (path.length() == 0)
			return handleRepositoryRoot(resource, repo, revCommit);

		try {
			if (monitor.isCanceled())
				throw new OperationCanceledException();

			TreeWalk tw = initializeTreeWalk(repo, path);

			int nth = tw.addTree(revCommit.getTree());
			IResourceVariant variant = null;
			if (resource.getType() == IResource.FILE) {
				tw.setRecursive(true);
				if (tw.next() && !tw.getObjectId(nth).equals(zeroId()))
					variant = new GitRemoteFile(repo, revCommit,
							tw.getObjectId(nth), path);
			} else {
				while (tw.next() && !path.equals(tw.getPathString())) {
					if (monitor.isCanceled())
						throw new OperationCanceledException();

					if (tw.isSubtree())
						tw.enterSubtree();
				}

				ObjectId objectId = tw.getObjectId(nth);
				if (!objectId.equals(zeroId()))
					variant = new GitRemoteFolder(repo, revCommit, objectId, path);
			}
			if (variant != null)
				cache.put(resource, variant);
			return variant;
		} catch (IOException e) {
			throw new TeamException(
					NLS.bind(
							CoreText.GitResourceVariantTree_couldNotFindResourceVariant,
							resource), e);
