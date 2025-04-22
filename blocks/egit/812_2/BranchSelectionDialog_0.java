			if (refName.startsWith(Constants.R_HEADS)) {
				Ref ref = this.repo.getRef(refName);
				node = new RefNode(localBranches, this.repo, ref);
			} else {
				String mappedRef = Activator.getDefault().getRepositoryUtil()
						.mapCommitToRef(this.repo, refName, false);
				if (mappedRef != null
						&& mappedRef.startsWith(Constants.R_REMOTES)) {
					Ref ref = this.repo.getRef(mappedRef);
					node = new RefNode(remoteBranches, this.repo, ref);
				} else if (mappedRef != null
						&& mappedRef.startsWith(Constants.R_TAGS)) {
					Ref ref = this.repo.getRef(mappedRef);
					node = new TagNode(tags, this.repo, ref);
				} else {
					return false;
				}
			}
