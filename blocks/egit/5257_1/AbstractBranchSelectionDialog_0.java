				Ref ref = repo.getRef(refName);
				if (ref == null)
					return false;
				node = new RefNode(localBranches, repo, ref);
			} else if (refName.startsWith(Constants.R_REMOTES)) {
				Ref ref = repo.getRef(refName);
				if (ref == null)
					return false;
				node = new RefNode(remoteBranches, repo, ref);
