		} else if (state == RepositoryState.SAFE && message == null) {
			try {
				message = repo.readSquashCommitMsg();
				if (message != null)
			} catch (IOException e) {
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().exceptionOccurredDuringReadingOfGIT_DIR
						Constants.MERGE_MSG
			}

