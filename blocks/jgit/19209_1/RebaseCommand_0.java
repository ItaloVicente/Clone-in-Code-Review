	private void autoStash() throws GitAPIException
		if (repo.getConfig().getBoolean(ConfigConstants.CONFIG_REBASE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSTASH
			RevCommit stashCommit = Git.wrap(repo).stashCreate().setRef(null)
					.call();
			if (stashCommit != null) {
				FileUtils.mkdir(rebaseState.getDir());
				rebaseState.createFile(AUTOSTASH
			}
		}
	}

	private boolean autoStashApply() throws IOException
		boolean conflicts = false;
		if (rebaseState.getFile(AUTOSTASH).exists()) {
			String stash = rebaseState.readFile(AUTOSTASH);
			try {
				Git.wrap(repo).stashApply().setStashRef(stash)
						.ignoreRepositoryState(true).call();
			} catch (StashApplyFailureException e) {
				conflicts = true;
			}
		}
		return conflicts;
	}

