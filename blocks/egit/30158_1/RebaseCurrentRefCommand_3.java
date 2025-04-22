			Repository repo = SelectionUtils.getRepository(ctx);
			if (repo != null) {
				boolean enabled = isEnabledForState(repo,
						repo.getRepositoryState());
				setBaseEnabled(enabled);
			} else {
				setBaseEnabled(false);
