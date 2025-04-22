			Git git = new Git(repository);
			try {
				git.fetch().setRemote(remoteName).call();
			} catch (Exception e) {
				Activator.logError(NLS.bind(CoreText.ConfigureFetchAfterCloneTask_couldNotFetch, fetchRefSpec), e);
			}
