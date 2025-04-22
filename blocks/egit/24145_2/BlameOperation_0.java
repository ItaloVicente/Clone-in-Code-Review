		else {
			try {
				command.setStartCommit(repository.resolve(Constants.HEAD));
			} catch (IOException e) {
				Activator
						.error("Error resolving HEAD for showing annotations in repository: " + repository, e); //$NON-NLS-1$
				return;
			}
		}
