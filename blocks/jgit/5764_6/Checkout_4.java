		try {
			String oldBranch = db.getBranch();
			Ref ref = command.call();
			if (Repository.shortenRefName(ref.getName()).equals(oldBranch)) {
				out.println(MessageFormat.format(CLIText.get().alreadyOnBranch
						name));
				return;
			}
			if (createBranch)
				out.println(MessageFormat.format(
						CLIText.get().switchedToNewBranch
						Repository.shortenRefName(ref.getName())));
			else
				out.println(MessageFormat.format(
						CLIText.get().switchedToBranch
						Repository.shortenRefName(ref.getName())));
		} catch (RefNotFoundException e) {
			throw die(MessageFormat.format(CLIText.get().pathspecDidNotMatch
					name));
		} catch (RefAlreadyExistsException e) {
			throw die(MessageFormat.format(CLIText.get().branchAlreadyExists
					name));
		}
