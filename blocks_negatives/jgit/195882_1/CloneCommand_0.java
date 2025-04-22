		if (!noCheckout) {
			try {
				checkout(repository, fetchResult);
			} catch (IOException ioe) {
				repository.close();
				throw new JGitInternalException(ioe.getMessage(), ioe);
			} catch (GitAPIException | RuntimeException e) {
				repository.close();
				throw e;
			}
