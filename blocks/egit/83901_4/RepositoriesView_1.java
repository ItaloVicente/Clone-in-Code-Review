		Map<Repository, Collection<String>> pathsByRepo = ResourceUtil
				.splitPathsByRepository(paths);
		boolean added = checkNotConfiguredRepositories(pathsByRepo);
		if (added) {
			scheduleRefresh(0, () -> {
				if (UIUtils.isUsable(getCommonViewer())) {
					selectAndReveal(pathsByRepo);
				}
			});
		} else {
			selectAndReveal(pathsByRepo);
		}
	}
