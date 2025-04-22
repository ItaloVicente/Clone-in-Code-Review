			RevCommit commonAncestor = null;
			try {
				Repository repo = getRepository(event);
				commonAncestor = RevUtils.getCommonAncestor(repo, repo.resolve(Constants.HEAD), commit);
			} catch (IOException e) {
				Activator.logError(NLS.bind(UIText.CompareWithWorkingTreeHandler_errorCommonAncestor,
						Constants.HEAD, commit.getName()), e);
			}
