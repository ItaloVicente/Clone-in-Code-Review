		try {
			tw.addTree(revCommit.getTree());
			if (resource.getType() == IResource.FILE) {
				tw.setRecursive(true);
				if (tw.next())
					return new GitBlobResourceVariant(repo, revCommit, path);
			} else
				return new GitFolderResourceVariant(repo, revCommit, path);
		} catch (IOException e) {
			throw new TeamException(
					NLS.bind(
							CoreText.GitResourceVariantTree_couldNotFindResourceVariant,
							resource), e);
		}
