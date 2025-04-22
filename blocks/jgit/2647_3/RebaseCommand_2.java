			if (result.getStatus().equals(Status.FAILED)) {
				RevCommit head = walk.parseCommit(repo.resolve(Constants.HEAD));
				dco = new DirCacheCheckout(repo
						repo.lockDirCache()
			} else {
				dco = new DirCacheCheckout(repo
						commit.getTree());
			}
