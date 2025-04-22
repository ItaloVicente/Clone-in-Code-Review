			RevCommit ancestorCommit = RevUtils.getCommonAncestor(repo, srcRev,
					dstRev);
			RevCommitList<RevCommit> commits = getRevCommits(ancestorCommit, dstRev);
			commits.addAll(getRevCommits(ancestorCommit, srcRev));

			for (RevCommit commit : commits)
				result.add(new GitModelCommit(this, commit));
