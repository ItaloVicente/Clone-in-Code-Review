		IJobChangeListener listener = null;
		try {
			String initialContent = getTestFileContent();
			createTag("ResetToFirst", "The first tag");
			touchAndSubmit(null);
			String newContent = getTestFileContent();
			assertFalse("Wrong content", initialContent.equals(newContent));
			createTag("ResetToSecond", "The second tag");
			refreshAndWait();
			myRepoViewUtil.getTagsItem(tree, repositoryFile).expand().getNode(
					"ResetToFirst").select();

			final boolean[] done = new boolean[] { false };

			final String jobName = NLS.bind(UIText.ResetAction_reset,
					"refs/tags/ResetToFirst");

			listener = new JobChangeAdapter() {

				@Override
				public void done(IJobChangeEvent event) {
					if (jobName.equals(event.getJob().getName()))
						done[0] = true;
				}

			};

			Job.getJobManager().addJobChangeListener(listener);

			ContextMenuHelper.clickContextMenu(tree, myUtil
					.getPluginLocalizedValue("ResetCommand"));

			SWTBotShell resetDialog = bot
					.shell(UIText.ResetCommand_WizardTitle);
			resetDialog.bot().radio(UIText.ResetTargetSelectionDialog_ResetTypeHardButton).click();
			waitInUI();
			resetDialog.bot().button(IDialogConstants.FINISH_LABEL).click();
			waitInUI();

			bot.shell(UIText.ResetTargetSelectionDialog_ResetQuestion).bot()
					.button(IDialogConstants.YES_LABEL).click();

			for (int i = 0; i < 1000; i++) {
				if (done[0])
					break;
				Thread.sleep(10);
			}

			assertTrue("Job should be completed", done[0]);

			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
					IResource.DEPTH_INFINITE, null);
			assertEquals("Wrong content", initialContent, getTestFileContent());
		} finally {
			if (listener != null)
				Job.getJobManager().removeJobChangeListener(listener);
		}
