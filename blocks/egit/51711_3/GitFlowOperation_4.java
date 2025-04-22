
			MergeResult result = mergeOperation.getResult();
			if (squash && result.getMergeStatus().isSuccessful()) {
				CommitCommand commitCommand = Git.wrap(repository.getRepository()).commit();
				commitCommand.call();
			}
			return result;
