
			int squashCount = 0;
			boolean squash = false;
			String squashCommitMessage = repo.readSquashCommitMsg();
			StringBuilder squashMessageBuilder = new StringBuilder(
					squashCommitMessage == null ? "" : squashCommitMessage);
