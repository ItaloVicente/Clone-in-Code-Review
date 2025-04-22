				String ref = RepositoryStateCache.INSTANCE
						.getFullBranchName(repository);
				String menuLabel = UIText.PushMenu_PushHEAD;
				if (ref != null && ref.startsWith(Constants.R_HEADS)) {
					menuLabel = NLS.bind(UIText.PushMenu_PushBranch,
							Repository.shortenRefName(ref));
