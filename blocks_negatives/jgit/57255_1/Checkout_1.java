			if (createBranch || orphan)
				outw.println(MessageFormat.format(
						CLIText.get().switchedToNewBranch, name));
			else
				outw.println(MessageFormat.format(
						CLIText.get().switchedToBranch,
						Repository.shortenRefName(ref.getName())));
		} catch (RefNotFoundException e) {
			outw.println(MessageFormat.format(
					CLIText.get().pathspecDidNotMatch,
					name));
		} catch (RefAlreadyExistsException e) {
			throw die(MessageFormat.format(CLIText.get().branchAlreadyExists,
					name));
		} catch (CheckoutConflictException e) {
			outw.println(CLIText.get().checkoutConflict);
			for (String path : e.getConflictingPaths())
				outw.println(MessageFormat.format(
						CLIText.get().checkoutConflictPathLine, path));
