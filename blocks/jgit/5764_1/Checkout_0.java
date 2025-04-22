		Ref ref = command.call();

		if (createBranch)
			out.println(MessageFormat.format(CLIText.get().switchedToNewBranch
					Repository.shortenRefName(ref.getName())));
		else
			out.println(MessageFormat.format(CLIText.get().switchedToBranch
					Repository.shortenRefName(ref.getName())));
