			Repository repo = getRepository(event);
			RevCommit commonAncestor = null;
			try {
				commonAncestor = RevUtils.getCommonAncestor(repo, commit1, commit2);
			} catch (IOException e) {
				Activator.logError(NLS.bind(UIText.CompareWithWorkingTreeHandler_errorCommonAncestor,
						commit1.getName(), commit2.getName()), e);
			}

