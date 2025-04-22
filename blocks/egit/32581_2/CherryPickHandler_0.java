					Integer.valueOf(commits.size()), repository.getBranch()));
			for (RevCommit c : commits)
				message.append(MessageFormat.format(
						UIText.CherryPickHandler_CommitFormat, reader
								.abbreviate(c.getId(), 7).name(), c
								.getShortMessage()));
