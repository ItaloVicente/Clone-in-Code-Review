		} else {
			String msg = NLS.bind(UIText.GitDocument_errorResolveQuickdiff,
					new Object[] { baseline, resource, repository });
			Activator.logError(msg, new Throwable());
			setResolved(null, null, null, ""); //$NON-NLS-1$
			return;
		}
		RevWalk rw = new RevWalk(repository);
		RevCommit baselineCommit;
		try {
			baselineCommit = rw.parseCommit(commitId);
		} catch (IOException err) {
			String msg = NLS.bind(UIText.GitDocument_errorLoadCommit,
					new Object[] { commitId, baseline, resource, repository });
			Activator.logError(msg, err);
			setResolved(null, null, null, ""); //$NON-NLS-1$
			return;
		} finally {
			rw.release();
		}
		RevTree treeId = baselineCommit.getTree();
		if (treeId.equals(lastTree)) {
			if (GitTraceLocation.QUICKDIFF.isActive())
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.QUICKDIFF.getLocation(),
			return;
		}
		Tree baselineTree = repository.mapTree(treeId);
		if (baselineTree == null) {
			String msg = NLS.bind(UIText.GitDocument_errorLoadTree,
					new Object[] { treeId, baseline, resource, repository });
			Activator.logError(msg, new Throwable());
			setResolved(null, null, null, ""); //$NON-NLS-1$
			return;
		}
		TreeEntry blobEntry = baselineTree.findBlobMember(gitPath);
		if (blobEntry != null && !blobEntry.getId().equals(lastBlob)) {
			if (GitTraceLocation.QUICKDIFF.isActive())
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.QUICKDIFF.getLocation(),
			ObjectLoader loader = repository.open(blobEntry.getId(),
					Constants.OBJ_BLOB);
			byte[] bytes = loader.getBytes();
			String charset;
			charset = CompareUtils.getResourceEncoding(resource);
			String s = new String(bytes, charset);
			setResolved(commitId, baselineTree.getId(), blobEntry.getId(), s);
			if (GitTraceLocation.QUICKDIFF.isActive())
				GitTraceLocation
						.getTrace()
						.trace(
								GitTraceLocation.QUICKDIFF.getLocation(),
								"(GitDocument) has reference doc, size=" + s.length() + " bytes"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			if (blobEntry == null)
