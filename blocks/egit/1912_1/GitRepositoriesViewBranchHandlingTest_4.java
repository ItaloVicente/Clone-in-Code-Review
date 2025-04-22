
			myRepoViewUtil.getLocalBranchesItem(tree, clonedRepositoryFile)
					.expand().getNode("master").select();
			ContextMenuHelper.clickContextMenu(tree, myUtil
					.getPluginLocalizedValue("CheckoutCommand"));
			TestUtil.joinJobs(JobFamilies.CHECKOUT);
			refreshAndWait();
