		static RemoteFile readFileWithMode(Repository repo
				String path) throws GitAPIException
			ObjectId refCommitId = repo.resolve(ref);
			if (refCommitId == null) {
				throw new InvalidRefNameException(
						MessageFormat.format(JGitText.get().refNotResolved
								ref));
