	public IResourceVariant[] getMembers(IProgressMonitor progress)
			throws IOException {
		if (members != null)
			try {
				return members;
			} finally {
				progress.done();
			}

		IProgressMonitor monitor = SubMonitor.convert(progress);
		monitor.beginTask(
				NLS.bind(CoreText.GitFolderResourceVariant_fetchingMembers, this),
				tw.getTreeCount());

		Repository repo = getRepository();
		List<IResourceVariant> result = new ArrayList<IResourceVariant>();

		try {
			while (tw.next()) {
				String path = getPath() + "/" + new String(tw.getRawPath()); //$NON-NLS-1$
				if (tw.isSubtree())
					result.add(new GitFolderResourceVariant(repo, getRevCommit(),
							path));
				else
					result.add(new GitBlobResourceVariant(repo, getRevCommit(),
							path));
				monitor.worked(1);
			}

			members = result.toArray(new IResourceVariant[result.size()]);
			return members;
		} finally {
			monitor.done();
		}
