			done.set(false);

			final String jobName2 = NLS.bind(
					UIText.RepositoriesView_CheckingOutMessage,
					"refs/heads/master");

			listener = new JobChangeAdapter() {

				@Override
				public void done(IJobChangeEvent event) {
					if (jobName2.equals(event.getJob().getName()))
						done.set(true);
				}

			};

			Job.getJobManager().addJobChangeListener(listener);

			myRepoViewUtil.getLocalBranchesItem(tree, clonedRepositoryFile)
					.expand().getNode("master").select();
			ContextMenuHelper.clickContextMenu(tree, myUtil
					.getPluginLocalizedValue("CheckoutCommand"));
			refreshAndWait();

			for (int i = 0; i < 1000; i++) {
				if (done.get())
					break;
				Thread.sleep(10);
			}
			assertTrue("Job should be completed", done.get());
