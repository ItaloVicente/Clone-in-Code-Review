		try (Git git = new Git(db)) {
			CheckoutCommand command = git.checkout();
			if (paths.size() > 0) {
				command.setStartPoint(name);
				for (String path : paths)
					command.addPath(path);
			} else {
				command.setCreateBranch(createBranch);
				command.setName(name);
				command.setForce(force);
				command.setOrphan(orphan);
			}
			try {
				String oldBranch = db.getBranch();
				Ref ref = command.call();
				if (ref == null)
					return;
				if (Repository.shortenRefName(ref.getName()).equals(oldBranch)) {
					outw.println(MessageFormat.format(
							CLIText.get().alreadyOnBranch
							name));
					return;
				}
				if (createBranch || orphan)
					outw.println(MessageFormat.format(
							CLIText.get().switchedToNewBranch
				else
					outw.println(MessageFormat.format(
							CLIText.get().switchedToBranch
							Repository.shortenRefName(ref.getName())));
			} catch (RefNotFoundException e) {
