				Repository repo = git.getRepository();

				ObjectId refCommitId = sha1(uri
				RevCommit commit = repo.parseCommit(refCommitId);
				TreeWalk tw = TreeWalk.forPath(repo

				return new RemoteFile(
						tw.getObjectReader().open(tw.getObjectId(0)).getBytes(
								Integer.MAX_VALUE)
						tw.getFileMode(0));
