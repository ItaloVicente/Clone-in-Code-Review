			};

			Job.getJobManager().addJobChangeListener(listener);
			
			myRepoViewUtil.getLocalBranchesItem(tree, clonedRepositoryFile).expand().getNode("master").select();
			ContextMenuHelper.clickContextMenu(tree, myUtil
					.getPluginLocalizedValue("CheckoutCommand"));
			refreshAndWait();

			for (int i = 0; i < 1000; i++) {
				if (done.get())
					break;
				Thread.sleep(10);
			}
			assertTrue("Job should be completed", done.get());
			
			Job.getJobManager().removeJobChangeListener(listener);
