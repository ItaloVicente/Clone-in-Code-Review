				RevWalk walk = new RevWalk(repo);
				RevCommit tip = walk.parseCommit(repo.resolve(Constants.HEAD));
				for (String branchName : branchNames) {
					if (branchName == null)
						continue;
					Ref currentRef = repo.getRef(branchName);
					if (currentRef == null)
						continue;
