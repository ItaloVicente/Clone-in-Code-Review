		getOrOpenView()
				.toolbarButton(
						myUtil.getPluginLocalizedValue("RepoViewAddRepository.tooltip"))
				.click();

		FileUtils.delete(getTestDirectory(), FileUtils.RECURSIVE
				| FileUtils.RETRY | FileUtils.SKIP_MISSING);
