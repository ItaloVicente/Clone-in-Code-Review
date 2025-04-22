		if (!noCheckout) {
			try {
				checkout(repository
			} catch (IOException ioe) {
				repository.close();
				throw new JGitInternalException(ioe.getMessage()
			} catch (GitAPIException | RuntimeException e) {
				repository.close();
				throw e;
			}
		}
		return new Git(repository
