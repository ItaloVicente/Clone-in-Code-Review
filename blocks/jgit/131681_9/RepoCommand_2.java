		RemoteFile readFileWithMode(Repository repo
				throws GitAPIException
			ObjectId refCommitId = repo.resolve(ref);
			if (refCommitId == null) {
				throw new InvalidRefNameException(
						MessageFormat.format(JGitText.get().refNotResolved
								ref));
