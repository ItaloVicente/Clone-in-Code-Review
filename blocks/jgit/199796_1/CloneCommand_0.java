		if (installBuiltinLfs && LfsFactory.getInstance().isAvailable()) {
			try {
				LfsInstallCommand installCommand = LfsFactory.getInstance()
						.getInstallCommand();
				if (installCommand != null) {
					installCommand.setRepository(repository).call();
				}
			} catch (Exception e) {
				if (repository != null) {
					repository.close();
				}
				cleanup();
				throw new JGitInternalException(e.getMessage()
			}
		}
