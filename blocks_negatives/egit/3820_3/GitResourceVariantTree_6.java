			Repository repo, RevCommit revCommit) throws TeamException {
		try {
			return new GitFolderResourceVariant(repo, revCommit,
					revCommit.getTree(), resource.getLocation().toString());
		} catch (IOException e) {
			throw new TeamException(
					NLS.bind(
							CoreText.GitResourceVariantTree_couldNotFindResourceVariant,
							resource), e);
		}
