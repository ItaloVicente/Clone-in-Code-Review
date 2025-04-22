			if (commits.size() == 1)
				message = MessageFormat.format(
						UIText.CherryPickHandler_ConfirmMessage, commits.get(0)
								.abbreviate(7).name(), repository.getBranch());
			else
				message = MessageFormat.format(
						UIText.CherryPickHandler_ConfirmMessage_Multi,
								Integer.valueOf(commits.size()),
								repository.getBranch());
