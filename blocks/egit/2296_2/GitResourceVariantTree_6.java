		SubMonitor subMonitor = SubMonitor.convert(monitor);
		if (resource == null || resource.getLocation() == null) {
			subMonitor.done();
			return null;
		}

		subMonitor.beginTask(NLS.bind(
				CoreText.GitResourceVariantTree_fetchingVariant,
				resource.getName()), IProgressMonitor.UNKNOWN);
		try {
			return fetchVariant(resource, subMonitor);
		} finally {
			subMonitor.done();
		}
	}

	private IResourceVariant fetchVariant(IResource resource,
			IProgressMonitor monitor) throws TeamException {
		GitSynchronizeData gsd = gsds.getData(resource.getProject());
		if (gsd == null)
			return null;

		Repository repo = gsd.getRepository();
		String path = getPath(resource, repo);
		RevCommit revCommit = getRevCommit(gsd);
		if (revCommit == null)
			return null;

		if (path.length() == 0)
			return handleRepositoryRoot(resource, repo, revCommit);

		try {
			if (monitor.isCanceled())
				throw new OperationCanceledException();

			TreeWalk tw = initializeTreeWalk(repo, path);

			int nth = tw.addTree(revCommit.getTree());
			if (resource.getType() == IResource.FILE) {
				tw.setRecursive(true);
				if (tw.next() && !tw.getObjectId(nth).equals(zeroId()))
					return new GitBlobResourceVariant(repo, revCommit,
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
					return new GitFolderResourceVariant(repo, revCommit, objectId, path);
			}
		} catch (IOException e) {
			throw new TeamException(
					NLS.bind(
							CoreText.GitResourceVariantTree_couldNotFindResourceVariant,
							resource), e);
		}

