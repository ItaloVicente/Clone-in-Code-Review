				Repository repo = git.getRepository();
				ObjectId refCommitId = sha1(uri
				if (refCommitId == null) {
					throw new InvalidRefNameException(MessageFormat
							.format(JGitText.get().refNotResolved
				}
				RevCommit commit = repo.parseCommit(refCommitId);
				TreeWalk tw = TreeWalk.forPath(repo

				return new RemoteFile(
						tw.getObjectReader().open(tw.getObjectId(0))
								.getCachedBytes(Integer.MAX_VALUE)
						tw.getFileMode(0));
