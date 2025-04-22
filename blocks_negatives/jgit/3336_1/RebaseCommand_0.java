			Iterable<RevCommit> parents = new Git(repo).log().add(
					upstreamCommit).call();
			for (RevCommit parent : parents) {
				if (parent.equals(headCommit))
					break;
				if (parent.getParentCount() != 1)
					throw new JGitInternalException(
							JGitText.get().canOnlyCherryPickCommitsWithOneParent);
				cherryPickList.add(parent);
			}
