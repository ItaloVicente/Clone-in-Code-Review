				Repository repo = git.getRepository();

				ObjectId refCommitId = sha1(uri
				RevCommit commit = repo.parseCommit(refCommitId);
				TreeWalk tw = TreeWalk.forPath(repo
				tw.setRecursive(true);

				return new RemoteFile(
						readFileFromRepo(git.getRepository()
						tw.getFileMode(0));
