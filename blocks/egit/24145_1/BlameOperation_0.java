		else {
			try {
				command.setStartCommit(repository.resolve(Constants.HEAD));
			} catch (IOException e) {
			}
		}
